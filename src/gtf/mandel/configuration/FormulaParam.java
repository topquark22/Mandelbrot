package gtf.mandel.configuration;

public class FormulaParam {

  private String paramName;
  private String paramValue;

  public void setName(String paramName) {
    this.paramName = paramName;
  }

  public String getName() {
    return paramName;
  }

  public void setValue(String paramValue) {
    this.paramValue = paramValue;
  }

  public String getValue() {
    return paramValue;
  }
}
