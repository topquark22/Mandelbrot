package gtf.mandel.client.event;

import java.util.EventObject;

import gtf.math.Complex;

import gtf.mandel.model.Region;


/**
 * An event generated by the main application panel
 */
public class LaunchEvent extends EventObject {

  private static final long serialVersionUID = 1L;
  
  private String formulaName;
  private String colorModelName;
  private int limit;
  private Region region;
  private Complex juliaValue;

  public LaunchEvent(Object source) {
    super(source);
  }

// getters and setters

  public void setFormulaName(String formulaName) {
    this.formulaName = formulaName;
  }

  public String getFormulaName() {
    return formulaName;
  }

  public void setColorModelName(String colorModelName) {
    this.colorModelName = colorModelName;
  }

  public String getColorModelName() {
    return colorModelName;
  }

  public void setLimit(int limit) {
    this.limit = limit;
  }

  public int getLimit() {
    return limit;
  }

  public void setRegion(Region region) {
    this.region = region;
  }

  public Region getRegion() {
    return region;
  }

  public void setJuliaValue(Complex juliaValue) {
    this.juliaValue = juliaValue;
  }

  public Complex getJuliaValue() {
    return juliaValue;
  }

  public boolean isJulia() {
    return juliaValue != null;
  }
}
