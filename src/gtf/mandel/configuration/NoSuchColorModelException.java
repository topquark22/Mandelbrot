package gtf.mandel.configuration;

/**
 *  Description of the Class
 *
 *@author     gtf
 */
public class NoSuchColorModelException extends java.lang.Exception {

  private static final long serialVersionUID = 1L;
 
  private final String typeName;

  /**
   *  Constructor for the NoSuchColorModelException object
   *
   *@param  typeName  Description of Parameter
   */
  public NoSuchColorModelException(String typeName) {
    this.typeName = typeName;
  }


  /**
   *  Description of the Method
   *
   *@return    Description of the Returned Value
   */
  public String toString() {
    return ("NoSuchColorModelException: " + typeName);
  }
}
