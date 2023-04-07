package gtf.mandel.client.view;


import java.awt.Frame;

/**
 *  A frame to contain the MainPanel.
 *
 *@author     gfalk
 */
public class LaunchFrame extends Frame {

  private static final long serialVersionUID = 1L;

  /**
   *  Constructor for the Main Frame object.
   */
  public LaunchFrame() {
//  setIconImage(new MIconImage());
    setMenuBar(new LaunchMenuBar()); // in progress
  }

  /**
   * Starts the frame.
   */
  public void init() {
    pack();
    setVisible(true);
  }
}

