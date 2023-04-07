package gtf.mandel.configuration;

/**
 *  Description of the Class
 *
 *@author     gtf
 */
public class NoSuchFormulaException extends java.lang.Exception {

  private static final long serialVersionUID = 1L;
  
  private final String typeName;

  /**
   *  Constructor for the NoSuchFormulaException object
   *
   *@param  typeName  Description of Parameter
   */
  public NoSuchFormulaException(String typeName) {
    this.typeName = typeName;
  }


  /**
   *  Description of the Method
   *
   *@return    Description of the Returned Value
   */
  public String toString() {
    return ("NoSuchFormulaException: " + typeName);
  }
}
