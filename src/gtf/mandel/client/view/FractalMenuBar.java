package gtf.mandel.client.view;

import gtf.mandel.client.event.FractalControlListener;

import java.awt.MenuBar;

/**
 *Description of the Class
 *
 * @author     gfalk
 */
class FractalMenuBar extends MenuBar {

  private static final long serialVersionUID = 1L;
  
  FractalFileMenu fileMenu;
  /*
  Menu bookmarksMenu;
  Menu pluginsMenu;
  Menu optionsMenu;
  Menu helpMenu;
  */

  /**
   *Constructor for the MMenuBar object
   *
   * @since
   */
  FractalMenuBar() {
    fileMenu = new FractalFileMenu(); // FIXME needs reference to the canvas
    add(fileMenu);
    /*
    bookmarksMenu = new BookmarksMenu();
    add(bookmarksMenu);
    pluginsMenu = new PluginsMenu();
    add(pluginsMenu);
    optionsMenu = new OptionsMenu();
    add(optionsMenu);
    helpMenu = new HelpMenu();
    add(helpMenu);
    */
  }
  
  /**
   * 
   * @param listener
   */
  public void setListener(FractalControlListener listener) {
    fileMenu.setListener(listener);
  }

  public void setEnabled(boolean b) {
    fileMenu.setEnabled(b);
  }
}
