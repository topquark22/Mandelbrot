package gtf.mandel.client.view;

import gtf.mandel.client.event.FractalControlListener;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

/**
 * File menu for an open fractal window.
 *
 * @author     gfalk
 */
class FractalFileMenu extends MenuSupport {

  private static final long serialVersionUID = 1L;
  
  /**
   * Listener to receive events.
   */
  private FractalControlListener listener;
  
  /**
   * Constructor for the FractalFileMenu object
   */
  FractalFileMenu() {
    super("File");
    addItem("Save location", null);
    addItem("Save image", new AbstractAction("saveImage") {

      private static final long serialVersionUID = 1L;

      public void actionPerformed(ActionEvent e) {
        if (listener != null) {
          listener.saveImageRequested();
        }
      }
    });
  }
  
  /**
   * 
   * @param listener
   */
  public void setListener(FractalControlListener listener) {
    this.listener = listener;
  }
}
