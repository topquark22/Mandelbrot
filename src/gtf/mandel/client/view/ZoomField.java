package gtf.mandel.client.view;

import java.awt.*;

/**
 *Description of the Class
 *
 * @author     gfalk
 */
class ZoomField extends TextField {

  private static final long serialVersionUID = 1L;
  
  /**
   *Description of the Field
   *
   * @since
   */
  protected double zoom;

  /**
   *Constructor for the ZoomField object
   *
   * @since
   */
  ZoomField() {
    super(8);
    setText("zoom=");
    setEditable(false);
    setBackground(Color.white);
    setColour();
  }

// Display log_10(delta), the number of orders of magnitude
// by which the display is magnified (round to 1 decimal)
// If we zoom in too far, the resolution will degrade. This happens
// usually when the zoom factor reaches 16. If this is the case,
// turn the text red.
  /**
   *Sets the zoom attribute of the ZoomField object
   *
   * @param  delta  The new zoom value
   * @since
   */
  public void setZoom(double delta) {
    zoom = Math.round(-10.0 * Math.log(delta) / Math.log(10.0)) / 10.0;
    setText("zoom=" + Double.toString(zoom));
    setColour();
  }

  /**
   *Sets the colour attribute of the ZoomField object
   *
   * @since
   */
  protected void setColour() {
    setForeground(zoom >= 16.0 ? Color.red : Color.black);
  }

}

