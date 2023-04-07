package gtf.mandel.client.controller;

import gtf.math.Complex;


/**
 * A handler for when the user double-clicks a point on the canvas.
 * 
 * @author gtf
 */
interface ClickHandler {
  
  /**
   * Handle double-click on a point
   *
   * @param  c  the complex number corresponding to the place where
   *            the user double-clicked.
   */
  void handleClick(Complex c);
  
}
