package gtf.awt;

import java.awt.event.InputEvent;

/**
 *  Description of the Class
 *
 *@author     gtf
 */
public class InputMaskParser {

  /**
   *  Description of the Method
   *
   *@param  e  Description of Parameter
   *@return    Description of the Returned Value
   */
  public static boolean button1(InputEvent e) {
    int modifiers = e.getModifiers();
    return ((modifiers & InputEvent.BUTTON1_MASK) != 0);
  }

  /**
   *  Description of the Method
   *
   *@param  e  Description of Parameter
   *@return    Description of the Returned Value
   */
  public static boolean button2(InputEvent e) {
    int modifiers = e.getModifiers();
    return ((modifiers & InputEvent.BUTTON2_MASK) != 0);
  }

  /**
   *  Description of the Method
   *
   *@param  e  Description of Parameter
   *@return    Description of the Returned Value
   */
  public static boolean button3(InputEvent e) {
    int modifiers = e.getModifiers();
    return ((modifiers & InputEvent.BUTTON3_MASK) != 0);
  }
}
