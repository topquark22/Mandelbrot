package gtf.mandel.client.view;

/**
 *  used by a CanvasController to control a FractalCanvas.
 *
 *@author     gtf
 */
interface CanvasControls {
  /**
   *  Enable/disable user input to the MCanvas.
   *
   *@param  isEnabled  The new enabled value
   */
  void setEnabled(boolean isEnabled);

  /**
   *  Turn on/off the running message.
   *
   *@param  isRunning  Description of Parameter
   */
  void setRunningMessage(boolean isRunning);

  /**
   *  Displays splash screen and sets timer.
   */
  void splash();

  /**
   *  Request to unsplash as soon as possible.
   */
  void unsplash();
}

