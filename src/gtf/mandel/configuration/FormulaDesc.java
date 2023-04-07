package gtf.mandel.configuration;

import java.util.Vector;
import java.util.Collection;
import java.util.Collections;

import gtf.mandel.model.Region;
import gtf.mandel.model.mutable.RegionBean;

/**
 * Contains data about a formula from the plugin descriptor. Can be used by a
 * factory class to instantiate the formula and its context.
 *
 *@author   gtf
 */
public class FormulaDesc {

  private String formulaName;
  private String functionClassname;
  private String formulaDisplayname;
  private Region region;
  private Region juliaRegion;

  private final Vector<FormulaParam> params = new Vector<FormulaParam>();

  /**
   * Set the formulaName attribute of the FormulaDesc object
   *
   *@param formulaName  The new formulaName value
   */
  public void setName(String formulaName) {
    this.formulaName = formulaName;
  }

  /**
   * Get the formulaName attribute of the FormulaDesc object
   *
   *@return   The name value
   */
  public String getName() {
    return formulaName;
  }

  /**
   * Set the formulaClassname attribute of the FormulaDesc object
   *
   *@param formulaClassname  The new formulaClassname value
   */
  public void setClassname(String functionClassname) {
    this.functionClassname = functionClassname;
  }

  /**
   * Get the classname attribute of the FormulaDesc object
   *
   *@return   The classname value
   */
  public String getClassname() {
    return functionClassname;
  }

  /**
   * Set the formulaDisplayname attribute of the FormulaDesc object
   *
   *@param formulaDisplayname  The new formulaDisplayname value
   */
  public void setDisplayname(String formulaDisplayname) {
    this.formulaDisplayname = formulaDisplayname;
  }

  /**
   * Get the displayname attribute of the FormulaDesc object
   *
   *@return   The displayname value
   */
  public String getDisplayname() {
    return formulaDisplayname;
  }

  /**
   * Sets the initial starting region.
   *
   *@param region  A JavaBean encapulating the initial region
   */
  public void setRegion(RegionBean region) {
    this.region = region.asRegion();
  }

  /**
   * Gets the initial starting region.
   */
  public Region getRegion() {
    return region;
  }


  /**
   * Sets the initial starting region for Julia sets.
   *
   *@param juliaRegion  A JavaBean encapulating the initial region for Julia sets
   */
  public void setJuliaRegion(RegionBean juliaRegion) {
    this.juliaRegion = juliaRegion.asRegion();
  }

  /**
   * Gets the initial starting region for Julia sets.
   */
  public Region getJuliaRegion() {
    return juliaRegion;
  }

  /**
   * Adds a parameter to the underlying formula.
   *
   *@param param  The formula parameter to be added
   */
  public void addParam(FormulaParam param) {
    params.add(param);
  }

  /**
   * Gets the parameters of the underlying formula.
   */
  public Collection<FormulaParam> getParams() {
    return Collections.unmodifiableCollection(params);
  }

  /**
   * Returns a string representation of the formula.
   */
  public String toString() {
    String head = formulaName + "\n" + functionClassname + "\n" + formulaDisplayname + "\n";
    String irs = "Initial region: " + (region == null? "null" : region.toString()) + "\n";
    String jirs = "Initial Julia region: " + ((juliaRegion == null) ? "null" : juliaRegion.toString()) + "\n";
    String paramStr = "";
    for (FormulaParam param : params) {
      paramStr += "   " + param.getName() + ": ";
      paramStr += param.getValue() + "\n";
    }
    return head + irs + jirs + paramStr;
  }
}
