package gtf.mandel.client.controller;

import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Point;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;

import org.apache.log4j.Logger;

import gtf.awt.ImageSaver;
import gtf.mandel.client.Snapshot;
import gtf.mandel.client.colour.ColorModelBase;
import gtf.mandel.client.event.FractalControlListener;
import gtf.mandel.client.view.FractalFrame;
import gtf.mandel.client.view.FractalPanel;
import gtf.mandel.common.ServerConnection;
import gtf.mandel.model.MDataset;
import gtf.mandel.model.Region;
import gtf.math.Complex;

/**
 * Implements the main event loop for each fractal window.
 *
 *@author     gfalk
 */
class FractalController implements FractalControlListener {

  private final static Logger logger = Logger.getLogger(FractalController.class);
  
  /**
   *  the frame controlled by this controller
   */
  private FractalFrame frame;

  /**
   *  The region currently being displayed on the canvas
   */
  private Region region = null;

  /**
   * The current iteration limit
   */
  protected int limit;

  /**
   * The width and height of the canvas
   */
  protected Dimension bounds;

  /**
   * Connection to the calculation server
   */
  private ServerConnection connection;

  /**
   * The colour model.
   */
  private ColorModelBase colorModel;

  /**
   * The event loop (runs in a new thread)
   */
  private final EventLoop eventLoop;
  
  /**
   * Thread in which to run the event loop
   */
  private Thread eventThread;

  /**
   * Delegate to handle double-click events
   */
  private ClickHandler clickHandler;
  
  /**
   * Sets the ClickHandler
   * 
   * @param clickHandler
   */
  void setClickHandler(ClickHandler clickHandler) {
    this.clickHandler = clickHandler;
  }
  
  /**
   * Constructor for the FractalController object.
   *
   *@param  connection    The connection to the calculation server
   *@param  colorModel    The colour model to use
   *@param  initialRegion The region to display on the first calculation cycle
   *@param  initialLimit  The limit to use for the first calculation cycle
   */
  FractalController(FractalFrame frame, ServerConnection connection, ColorModelBase colorModel, Region initialRegion, int initialLimit) {
    this.frame = frame;
    frame.setListener(this);
    this.connection = connection;
    this.region = initialRegion;
    this.colorModel = colorModel;
    this.limit = initialLimit;
    this.eventLoop = new EventLoop();
  }

  /**
   * The main event loop for a display frame.
   */
  private class EventLoop implements Runnable {
    /**
     * Whether we are waiting for user input from the GUI
     */
    private volatile boolean waiting;
    
    /**
     * Whether this thread has been signalled to stop
     */
    private volatile boolean shouldStop = false;
    
    /**
     * Signal the event loop to stop and the thread to exit
     */
    private synchronized void stop() {
      shouldStop = true;
      notifyAll();
    }
    
    /**
     * @see Runnable.run()
     */
    public synchronized void run() {
      FractalPanel panel = frame.getPanel();
      panel.setSplash(true);
      try {
        do {
          // main event loop
          frame.enableControls(false);
          bounds = panel.getCanvasSize();
          limit = panel.getLimit();
          panel.setRunningMessage(true);
          MDataset dset = calculate();
          Snapshot snap = new Snapshot(dset, colorModel, region, limit);
          update(snap);
          panel.setRunningMessage(false);
          panel.setSplash(false);
          frame.enableControls(true);
          // Will be notified by a user action.
          waiting = true;
          while (waiting && !shouldStop) {
            wait();
          }
        } while (!shouldStop);
      } catch (InterruptedException e) {
        logger.error("Event loop was interrupted", e);
        System.exit(1); // FIXME should not be able to terminate the whole application!
      }
    }
    
    /**
     * Start a new calculation cycle.
     *
     *@since
     */
    private synchronized void trigger() {
      eventLoop.waiting = false;
      notifyAll();
    }
  }

  /**
   * Called when the user has dragged to a new region.
   *
   *@param  p1  upper left point
   *@param  p2  lower right point
   *@see FractalCanvasListener.regionChanged()
   */
  public void regionChanged(Point p1, Point p2) {
    synchronized (eventLoop) {
      region = toRegion(p1, p2);
      eventLoop.trigger();
    }
  }

  /**
   * Called when user changes value in the DisplayBar. Method of RegionListener
   * interface.
   *
   *@param  region  New region entered by the user
   */
  public void regionChanged(Region region) {
    synchronized (eventLoop) {
      this.region = region;
      eventLoop.trigger();
    }
  }

  /**
   * Callback from the frame when user double-clicks a point. This does not
   * need to be synchronized, because it does not affect the state of the
   * current panel. Method of MCanvasListener interface.
   *
   *@param  p  the point where the user double-clicked
   */
  public void clickReceived(Point p) {
    if (clickHandler != null) {
      clickHandler.handleClick(toComplex(p));
    }
  }

  /**
   *  Called when user presses "back". Overridden in stack controller. Method of
   *  MControlListener
   *
   *@since
   */
  public void backPressed() {}

  /**
   *  Called when user presses "reset". Overridden in stack controller. Method
   *  of MControlListener
   *
   *@since
   */
  public void resetPressed() {}

  /**
   * Called when user changes limit in the limit field.
   *
   * @param limit The new limit to use
   */
  public void limitChanged(int limit) {
    synchronized (eventLoop) {
      this.limit = limit;
      eventLoop.trigger();
    }
  }

  /**
   * Called when the frame was resized.
   */
  public void panelResized() {
    synchronized (eventLoop) {
      frame.getPanel().setSplash(true);
      eventLoop.trigger();
    }
  }

  /**
   *  Display a newly-calculated snapshot.
   *
   *@param  snap  The snapshot to be displayed
   *@since
   */
  protected void update(Snapshot snap) {
    region = snap.getRegion();
    limit = snap.getLimit();
    frame.getPanel().setSnapshot(snap);
  }

  /**
   * Kick off a new calculation.
   *
   *@return    The results of the calculation
   */
  private MDataset calculate() {
    MDataset data = null;
    try {
      data = connection.calculate(region, bounds, limit);
    } catch (RemoteException e) {
      e.printStackTrace(); // TODO better exception handling
      System.exit(1);
    } catch (InterruptedException e) {
      e.printStackTrace(); // TODO better exception handling
      System.exit(1);
    }
    return data;
  }

  /**
   * What happens when user requests to save the image.
   * 
   * @see gtf.mandel.client.event.FractalControlListener#saveImageRequested()
   */
  @SuppressWarnings("deprecation")
  public final void saveImageRequested() {
    final ImageProducer producer = frame.getPanel().getImageProducer();
    if (producer == null) {
      // no image
      return;
    }
    final FileDialog dialog = new FileDialog(frame, "Save Image", FileDialog.SAVE);
    dialog.setFile("*.png");
    /* TODO
     * dialog.show() is deprecated, but makes the thread block as required.
     * Need to determine if it can be replaced by dialog.setVisible(true)
     * with a modal dialog. Make sure it still blocks. Take care not to
     * depend on details of the implementation of this deprecated method.
     */
    dialog.show();
    String directory = dialog.getDirectory();
    String filename = dialog.getFile();
    if (directory != null && filename != null) {
      try {
        File file = new File(directory, filename);
        new ImageSaver(file).saveImage(producer);
      } catch (IOException ioe) {
        throw new RuntimeException(ioe); // failed to save
      }
    }
  }
  
  /**
   * Convert point coordinates to appropriate complex value.
   *
   *@param  p  The point where the user clicked
   *@return    The complex number corresponding to the point where
   *           the user clicked
   */
  private Complex toComplex(Point p) {
    double delta = region.getSpan() / bounds.width;
    return new Complex(region.getX() + delta * p.getX(), region.getY() + delta * p.getY());
  }

  /**
   * Convert rectangular coordinates to appropriate Region of complex plane.
   *
   *@param  p1  Pixel coordinates of upper-left corner of region
   *@param  p2  Pixel coordinates of lower-right corner of region
   *@return     Region object corresponding to the rectangular pixel area
   */
  private Region toRegion(Point p1, Point p2) {
    Complex corner1 = toComplex(p1);
    Complex corner2 = toComplex(p2);
    return new Region(corner1.getX(), corner1.getY(), corner2.getX() - corner1.getX());
  }
  
  /**
   * Launch the event loop in a new thread. Returns immediately
   * to the caller.
   * 
   * @throws IllegalStateException if the event loop has already
   * been launched
   */
  public final void launchEventLoop() {
    if (eventThread != null) {
      throw new IllegalStateException("already launched!");
    }
    eventThread = new Thread(eventLoop);
    logger.debug("Started thread " + eventThread.getName());
    eventThread.start();
  }
  
  /**
   * Request the event loop to terminate. Blocks until the
   * thread exits.
   *
   * @see FractalControlListener.terminate()
   */
  public final void terminate() {
    eventLoop.stop();
    try {
      eventThread.join();
    } catch (InterruptedException e) {}
    logger.debug("Joined thread " + eventThread.getName());
  }
}
