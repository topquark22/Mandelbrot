package gtf.mandel.configuration;

import java.lang.reflect.Constructor;

import gtf.mandel.client.colour.ColorModelBase;
import gtf.mandel.client.colour.ColorModelContext;


/**
 * A factory for colour models. Intended to be used only within the
 * configuration package (called by the configuration wrapper)
 *
 *@author   gtf
 */
class ColorModelFactory {

  private ClassLoader classLoader;

  /**
   * Constructor for the ColorModelFactory object
   *
   *@param classLoader  the classloader to use
   */
  public ColorModelFactory(ClassLoader classLoader) {
    this.classLoader = classLoader;
  }

  ColorModelContext newColorModelContext(ColorModelDesc desc) {
    ColorModelContext context = new ColorModelContext();
    context.setName(desc.getName());
    context.setDisplayName(desc.getDisplayname());
    context.setAttributes(desc.getParams());
    return context;
  }

  /**
   * Instantiate a new colour model and its context based on data from the XML
   * descriptor.
   *
   *@param desc  Data from the XML descriptor
   *@return      The colour model with its context
   */
  ColorModelBase newColorModel(ColorModelDesc desc) {
    ColorModelContext context = newColorModelContext(desc);
    ColorModelBase colorModel = null;
    Class<?> clazz = null;
    try {
      clazz = classLoader.loadClass(desc.getClassname());
    } catch (ClassNotFoundException e) {
// TODO: Realistically could get here if someone defined a colour model
// with a classname that does not exist. Throw a meaningful exception.
      e.printStackTrace();
      System.exit(1);
    }
    try {
      Constructor<?> cons = clazz.getConstructor(ColorModelContext.class);
      colorModel = (ColorModelBase) cons.newInstance(context);
    } catch (Exception e) {
// TODO: Realistically could get here if someone defined a colour model
// without a (ColorModelBase) constructor. Throw a meaningful
// exception.
      e.printStackTrace();
      System.exit(1);
    }
    return colorModel;
  }
}
