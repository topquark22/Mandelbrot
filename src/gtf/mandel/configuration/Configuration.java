package gtf.mandel.configuration;

import java.io.InputStream;
import java.util.Collection;
import gtf.mandel.formula.Formula;
import gtf.mandel.client.colour.ColorModelBase;

public final class Configuration {

  public static final String CONFIG_RESOURCE = "META-INF/mandelbrot-def.xml";

  private static Configuration instance;

  private FormulaFactory formulaFactory;
  private ColorModelFactory colorModelFactory;
  private PluginDescriptor builtins;

  private Configuration() throws ConfigurationException {
    formulaFactory = new FormulaFactory(getClass().getClassLoader()); // TEMP - support class loader of auxiliary jar
    colorModelFactory = new ColorModelFactory(getClass().getClassLoader()); // TEMP
    InputStream in = getClass().getClassLoader().getResourceAsStream(CONFIG_RESOURCE);
    if (in == null) {
      throw new ConfigurationException("Builtin plugins descriptor not found");
    }
    PluginDescriptorParser parser = new PluginDescriptorParser();
    parser.parse(in);
    builtins = parser.getDescriptor();
  }

  public synchronized static Configuration getInstance() throws ConfigurationException {
    if (instance == null) {
      instance = new Configuration();
    }
    return instance;
  }

  public Formula getDefaultFormula() throws NoSuchFormulaException {
    return getFormulaByName(builtins.getDefaultFormulaName());
  }

  public Collection<FormulaDesc> getFormulas() {
    return builtins.getFormulas();
  }

  public Formula getFormulaByName(String formulaName) throws NoSuchFormulaException {
    FormulaDesc desc = getFormulaDescByName(formulaName);
    if (desc == null) {
      throw new NoSuchFormulaException(formulaName + " not found");
    }
    return formulaFactory.newFormula(desc);
  }

  private FormulaDesc getFormulaDescByName(String formulaName) {
    FormulaDesc desc = null;
    Collection<FormulaDesc> formulas = builtins.getFormulas();
    for (FormulaDesc thisDesc : formulas) {
      if (thisDesc.getName().equals(formulaName)) {
        desc = thisDesc;
        break;
      }
    }
    return desc;
  }

  public ColorModelBase getDefaultColorModel() throws NoSuchColorModelException {
    return getColorModelByName(builtins.getDefaultColorModelName());
  }

  public Collection<ColorModelDesc> getColorModels() {
    return builtins.getColorModels();
  }

  public ColorModelBase getColorModelByName(String colorModelName) throws NoSuchColorModelException {
    ColorModelDesc desc = getColorModelDescByName(colorModelName);
    if (desc == null) {
      throw new NoSuchColorModelException("Colour model " + colorModelName + " not found");
    }
    return colorModelFactory.newColorModel(desc);
  }

  private ColorModelDesc getColorModelDescByName(String colorModelName) {
    ColorModelDesc desc = null;
    Collection<ColorModelDesc> colorModels = builtins.getColorModels();
    for(ColorModelDesc thisDesc : colorModels) {
      if (thisDesc.getName().equals(colorModelName)) {
        desc = thisDesc;
        break;
      }
    }
    return desc;
  }
}
