package gtf.mandel.client.bookmark;

import gtf.mandel.model.Region;
import gtf.mandel.model.mutable.JuliaRegionBean;
import gtf.mandel.model.mutable.RegionBean;
import gtf.math.Complex;

/**
 * Represents the data in a single bookmark.
 *
 *@author   gfalk
 */
public class Bookmark implements Bookmarkable {

  /**
   * The name of the formula for this bookmark.
   */
  private String formulaName;

  /**
   * The name key
   */
  private String name;

  /**
   * The long description
   */
  private String description;

  /**
   * This field determines whether the bookmark is for
   * a Julia set. If the value is null, the bookmark is
   * for a Mandelbrot set. Otherwise, it is for a Julia
   * set with the given juliaValue.
   */
  private Complex juliaValue;

  /**
   * The iteration limit for this bookmark.
   */
  private int limit;

  /**
   * The region of the complex plane.
   */
  private RegionBean region;

  public boolean isJulia() {
    return (region instanceof JuliaRegionBean);
  }

  /**
   * Gets the juliaValue attribute of the Bookmark object
   *
   *@return   The juliaValue value
   */
  public Complex getJuliaValue() {
    if (!isJulia()) {
      return null;
    }
    return ((JuliaRegionBean) region).getJuliaValue();
  }

  /**
   * Sets the juliaValue attribute of the Bookmark object
   *
   *@param juliaValue  The new juliaValue value
   */
  public void setJuliaValue(Complex juliaValue) {
    this.juliaValue = juliaValue;
  }

  /**
   * Gets the limit attribute of the Bookmark object
   *
   *@return   The limit value
   */
  public int getLimit() {
    return limit;
  }

  /**
   * Gets the region attribute of the Bookmark object.
   */
  public Region getRegion() {
    return region.asRegion();
  }

  /**
   * Gets the formula name.
   */
  public String getFormulaName() {
    return formulaName;
  }

  public String getName() {
    return name;
  }

  /**
   * Gets the long description.
   */
  public String getDescription() {
    return description;
  }

  /**
   * Sets the region attribute of the Bookmark object
   *
   *@param region  The new region value
   */
  public void setRegion(RegionBean region) {
    this.region = region;
  }

  /**
   * Sets the region attribute of the Bookmark object
   *
   *@param region  The new region value
   */
  public void setRegion(Region region) {
    this.region = new RegionBean(region);
  }

  /**
   * Sets the formulaName attribute of the Bookmark object
   *
   *@param formulaName  The new formulaName value
   */
  public void setFormulaName(String formulaName) {
    this.formulaName = formulaName;
  }

  /**
   * Sets the limit attribute of the Bookmark object
   *
   *@param limit  The new limit value
   */
  public void setLimit(int limit) {
    this.limit = limit;
  }

  public int getSize() {
    return 1;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * equals method (useful in unit test cases).
   *
   *@param o  Object to compare to
   *@return   Whether the object is a Bookmark with equal values.
   */
  public boolean equals(Object o) {
    if (o == null || !(o instanceof Bookmark)) {
      return false;
    }
    Bookmark b = (Bookmark) o;
    if (limit != b.limit) {
      return false;
    }
    if (isJulia() != b.isJulia()) {
      return false;
    }
    if (isJulia()) {
      if (!juliaValue.equals(b.juliaValue)) {
        return false;
      }
    }
    if ((description == null) ^ (b.description == null)) {
      return false;
    }
    if (description != null) {
      if (!description.equals(b.description)) {
        return false;
      }
    }
    if ((region == null) ^ (b.region == null)) {
      return false;
    }
    if (region != null) {
      if (!region.equals(b.region)) {
        return false;
      }
    }
    if ((formulaName == null) ^ (b.formulaName == null)) {
       return false;
    }
    if (formulaName != null) {
      if (!formulaName.equals(b.formulaName)) {
        return false;
      }
    }
    return true;
  }

  public int hashCode() {
    int code = limit;
    if (isJulia()) {
      code ^= juliaValue.hashCode();
    }
    if (description != null) {
      code ^= description.hashCode();
    }
    if (region != null) {
      code ^= region.hashCode();
    }
    if (formulaName != null) {
      code ^= formulaName.hashCode();
    }
    return code;
  }

  public String toString() {
    StringBuffer buf = new StringBuffer();
    buf.append("[name=" + name);
    if (description != null) {
      buf.append(", description=\"" + description + "\"");
    }
    buf.append(", formula=" + formulaName);
    buf.append(", region=" + region + "]");
    return buf.toString();
  }
}
