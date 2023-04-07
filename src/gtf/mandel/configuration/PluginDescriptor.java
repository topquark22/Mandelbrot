package gtf.mandel.configuration;

import java.util.Vector;
import java.util.Collection;
import java.util.Collections;

public class PluginDescriptor //implements FormulaDescriptor, ColorModelDescriptor
{

  private String name;
  private boolean builtin = false;
  private String description;
  private String author;
  private String copyright;
  private String defaultFormulaName = null;
  private String defaultColorModelName = null;

  private final Vector<FormulaDesc> formulas = new Vector<FormulaDesc>(); // TEMP
  private final Vector<ColorModelDesc> colourModels = new Vector<ColorModelDesc>(); // TEMP

  public void setName(String name) {
    this.name = name;
  }

  public void setBuiltin(boolean builtin) {
    this.builtin = builtin;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getAuthor() {
    return author;
  }

  public void setCopyright(String copyright) {
    this.copyright = copyright;
  }

  public String getCopyright() {
    return copyright;
  }

  public void addFormula(FormulaDesc desc) {
    formulas.add(desc);
  }

  Collection<FormulaDesc> getFormulas() {
    return Collections.unmodifiableCollection(formulas);
  }

  public void setDefaultFormulaName(String defaultFormulaName) {
    this.defaultFormulaName = defaultFormulaName;
  }

  public String getDefaultFormulaName() {
    return defaultFormulaName;
  }

  public void addColorModel(ColorModelDesc desc) {
    colourModels.add(desc);
  }

  public void setDefaultColorModelName(String defaultColorModelName) {
    this.defaultColorModelName = defaultColorModelName;
  }

  public String getDefaultColorModelName() {
    return defaultColorModelName;
  }

  Collection<ColorModelDesc> getColorModels() {
    return Collections.unmodifiableCollection(colourModels);
  }

  public void validate() throws PluginException {
    if (!builtin) {
      if (defaultFormulaName != null) {
        throw new PluginException("default formula only supported for builtin");
      }
      if (defaultColorModelName != null) {
        throw new PluginException("default colour model only supported for builtin");
      }
    } else {
      if (defaultFormulaName == null) {
        throw new PluginException("default formula required for builtin");
      }
      // TODO: make sure the default formula actually exists
      if (defaultColorModelName == null) {
        throw new PluginException("default colour model required for builtin");
      }
      // TODO: make sure the default colour model actually exists
    }
  }

  public String toString() {
    String head = getDescription() + "\n" + getAuthor() + "\n" + getCopyright() + "\n";
    head += "Default formula name = " + defaultFormulaName + "\n";
    head += "Default colour model name = " + defaultColorModelName + "\n";
    head += "Builtin = " + builtin + "\n";
    head += "Name = " + name + "\n";

    String f = "Formulas:\n";
    for (FormulaDesc desc : formulas) {
      f += desc.toString() + "\n";
    }

    String c = "Colour models:\n";
    for (ColorModelDesc desc : colourModels) {
      c += desc.toString() + "\n";
    }
    return head + "\n" + f + "\n" + c + "\n";
  }
}
