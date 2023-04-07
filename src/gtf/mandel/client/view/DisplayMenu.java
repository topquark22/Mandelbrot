package gtf.mandel.client.view;

import java.awt.*;

/**
 * Description of the Class
 *
 *@author     gfalk
 */
class DisplayMenu extends Menu {

  private static final long serialVersionUID = 1L;

  /**
   *Constructor for the DisplayMenu object
   *
   * @since
   */
  DisplayMenu() {
    super("Display");
    add(new MenuItem("Region"));
  }
}

