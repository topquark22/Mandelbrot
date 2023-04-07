package gtf.mandel.client.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.ImageProducer;
import java.util.Timer;
import java.util.TimerTask;

import gtf.awt.InputMaskParser;
import gtf.mandel.client.CopyrightInfo;
import gtf.mandel.client.event.CanvasMouseListener;
import gtf.mandel.client.event.FractalControlListener;


/**
 *  FractalCanvas is responsible for displaying the copyright message and the colour
 *  image, passing raw mouse events to a "cooker" for higher-level processing,
 *  and drawing the drag box. The Cooker converts the raw mouse events into
 *  meaningful canvas events and passes them back to the FractalPanel. (The FractalPanel
 *  further converts the canvas coordinates back to double values before passing
 *  them on to the MEngine.)
 *
 *@author     gfalk
 */
class FractalCanvas extends gtf.awt.DBCanvas implements CanvasControls {

  private static final long serialVersionUID = 1L;

  private CanvasCooker cooker;

  private Image image = null;
  private ImageProducer producer = null;
  private DragBox dragBox;
  private boolean showRunningMessage;
  private boolean showDragBox;
  private boolean showSplash;
  private CopyrightInfo copyrightInfo;
  private boolean actuallyUnsplash = false;

  /**
   *  Constructor for the FractalCanvas object
   *
   *@param  copyrightInfo  Description of Parameter
   *@param  size           Description of Parameter
   */
  FractalCanvas(CopyrightInfo copyrightInfo, Dimension size) {
    this(copyrightInfo);
    setSize(size);
  }

  /**
   *  Constructor for the FractalCanvas object
   *
   *@param  copyrightInfo  Description of Parameter
   *@since
   */
  FractalCanvas(CopyrightInfo copyrightInfo) {
    this.copyrightInfo = copyrightInfo;
    cooker = new CanvasCooker();
    addMouseListener(cooker);
    addMouseMotionListener(cooker);
    showSplash = true;
    showRunningMessage = true;
    showDragBox = false;
  }

  /**
   *  Request the splash screen to appear immediately. A new timer will be set.
   *  The splash screen will remain visible until after the timer expires *and*
   *  unsplash() is called (in any order).
   */
  public synchronized void splash() {
    actuallyUnsplash = false;
    showSplash = true;
    new CopyrightTimer(copyrightInfo.getDelayTime());
    repaint();
  }

  /**
   *  Request the splash screen to go away as soon as possible.
   *  (If canvas timer has not expired, splash screen will not go away
   *  until timer actually expires.)
   */
  public synchronized void unsplash() {
    if(actuallyUnsplash) {
      showSplash = false;
      repaint();
    } else {
      actuallyUnsplash = true;
    }
  }

  /**
   *  Sets the image attribute of the FractalCanvas object
   *
   *@param  producer  The new producer value
   *@since
   */
  public synchronized void setProducer(ImageProducer producer) {
    this.producer = producer;
    image = createImage(producer);
    showRunningMessage = false;
    showDragBox = false;
    repaint();
  }

  /**
   *  Sets the runningMessage attribute of the FractalCanvas object
   *
   *@param  bit  The new runningMessage value
   *@since
   */
  public synchronized void setRunningMessage(boolean bit) {
    showRunningMessage = bit;
    repaint();
  }

  /**
   * Gets the image producer.
   */
  public synchronized ImageProducer getImageProducer() {
    if (showSplash) {
      return null;
    }
    return producer;
  }
  
  /**
   * Gets the rendered image.
   * FIXME possibly not synchronized properly?? check image is
   * available and ready?
   * 
   * @return the rendered image
   */
  public Image getImage() {
    if (showSplash || image == null) {
      // FIXME this is not graceful!!
      throw new IllegalStateException("image is not ready!!");
    }
    return image;
  }
  
  /**
   *  Set a listener to receive "cooked" canvas events from the canvas
   *
   *@param  listener  The listener (typically the MController)
   */
  public void setListener(FractalControlListener listener) {
    cooker.setListener(listener);
  }

  /**
   *  Description of the Method
   *
   *@param  g  Description of Parameter
   *@since
   */
  public void paint(Graphics g) {
    if (showSplash || image == null) {
      splash(g);
      runningMessage(g);
    } else {
      Rectangle clipBounds = g.getClipBounds();
      if (clipBounds != null) {
        g.drawImage(image, clipBounds.x, clipBounds.y,
            clipBounds.x + clipBounds.width, clipBounds.y + clipBounds.height,
            clipBounds.x, clipBounds.y,
            clipBounds.x + clipBounds.width, clipBounds.y + clipBounds.height,
            this);
      } else {
        g.drawImage(image, 0, 0, this);
      }
      if (showDragBox) {
        dragBox.draw(g);
      }
      if (showRunningMessage) {
        runningMessage(g);
      }
    }
  }

  /**
   *  Sets the dragBox attribute of the FractalCanvas object
   *
   *@param  p1  The new dragBox value
   *@param  p2  The new dragBox value
   */
  private synchronized void setDragBox(Point p1, Point p2) {
    this.dragBox = new DragBox(p1, p2);
  }

  /**
   *  for use of the CanvasCooker inner class
   *
   *@return    The dragBox value
   */
  private DragBox getDragBox() {
    return dragBox;
  }

  /**
   *  Description of the Method
   */
  private synchronized void timerExpired() {
    if(image == null) {
      actuallyUnsplash = true;  // avoids repainting
    } else {
      unsplash();
    }
  }

  /**
   *  Paint the splash screen
   *
   *@param  g  graphics context
   *@since
   */
  private void splash(Graphics g) {
    int width = getWidth();
    int height = getHeight();
    g.setColor(Color.white);
    g.fillRect(0, 0, getWidth(), getHeight());
    g.setColor(Color.black);
    g.drawString("Mandelbrot " + copyrightInfo.getVersion(), (width / 2) - 40, height / 2 - 12);
    g.drawString("copyright " + copyrightInfo.getDate(), (width / 2) - 40, height / 2);
    g.drawString(copyrightInfo.getAuthor(), (width / 2) - 40, height / 2 + 12);
    g.drawString("All rights reserved", (width / 2) - 40, height / 2 + 24);
  }

  /**
   *  Paint the running message
   *
   *@param  g  graphics context
   *@since
   */
  private void runningMessage(Graphics g) {
    if (showSplash) {
      g.setColor(Color.black);
    } else {
      g.setColor(Color.white);
    }
    int width = getWidth();
    int height = getHeight();
    g.drawString("(please wait)", (width / 2) - 40, height - 12);
  }

  /**
   *  Set a timer to ensure that the copyright message displays for a minimum
   *  amount of time.
   *
   *@author     gtf
   */
  class CopyrightTimer extends Timer {
    /**
     *  Constructor for the CopyrightTimer object
     *
     *@param  delay  delay in seconds
     */
    CopyrightTimer(long delay) {
      TimerTask timerTask = new TimerTask() {
        public void run() {
          timerExpired();
          CopyrightTimer.this.cancel(); // allow reaping of timer thread
        }
      };
      schedule(timerTask, 1000 * delay);
    }
  }

  /**
   *  Receives raw mouse events from the FractalCanvas. Manages the state and display of
   *  the drag box. Transmits coordinates to the FractalControlListener upon drag or
   *  double-click.
   *
   *@author     gfalk
   */
  class CanvasCooker implements CanvasMouseListener {

    FractalControlListener listener;

    /**
     *  pressedAtPoint keeps track of the origin of the drag box. Only valid if
     *  showDragBox == true.
     */
    private Point pressedAtPoint = null;

    /**
     *  Adds a listener (typically the FractalController) to receive "cooked" mouse
     *  events from the FractalCanvas.
     *
     *@param  listener  normally the FractalController for this window
     */
    public void setListener(FractalControlListener listener) {
      this.listener = listener;
    }

    /**
     *  Method of the MouseListener interface
     *
     *@param  e  The mouse event
     *@since
     */
    public synchronized void mousePressed(MouseEvent e) {
      if (InputMaskParser.button1(e)) {
        pressedAtPoint = e.getPoint();
        setDragBox(pressedAtPoint, pressedAtPoint);
        showDragBox = true;
        repaint();
      } else if (InputMaskParser.button2(e)) {
      }
    }

    /**
     *  Method of the MouseListener interface
     *
     *@param  e  The mouse event
     *@since
     */
    public synchronized void mouseReleased(MouseEvent e) {
      if (InputMaskParser.button1(e)) {
        if (pressedAtPoint != null) {
          if (!e.getPoint().equals(pressedAtPoint)) {
            Point p1 = pressedAtPoint;
            Point p2 = e.getPoint();
            setDragBox(p1, p2);
            notifyOfDrag();
          }
          pressedAtPoint = null;
        }
      } else if (InputMaskParser.button2(e)) {
      }
    }

    /**
     *  Method of the MouseListener interface
     *
     *@param  e  The mouse event
     *@since
     */
    public synchronized void mouseClicked(MouseEvent e) {
      if (InputMaskParser.button1(e)) {
        if (e.getClickCount() == 2) {
          notifyOfClick(e.getPoint());
        }
      }
    }

    /**
     *  Method of the MouseMotionListener interface
     *
     *@param  e  The mouse event
     *@since
     */
    public synchronized void mouseDragged(MouseEvent e) {
      if (pressedAtPoint != null) {
        setDragBox(pressedAtPoint, e.getPoint());
        repaint();
      }
    }

    /**
     *  Method of the MouseMotionListener interface
     *
     *@param  e  The mouse event
     *@since
     */
    public void mouseMoved(MouseEvent e) { }

    /**
     *  Method of the MouseMotionListener interface
     *
     *@param  e  The mouse event
     *@since
     */
    public void mouseEntered(MouseEvent e) { }

    /**
     *  Method of MouseMotionListener interface
     *
     *@param  e  a mouse event
     *@since
     */
    public void mouseExited(MouseEvent e) { }

    /**
     *  Determine coordinates of a double-click event and notify the
     *  FractalControlListener. Must return immediately.
     *
     *@param  p  The point where the user double-clicked.
     *@since
     */
    private void notifyOfClick(Point p) {
      if (listener != null) {
        listener.clickReceived(p);
      }
    }

    /**
     *  Determine coordinates of a click-drag-release event and notify the
     *  FractalControlListener. Must return immediately.
     *
     *@since
     */
    private void notifyOfDrag() {
      if (listener != null) {
        Point p1 = getDragBox().getCorner1();
        Point p2 = getDragBox().getCorner2();
        listener.regionChanged(p1, p2);
      }
    }
  }

  /**
   *  The white box that appears on the canvas when you drag the mouse
   *
   *@author     gtf
   */
  private class DragBox {
    private int boxX, boxY, boxWidth, boxHeight;

    /**
     *  DragBox is defined by its origin (where mouse pressed), and the drag
     *  diagonal. It adjusts itself to conform to the canvas's aspect ratio.
     *
     *@param  p1  The origin
     *@param  p2  Position of the mouse pointer
     *@since
     */
    private DragBox(Point p1, Point p2) {
      float aspectRatio = 1.0f * getHeight() / getWidth();
      boxWidth = Math.max(Math.abs(p2.x - p1.x),
          Math.round(Math.abs(p2.y - p1.y) / aspectRatio));
      boxHeight = Math.round(boxWidth * aspectRatio);
      boxX = (p2.x > p1.x) ? p1.x : p1.x - boxWidth;
      boxY = (p2.y > p1.y) ? p1.y : p1.y - boxHeight;
    }

    /**
     *  Gets the upper-left corner coordinates
     *
     *@return    The upper-left corner coordinates
     *@since
     */
    private Point getCorner1() {
      return new Point(boxX, boxY);
    }

    /**
     *  Gets the lower-right corner coordinates
     *
     *@return    The lower-right corner coordinates
     *@since
     */
    private Point getCorner2() {
      return new Point(boxX + boxWidth, boxY + boxHeight);
    }

    /**
     *  paint the dragBox onto a graphics context
     *
     *@param  g  graphics context
     *@since
     */
    private void draw(Graphics g) {
      if (boxWidth > 0 && boxHeight > 0) {
        g.setColor(Color.white);
        g.drawRect(boxX, boxY, boxWidth, boxHeight);
      }
    }
  }
}
