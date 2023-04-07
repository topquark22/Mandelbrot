package gtf.mandel.model.mutable;

import gtf.math.Complex;

/**
 * Mutable bean representing a Region and a juliaValue, for
 * use with the Digester.
 *
 * Needs to be public, but only so the Digester can find it.
 *
 *@author     gtf
 */
public class JuliaRegionBean extends RegionBean {

  private double juliaX;

  private double juliaY;

  public double getJuliaX() {
    return juliaX;
  }

  public double getJuliaY() {
    return juliaY;
  }

  public void setJuliaX(double juliaX) {
    this.juliaX = juliaX;
  }

  public void setJuliaY(double juliaY) {
    this.juliaY = juliaY;
  }

  public Complex getJuliaValue() {
    return new Complex(juliaX, juliaY);
  }

  public String toString() {
    return "[region=" + asRegion() + ", juliaValue=" + getJuliaValue() + "]";
  }

}

