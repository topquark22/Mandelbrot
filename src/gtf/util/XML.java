package gtf.util;

import java.io.PrintStream;

/**
 *Description of the Class
 *
 *@author     gtf
 */
public class XML {
  /**
   *Description of the Method
   *
   *@param  out  Description of Parameter
   *@since
   */
  public static void putHeader(PrintStream out) {
    out.println("<?xml version=\"1.0\"?>");
  }

  /**
   *Description of the Method
   *
   *@param  width  Description of Parameter
   *@return        Description of the Returned Value
   *@since
   */
  public static String tab(int width) {
    StringBuffer buf = new StringBuffer();
    for (int i = 0; i < width; i++) {
      buf.append("  ");
    }
    return buf.toString();
  }
}
