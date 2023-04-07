package gtf.mandel.client;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 * <p>Encapsulates parameters about things that were specified in the deployment
 * descriptor. Specialized contexts about things specified in the deployment
 * descriptor should extend this class.</p>
 *
 * <p>All things in the deployment descriptor support a name and a displayName, and
 * can have named String attributes. You can retrieve an attribute as several
 * primitive types. If your requirements go beyond that, you will need to extend
 * this class.</p>
 *
 *@author   gtf
 */
public abstract class ContextBase implements Serializable {

  private static final long serialVersionUID = 1L;

  private String name;

  private String displayName;

  private Map<String, Object> attributes;

  /**
   * Set the name within the Context.
   *
   *@param name  The new name value
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Get the name within the Context.
   *
   *@return   The name value
   */
  public String getName() {
    return name;
  }

  /**
   * Set the displayName within the Context.
   *
   *@param displayName  The new displayName value
   */
  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  /**
   * Get the displayName within the Context.
   *
   *@return   The displayName value
   */
  public String getDisplayName() {
    return displayName;
  }

  /**
   * Set the attributes.
   *
   * TODO see if it can be made immutable
   *
   *@param attributes  The new attributes value
   */
  public void setAttributes(Map<String, Object> attributes) {
    this.attributes = attributes;
  }

  /**
   * Get a copy of the attributes from this Context.
   *
   *@return   A copy of the attributes
   */
  public Map<String, Object> getAttributes() {
    return (attributes == null ? new HashMap<String, Object>() : Collections.unmodifiableMap(attributes));
  }

  /**
   * Set an attribute within the Context.
   *
   *@param key    The new attribute value
   *@param value  The new attribute value
   */
  public void setAttribute(String key, Object value) {
    if (attributes == null) {
      attributes = new HashMap<String, Object>();
    }
    attributes.put(key, value);
  }

  /**
   * Get an attribute and return null if it does not exist.
   *
   *@param key  Name of the attribute
   *@return     The attribute value
   */
  public String getAttribute(String key) {
    return (String) attributes.get(key);
  }

  /**
   * Try to get an attribute as a String type.
   *
   *@param key                      Name of the attribute
   *@return                         The attribute value as a String
   *@throws NoSuchElementException  The attribute does not exist in the
   *      attribute list
   */
  public String getAttributeAsString(String key) throws NoSuchElementException {
    String value = getAttribute(key);
    if (value == null) {
      throw new NoSuchElementException("Nonexistent attribute \"" + key +
          "\" requested. Check your mandelbrot-def.xml");
    }
    return value;
  }

  /**
   * Get a context attribute as a double type.
   *
   *@param key                      Name of the attribute
   *@return                         The attribute value as a double
   *@throws NoSuchElementException  The attribute does not exist in the
   *      attribute list
   *@throws NumberFormatException   The attribute cannot be converted to a
   *      double
   */
  public double getAttributeAsDouble(String key) throws NoSuchElementException, NumberFormatException {
    String value = getAttributeAsString(key);
    double result;
    try {
      result = Double.parseDouble(value);
    } catch (NumberFormatException e) {
      throw new NumberFormatException("Invalid double value \"" + value +
          "\" for key \"" + key + "\". Check your mandelbrot-def.xml");
    }
    return result;
  }

  /**
   * Get a context attribute as a float type.
   *
   *@param key                      Name of the attribute
   *@return                         The attribute value as a float
   *@throws NoSuchElementException  The attribute does not exist in the
   *      attribute list
   *@throws NumberFormatException   The attribute cannot be converted to a float
   */
  public float getAttributeAsFloat(String key) throws NoSuchElementException, NumberFormatException {
    String value = getAttributeAsString(key);
    float result;
    try {
      result = Float.parseFloat(value);
    } catch (NumberFormatException e) {
      throw new NumberFormatException("Invalid float value \"" + value +
          "\" for key \"" + key + "\". Check your mandelbrot-def.xml");
    }
    return result;
  }

  /**
   * Get a context attribute as an int type.
   *
   *@param key                      Name of the attribute
   *@return                         The attribute value as an int
   *@throws NoSuchElementException  The attribute does not exist in the
   *      attribute list
   *@throws NumberFormatException   The attribute cannot be converted to an int
   */
  public int getAttributeAsInt(String key) throws NoSuchElementException, NumberFormatException {
    String value = getAttributeAsString(key);
    int result;
    try {
      result = Integer.parseInt(value);
    } catch (NumberFormatException e) {
      throw new NumberFormatException("Invalid int value \"" + value +
          "\" for key \"" + key + "\". Check your mandelbrot-def.xml");
    }
    return result;
  }

  /**
   * Get a context attribute as a long type.
   *
   *@param key                      Name of the attribute
   *@return                         The attribute value as a long
   *@throws NoSuchElementException  The attribute does not exist in the
   *      attribute list
   *@throws NumberFormatException   The attribute cannot be converted to a long
   */
  public long getAttributeAsLong(String key) throws NoSuchElementException, NumberFormatException {
    String value = getAttributeAsString(key);
    long result;
    try {
      result = Long.parseLong(value);
    } catch (NumberFormatException e) {
      throw new NumberFormatException("Invalid long value \"" + value +
          "\" for key \"" + key + "\". Check your mandelbrot-def.xml");
    }
    return result;
  }

  /**
   * Return a String representation of this object.
   *
   * @return The String representation
   */
  public String toString() {
    StringBuffer buf = new StringBuffer();
    buf.append("[ContextBase Name=" + name + ",");
    buf.append("Displayname=" + displayName + "]");
    return buf.toString();
  }

  /**
   * Get a context attribute as a boolean type.
   *
   *@param key                      Name of the attribute
   *@return                         The attribute value as a boolean. Valid
   *      values for the attribute are "true", "yes", and "on" which will
   *      convert to <i>true</i> ; and "false", "no" and "off" which will
   *      convert to <i>false</i> .
   *@throws NoSuchElementException  The attribute does not exist in the
   *      attribute list
   *@throws NumberFormatException   The attribute cannot be converted to a
   *      boolean
   */
  public boolean getAttributeAsBoolean(String key) throws NoSuchElementException, NumberFormatException {
    String value = getAttributeAsString(key);
    if ("true".equals(value) || "yes".equals(value) || "on".equals(value)) {
      return true;
    }
    if ("false".equals(value) || "no".equals(value) || "off".equals(value)) {
      return false;
    }
    throw new NumberFormatException("Invalid boolean value \"" + value +
        "\" for key \"" + key + "\". Check your mandelbrot-def.xml");
  }
}
