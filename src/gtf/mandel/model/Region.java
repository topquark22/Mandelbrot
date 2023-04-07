package gtf.mandel.model;

import java.io.PrintStream;
import java.io.Serializable;

import gtf.math.Complex;

/**
 * Represents a square region of the complex plane.
 * The coordinates of the lower left corner point and the
 * side length (span) are included.
 *
 *@author     gfalk
 */
public class Region implements Serializable {

  private static final long serialVersionUID = 1L;

  private final double x;

  private final double y;

  private final double span;

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  /**
   * Get the lower-left corner as a complex number.
   */
  public Complex getCorner() {
    return new Complex(x, y);
  }

  /**
   * Get the span (side length). Note if the display is rectangular,
   * the side length is the width of the display.
   */
  public double getSpan() {
    return span;
  }

  /**
   *Constructor for the Region object
   *
   *@param  x     Description of Parameter
   *@param  y     Description of Parameter
   *@param  span  Description of Parameter
   *@since
   */
  public Region(double x, double y, double span) {
    this.x = x;
    this.y = y;
    this.span = span;
  }

  /**
   * Provides a readable representation of this object.
   */
  public String toString() {
    return (new Complex(x, y).toString() + " span=" + span);
  }

  /**
   * Compares for equality.
   *
   *@param  o  The object to compare to
   */
  public boolean equals(Object o) {
    if (o == null || !(o instanceof Region)) {
      return false;
    }
    Region or = (Region) o;
    long xL = Double.doubleToLongBits(x);
    long yL = Double.doubleToLongBits(y);
    long spanL = Double.doubleToLongBits(span);
    long oxL = Double.doubleToLongBits(or.x);
    long oyL = Double.doubleToLongBits(or.y);
    long ospanL = Double.doubleToLongBits(or.span);
    return (xL == oxL && yL == oyL && spanL == ospanL);
  }

  /**
   * Computes a HashCode for this object.
   */
  public int hashCode() {
    long xL = Double.doubleToLongBits(x);
    long yL = Double.doubleToLongBits(y);
    long spanL = Double.doubleToLongBits(span);
    int xh = (int) (xL ^ (xL >>> 32));
    int yh = (int) (yL ^ (yL >>> 32));
    int spanh = (int) (spanL ^ (spanL >>> 32));
    int result = 17;
    result = 37 * result + xh;
    result = 37 * result + yh;
    result = 37 * result + spanh;
    return result;
  }

/* rather useless: */
  /**
   *Description of the Method
   *
   *@param out
   *@param indentLevel
   */
  public void printXML(PrintStream out, int indentLevel) {
    String tab = gtf.util.XML.tab(indentLevel);
    out.println(tab + "<region>");
    out.println(tab + "  <x>" + x + "</x>");
    out.println(tab + "  <y>" + y + "</y>");
    out.println(tab + "  <span>" + span + "</span>");
    out.println(tab + "</region>");
  }
}
