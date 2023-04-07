package gtf.mandel.configuration;

import java.lang.reflect.Constructor;

import gtf.mandel.formula.Formula;
import gtf.mandel.formula.FormulaContext;
import gtf.mandel.formula.IterationFunction;

/**
 * A factory for formulas. Intended to be used only within the
 * configuration package (called by the configuration wrapper)
 *
 *@author   gtf
 */
class FormulaFactory {

  private ClassLoader classLoader;

  /**
   * Constructor for the FormulaFactory object
   *
   *@param classLoader  the classloader to use
   */
  public FormulaFactory(ClassLoader classLoader) {
    this.classLoader = classLoader;
  }

  FormulaContext newFormulaContext(FormulaDesc desc) {
    FormulaContext context = new FormulaContext();
    context.setName(desc.getName());
    context.setDisplayName(desc.getDisplayname());
    context.setInitialRegion(desc.getRegion());
    context.setInitialJuliaRegion(desc.getJuliaRegion());
    context.setAttributes(desc.getParams());
    return context;
  }

  /**
   * Instantiate a new formula and its context based on data from the XML
   * descriptor.
   *
   *@param desc  Data from the XML descriptor
   *@return      The formula with its context
   */
  Formula newFormula(FormulaDesc desc) throws NoSuchFormulaException {
    FormulaContext context = newFormulaContext(desc);
    Formula formula = null;
    Class<?> clazz = null;
    String classname = desc.getClassname();
    try {
      clazz = classLoader.loadClass(classname);
    } catch (ClassNotFoundException e) {
      throw new NoSuchFormulaException("Class for formula \"" +
          classname + "\" was not found.");
    }
// Ensure that the formula class implements Formula
    if (!IterationFunction.class.isAssignableFrom(clazz)) {
      throw new NoSuchFormulaException(classname + " does not implement " +
          IterationFunction.class.getName());
    }
    Constructor<?> cons = null;
    try {
      try {
        cons = clazz.getConstructor();
      } catch (NoSuchMethodException e) {
        throw new NoSuchFormulaException(classname + " must have a no-argument constructor");
      }
      IterationFunction function = (IterationFunction) cons.newInstance();
      formula = new Formula(context, function);
    } catch (Exception e) {
// What can go wrong that we get here?
      e.printStackTrace();
      System.exit(1);
    }
    return formula;
  }
}
