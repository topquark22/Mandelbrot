package gtf.mandel.configuration;

import java.util.Vector;
import java.util.Collection;
import java.util.Collections;

/**
 * Contains data about a colour model from the plugin descriptor.
 * Can be used by a factory class to instantiate the colour model.
 *
 *@author   gtf
 */
public class ColorModelDesc {

  private String colourModelName;
  private String colourModelClassname;
  private String colourModelDisplayname;

  private final Vector<ColorModelParam> params = new Vector<ColorModelParam>();

  /**
   * Sets the name of this colour model.
   *
   *@param colourModelName  The new colour model name
   */
  public void setName(String colourModelName) {
    this.colourModelName = colourModelName;
  }

  /**
   * Gets the colour model name.
   */
  public String getName() {
    return colourModelName;
  }

  /**
   * Sets the classname of this colour model.
   *
   *@param colourModelClassname  The classname of the colour model
   */
  public void setClassname(String colourModelClassname) {
    this.colourModelClassname = colourModelClassname;
  }

  public String getClassname() {
    return colourModelClassname;
  }

  /**
   * Sets the display name of this colour model.
   *
   *@param colourModelDisplayname  The display name of the colour model
   */
  public void setDisplayname(String colourModelDisplayname) {
    this.colourModelDisplayname = colourModelDisplayname;
  }

  public String getDisplayname() {
    return colourModelDisplayname;
  }

  /**
   * Adds a parameter to the colour model.
   *
   *@param param  The parameter to be added
   */
  public void addParam(ColorModelParam param) {
    params.add(param);
  }

  /**
   * Returns an unmodifiable copy of the parameters for the colour model.
   */
  public Collection<ColorModelParam> getParams() {
    return Collections.unmodifiableCollection(params);
  }

  /**
   * Returns a string representation of the colour model.
   */
  public String toString() {
    String head = colourModelName + "\n" + colourModelClassname + "\n" + colourModelDisplayname + "\n";
    String paramStr = "";
    for (ColorModelParam param : params) {
      paramStr += "   " + param.getName() + ": ";
      paramStr += param.getValue() + "\n";
    }
    return head + paramStr;
  }
}
