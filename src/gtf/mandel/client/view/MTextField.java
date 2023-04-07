package gtf.mandel.client.view;

import java.awt.*;
import java.awt.event.*;

/**
 *  A text field which keeps track of committed changes. Text turns red when
 *  changed. When the text is set programmatically, it turns black again. One
 *  way to use MTextField is to extend it by an implementation of
 *  ActionListener, which listens to its own ActionEvents. When the user hits
 *  "enter", this fires an ActionEvent. The text can then be validated. A call
 *  to MTextField.setText then turns the validated text black again. Otherwise
 *  the invalid text can be left as is.
 *
 *@author     gfalk
 */
class MTextField extends TextField implements KeyListener {

  private static final long serialVersionUID = 1L;
  
  /**
   * Colour of text when dirty
   */
  public final static Color DIRTY_COLOUR = Color.red;
  
  /**
   * Colour of text when clean
   */
  public final static Color CLEAN_COLOUR = Color.black;

  private boolean dirty = false;
  private boolean wipe = false;

  /**
   *  Constructor for the MTextField object
   *
   *@param  wipe  Whether to wipe existing text when new text is typed
   *@since
   */
  public MTextField(boolean wipe) {
    _MTextField(wipe);
  }
        
  /**
   *  Constructor for the MTextField object
   *
   *@param  width  Number of columns (see java.awt.TextField())
   *@param  wipe   Whether to wipe existing text when new text is typed
   */
  public MTextField(int width, boolean wipe) {
    super(width);
    _MTextField(wipe);
  }

  /**
   *  Constructor functionality
   *
   *@param  wipe  Whether to wipe existing text when new text is typed
   */
  private void _MTextField(boolean wipe) {
    this.wipe = wipe;
    setFont(new Font("Default", Font.PLAIN, 10));
    setBackground(Color.white);
    setDirty(false);
    addKeyListener(this);
  }

  /**
   *  Sets the dirty attribute of the MTextField object
   *
   *@param  dirty  The new dirty value
   */
  public void setDirty(boolean dirty) {
    if(wipe && dirty && !this.dirty)
      super.setText("");
    this.dirty = dirty;
    if (dirty) {
      setForeground(DIRTY_COLOUR);
    } else {
      setForeground(CLEAN_COLOUR);
    }
  }

  /**
   *  Indicate that text is out-of-date by changing colour to red.
   *
   *@param  e  the KeyEvent
   */
  public void keyTyped(KeyEvent e) {
    if (isEditable()) {
      if (wipe && !dirty) {
        setText("");
      }
      setDirty(true);
    }
  }

  /**
   *  Method of KeyListener interface
   *
   *@param  e  the KeyEvent
   */
  public void keyPressed(KeyEvent e) { }

  /**
   *  Method of KeyListener interface
   *
   *@param  e  the KeyEvent
   */
  public void keyReleased(KeyEvent e) { }
}

