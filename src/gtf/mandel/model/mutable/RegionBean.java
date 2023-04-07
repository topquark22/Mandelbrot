package gtf.mandel.model.mutable;

import gtf.mandel.model.Region;

/**
 * Mutable wrapper for the immutable Region class, for use
 * with the Digester.
 *
 * Needs to be public, but only so the Digester can find it.
 *
 *@author     gtf
 */
public class RegionBean {

  private double x;

  private double y;

  private double span;

  public RegionBean() {
  }

  public RegionBean(Region region) {
    x = region.getX();
    y = region.getY();
    span = region.getSpan();
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  public void setX(double x) {
    this.x = x;
  }

  public void setY(double y) {
    this.y = y;
  }

  public void setSpan(double span) {
    this.span = span;
  }

  public Region asRegion() {
    return new Region(x, y, span);
  }

  /**
   * Get the span (side length). Note if the display is rectangular,
   * the side length is the width of the display.
   */
  public double getSpan() {
    return span;
  }

  public String toString() {
    return "[" + asRegion().toString() + "]";
  }

}

