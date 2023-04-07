package gtf.mandel.client.view;

import java.awt.Color;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gtf.mandel.client.event.FractalControlListener;


/**
 *  Description of the Class
 *
 *@author     gfalk
 */
class LimitField extends TextField /* MTextField */ implements ActionListener {

  private static final long serialVersionUID = 1L;
  
  private int value;
  private FractalControlListener listener;

  /**
   *  Constructor for the LimitField object
   *
   *@param  value  Initial limit
   *@since
   */
  public LimitField(int value) {
 // super(8, false);   // default width; no wipe
    super(8);
    setBackground(Color.white);
    this.value = value;
    addActionListener(this);
  }
  /**
   *  Sets the limit attribute of the LimitField object
   *
   *@param  value  The new limit value
   *@since
   */
  public void setValue(int value) {
    this.value = value;
    setText(Integer.toString(value));
  }

  /**
   *  Gets the limit attribute of the LimitField object
   *
   *@return    The limit value
   *@since
   */
  public int getValue() {
    return value;
  }

  /**
   *  Description of the Method
   *
   *@param  e  Description of Parameter
   *@since
   */
  public void actionPerformed(ActionEvent e) {
    String s = e.getActionCommand();
    try {
      int newValue = Integer.parseInt(s);
      int oldValue = value;
      setValue(newValue);
      if (oldValue != newValue) {
        notifyListener();
      }
    } catch (NumberFormatException f) {
      setValue(value);
    }
  }

  /**
   * 
   * @param listener
   */
  public void setListener(FractalControlListener listener) {
    this.listener = listener;
  }

  /**
   *  Description of the Method
   *
   *@since
   */
  protected void notifyListener() {
    if(listener != null) {
      listener.limitChanged(value);
    }
  }
}

