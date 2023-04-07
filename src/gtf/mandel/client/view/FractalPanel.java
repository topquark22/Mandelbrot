package gtf.mandel.client.view;

import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.ImageProducer;

import gtf.mandel.client.Snapshot;
import gtf.mandel.client.event.FractalControlListener;
import gtf.mandel.model.Region;


/**
 *  FractalPanel represents the display of a fractal and its associated controls. It
 *  is added to either an Applet (if run from a web page) or a Frame (if run as
 *  a standalone application). Most of the behaviour of the panel is managed by
 *  the controller. However: *)) If the user performed any action which could
 *  change the image (such as dragging on the canvas or pressing "back" or
 *  "reset"), the panel disables input and fires a panel event. *)) When the
 *  controller has sent a new image to the panel, the panel re-enables itself
 *  for input. Note: You will have to subclass FractalPanel and implement the
 *  constructor, in order to add the components and determine how they are laid
 *  out.
 *
 *@author     gfalk
 */
public class FractalPanel extends gtf.awt.DBPanel {

  private static final long serialVersionUID = 1L;
  
  /**
   *  Description of the Field
   *
   *@since
   */
  protected FractalCanvas canvas;
  /**
   *  Description of the Field
   *
   *@since
   */
  protected ControlBar controlBar;
  /**
   *  Description of the Field
   *
   *@since
   */
  protected DisplayBar displayBar;

  /**
   *Description of the Field
   *
   *@since
   */
  protected PanelDefaults defaults = new PanelDefaults();

  /**
   *  Request the splash screen to appear or go away as soon as possible. (If
   *  canvas timer has not expired, splash screen will not go away until timer
   *  actually expires.)
   *
   *@param  bit  The new splash value
   *@since
   */
  public void setSplash(boolean bit) {
    if (bit) {
      canvas.splash();
    } else {
      canvas.unsplash();
    }
  }

  /**
   *  Sets the x, y, span values to display in the DisplayBar, and the image
   *  displayed in the panel
   *
   *@param  snap  The new snapshot
   *@since
   */
  public void setSnapshot(Snapshot snap) {
    Region r = snap.getRegion();
    canvas.setProducer(snap.getProducer());
    displayBar.setRegion(r);
    controlBar.setLimit(snap.getLimit());
    controlBar.setZoom(r.getSpan() / canvas.getWidth());
  }

  /**
   *  Sets the runningMessage attribute of the FractalPanel object
   *
   *@param  bit  The new runningMessage value
   *@since
   */
  public void setRunningMessage(boolean bit) {
    canvas.setRunningMessage(bit);
  }

  /**
   *  Tell the panel to notify us when the user does something.
   *
   *@param  listener  The new listener value
   *@since
   */
  public void setListener(final FractalControlListener listener) {
    controlBar.setListener(listener);
//  displayBar.setListener(listener);
    canvas.setListener(listener);
    addComponentListener(
      new ComponentAdapter() {
        public void componentResized(ComponentEvent e) {
          listener.panelResized();
        }
      }
    );
  }

  /**
   *  allows the controller to get the new region, if user changed it
   *
   *@return    The region value
   *@since
   */
  public Region getRegion() {
    return displayBar.getRegion();
  }

  /**
   *  Allows the controller to get the new limit value, if user changed it
   *
   *@return    The limit value
   *@since
   */
  public int getLimit() {
    return controlBar.getLimit();
  }

  /**
   *  Enable/disable user input
   *
   *@param  enabled  Whether or not the panel should be enabled
   *@since
   */
  public void enableControls(boolean enabled) {
    canvas.setEnabled(enabled);
    controlBar.setEnabled(enabled);
    displayBar.setEnabled(enabled);
  }

  /**
   *  Gets the size of the canvas
   *
   *@return    Size of the canvas
   *@since
   */
  public Dimension getCanvasSize() {
    return new Dimension(canvas.getWidth(), canvas.getHeight());
  }

  /**
   *  Sets the limit attribute of the FractalPanel object
   *
   *@param  limit  The new limit value
   *@since
   */
  void setLimit(int limit) {
    controlBar.setLimit(limit);
  }

  /**
   *  Sets the region values displayed in the control bar.
   *
   *@param  region  The new region value
   *@since
   */
  void setRegion(Region region) {
    displayBar.setRegion(region);
  }

  public ImageProducer getImageProducer() {
    return canvas.getImageProducer();
  }
}

