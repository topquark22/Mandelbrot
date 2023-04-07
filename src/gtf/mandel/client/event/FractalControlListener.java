package gtf.mandel.client.event;

import java.util.EventListener;
import java.awt.Point;

import gtf.mandel.model.Region;


/**
 * Listener for GUI events from the FractalFrame.
 *
 *@author     gfalk
 */
public interface FractalControlListener extends EventListener {
  /**
   *  invoked by ControlBar when "back" button pressed
   *
   *@since
   */
  void backPressed();

  /**
   *  invoked by ControlBar when "reset" button pressed
   *
   *@since
   */
  void resetPressed();

  /**
   *  invoked by ControlBar when limit changed to a new valid value
   *
   *@param  limit  Description of Parameter
   */
  void limitChanged(int limit);

  /**
   *  invoked by FractalPanel when panel resized
   *
   *@since
   */
  void panelResized();

  /**
   *  invoked by DisplayBar when user changes values
   *
   *@param  region  Description of Parameter
   */
  void regionChanged(Region region);

  /**
   *  invoked by CanvasController when user drags mouse
   *
   *@param  p1  upper-left corner of drag box
   *@param  p2  lower-right corner of drag box
   */
  void regionChanged(Point p1, Point p2);

  /**
   *  invoked by CanvasController when user clicks on a point
   *
   *@param  p  the point where user clicked
   */
  void clickReceived(Point p);
  
  /**
   * Invoked by FractalFileMenu when the "save image" item is pressed.
   */
  void saveImageRequested();
  
  /**
   * Request the event loop to terminate and the thread to
   * exit. Normally invoked when the window is closing.
   */
  void terminate();

}
