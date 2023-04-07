package gtf.mandel.client.view;

import gtf.mandel.client.event.FractalControlListener;

import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


/**
 *  A frame to contain the FractalPanel.
 *
 *@author     gfalk
 */
public abstract class FractalFrame extends Frame {

  private static final long serialVersionUID = 1L;
  
  /**
   *  Description of the Field
   *
   *@since
   */
  protected FractalPanel panel;
  private FractalMenuBar menuBar;
  private FractalControlListener listener;

  /**
   *  Constructor for the FractalFrame object.
   */
  public FractalFrame(int limit) {
    addWindowListener(
      new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
          FractalFrame.this.windowClosing();
          dispose();
        }
      }
    );
//  setIconImage(new MIconImage());
    setMenuBar(menuBar = new FractalMenuBar());
    setLayout(new GridLayout(1, 1));
    panel = new MBoxPanel(limit);
    setSize(panel.getPreferredSize());
    add(panel);
    pack();
    setVisible(true);
  }

  /**
   *  Gets the panel attribute of the FractalFrame object
   *
   *@return    The panel value
   */
  public FractalPanel getPanel() {
    return panel;
  }

  public void setListener(FractalControlListener listener) {
    this.listener = listener;
    panel.setListener(listener);
    menuBar.setListener(listener);
  }

  public void enableControls(boolean b) {
    panel.enableControls(b);
    menuBar.setEnabled(b);
  }
  
  private void windowClosing() {
    if (listener != null) {
      listener.terminate();
    }
  }
}

