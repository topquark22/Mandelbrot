package gtf.mandel.client.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *  Description of the Class
 *
 *@author     gfalk
 */
class DTextField extends MTextField implements ActionListener {

  private static final long serialVersionUID = 1L;
  
  private double value;

  /**
   *  Constructor for the DTextField object
   */
  public DTextField() {
    super(true);        // wipe
    addActionListener(this);
  }

  /**
   *  Sets the value attribute of the DTextField object
   *
   *@param  value  The new value value
   */
  public void setValue(double value) {
    setText(Double.toString(value));
    this.value = value;
  }

  /**
   *  Gets the value attribute of the DTextField object
   *
   *@return    The value value
   */
  public double getValue() {
    return value;
  }

  /**
   *  Description of the Method
   *
   *@param  e  Description of Parameter
   */
  public synchronized void actionPerformed(ActionEvent e) {
    String s = e.getActionCommand();
    try {
      double newValue = Double.parseDouble(s);
      setValue(newValue);
    } catch (NumberFormatException f) {
      setValue(value);
    }
  }
}

