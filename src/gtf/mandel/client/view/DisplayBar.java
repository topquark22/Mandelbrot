package gtf.mandel.client.view;

import gtf.mandel.model.Region;

import java.awt.GridLayout;
import java.awt.Panel;

/**
 *  Description of the Class
 *
 *@author     gfalk
 */
class DisplayBar extends Panel {

  private static final long serialVersionUID = 1L;
  
  private DTextField x, y, span;

  /**
   *  Constructor for the DisplayBar object
   *
   *@since
   */
  public DisplayBar() {
    super(new GridLayout());
    add(x = new DTextField());
    add(y = new DTextField());
    add(span = new DTextField());
    x.setEditable(false);
    y.setEditable(false);
    span.setEditable(false);
  }

  /**
   *  Sets the region displayed on the DisplayBar
   *
   *@param  region  The new region value
   *@since
   */
  public synchronized void setRegion(Region region) {
    x.setValue(region.getX());
    y.setValue(region.getY());
    span.setValue(region.getSpan());
  }

  /**
   *  Gets the region displayed on the DisplayBar
   *
   *@return    The region value
   *@since
   */
  public synchronized Region getRegion() {
    return new Region(x.getValue(), y.getValue(), span.getValue());
  }
}

