package gtf.mandel.client.view;

import java.awt.*;
import java.awt.event.ActionListener;

/**
 *Description of the Class
 *
 * @author     gfalk
 */
class LaunchMenuBar extends MenuBar {

  private static final long serialVersionUID = 1L;
  
  private Menu fileMenu;
  private Menu bookmarksMenu;
  private Menu pluginsMenu;
  private Menu optionsMenu;
  private Menu helpMenu;

  /**
   *Constructor for the MMenuBar object
   *
   * @since
   */
  LaunchMenuBar() {
    fileMenu = new LaunchFileMenu();
    add(fileMenu);
    bookmarksMenu = new BookmarksMenu();
    add(bookmarksMenu);
    /*
    pluginsMenu = new PluginsMenu();
    add(pluginsMenu);
    */
    optionsMenu = new OptionsMenu();
    add(optionsMenu);

    helpMenu = new HelpMenu();
    add(helpMenu);
  }

  public void addListener(ActionListener listener) {
    fileMenu.addActionListener(listener);
    bookmarksMenu.addActionListener(listener);
    pluginsMenu.addActionListener(listener);
    optionsMenu.addActionListener(listener);
    helpMenu.addActionListener(listener);
  }
}
