package gtf.mandel.configuration;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.digester.Digester;
import org.xml.sax.SAXException;


/**
 * Parse the mandelbrot-def.xml descriptor from a plugin or
 * builtin module.
 *
 * TODO: Except for the rules and String constants, this is almost the same
 * as BookmarksParser. Think about how to refactor it by encapsulating
 * rule-setting code somehow.
 *
 *@author   gtf
 */
class PluginDescriptorParser {

  /**
   * The Public ID for a Mandelbrot plugin descriptor.
   */
  final static String PUBLIC_ID = "-//Geoffrey Trueman Falk//DTD Mandelbrot Plugin 1.0//EN";

  /**
   * The System ID for a Mandelbrot plugin descriptor.
   */
  final static String SYSTEM_ID = "http://gtf.cirp.org/DTD/mandelbrot-def-1_0.dtd";

  /**
   * The file location for the DTD.
   */
  final static String DTD_LOCATION = "META-INF/mandelbrot-def-1_0.dtd";

  private PluginDescriptor descriptor;

  private final Digester digester;

  PluginDescriptorParser() {
    LocalEntityResolver resolver = new ClasspathEntityResolver();
    resolver.addMapping(SYSTEM_ID, DTD_LOCATION);
    digester = new Digester();
    digester.setEntityResolver(resolver);
    setBaseRules();
    setFormulaRules();
    setColourMapRules();
  }

  private void setBaseRules() {
    digester.addObjectCreate("mandelbrot-def", "gtf.mandel.configuration.PluginDescriptor");
    digester.addBeanPropertySetter("mandelbrot-def/description");
    digester.addBeanPropertySetter("mandelbrot-def/author");
    digester.addBeanPropertySetter("mandelbrot-def/copyright");
    digester.addSetProperties("mandelbrot-def",
      new String[] { "builtin", "name" },
      new String[] { "builtin", "name" }
    );
  }

  private void setFormulaRules() {
    // formula
    digester.addObjectCreate("mandelbrot-def/formulas/formula", "gtf.mandel.configuration.FormulaDesc");
    digester.addBeanPropertySetter("mandelbrot-def/formulas/formula/formula-name", "name");
    digester.addBeanPropertySetter("mandelbrot-def/formulas/formula/formula-classname", "classname");
    digester.addBeanPropertySetter("mandelbrot-def/formulas/formula/formula-displayname", "displayname");
    digester.addSetProperties("mandelbrot-def/formulas",
      new String[] { "default" },
      new String[] { "defaultFormulaName" }
    );
    digester.addSetNext("mandelbrot-def/formulas/formula", "addFormula", "gtf.mandel.configuration.FormulaDesc");
    // initial-region
    digester.addObjectCreate("mandelbrot-def/formulas/formula/initial-region", "gtf.mandel.model.mutable.RegionBean");
    digester.addSetProperties("mandelbrot-def/formulas/formula/initial-region",
      new String[] { "x", "y", "span" },
      new String[] { "x", "y", "span" }
    );
    digester.addSetNext("mandelbrot-def/formulas/formula/initial-region", "setRegion", "gtf.mandel.model.mutable.RegionBean");
    // julia-initial-region
    digester.addObjectCreate("mandelbrot-def/formulas/formula/julia-initial-region", "gtf.mandel.model.mutable.RegionBean");
    digester.addSetProperties("mandelbrot-def/formulas/formula/julia-initial-region",
      new String[] { "x", "y", "span" },
      new String[] { "x", "y", "span" }
    );
    digester.addSetNext("mandelbrot-def/formulas/formula/julia-initial-region", "setJuliaRegion", "gtf.mandel.model.mutable.RegionBean");
    // params
    digester.addObjectCreate("mandelbrot-def/formulas/formula/formula-params/formula-param", "gtf.mandel.configuration.FormulaParam");
    digester.addBeanPropertySetter("mandelbrot-def/formulas/formula/formula-params/formula-param/formula-param-name", "name");
    digester.addBeanPropertySetter("mandelbrot-def/formulas/formula/formula-params/formula-param/formula-param-value", "value");
    digester.addSetNext("mandelbrot-def/formulas/formula/formula-params/formula-param", "addParam", "gtf.mandel.configuration.FormulaParam");
  }

  private void setColourMapRules() {
    // colour map
    digester.addObjectCreate("mandelbrot-def/colour-models/colour-model", "gtf.mandel.configuration.ColorModelDesc");
    digester.addBeanPropertySetter("mandelbrot-def/colour-models/colour-model/colour-model-name", "name");
    digester.addBeanPropertySetter("mandelbrot-def/colour-models/colour-model/colour-model-classname", "classname");
    digester.addBeanPropertySetter("mandelbrot-def/colour-models/colour-model/colour-model-displayname", "displayname");
    digester.addSetNext("mandelbrot-def/colour-models/colour-model", "addColorModel", "gtf.mandel.configuration.ColorModelDesc");
    digester.addSetProperties("mandelbrot-def/colour-models",
      new String[] { "default" },
      new String[] { "defaultColorModelName" }
    );
    // params
    digester.addObjectCreate("mandelbrot-def/colour-models/colour-model/colour-model-params/colour-model-param", "gtf.mandel.configuration.ColorModelParam");
    digester.addBeanPropertySetter("mandelbrot-def/colour-models/colour-model/colour-model-params/colour-model-param/colour-model-param-name", "name");
    digester.addBeanPropertySetter("mandelbrot-def/colour-models/colour-model/colour-model-params/colour-model-param/colour-model-param-value", "value");
    digester.addSetNext("mandelbrot-def/colour-models/colour-model/colour-model-params/colour-model-param", "addParam", "gtf.mandel.configuration.ColorModelParam");
  }

  PluginDescriptor parse(InputStream in) throws PluginException {
    try {
      descriptor = (PluginDescriptor) digester.parse(in);
    } catch (IOException eieio) {
      throw new PluginException("Could not read plugin descriptor");
    } catch (SAXException sex) {
      throw new PluginException("Error parsing plugin descriptor");
    }
    descriptor.validate();
    return descriptor;
  }

  public PluginDescriptor getDescriptor() {
    return descriptor;
  }
}
