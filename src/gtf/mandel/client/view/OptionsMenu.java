package gtf.mandel.client.view;

import java.awt.*;

/**
 *Description of the Class
 *
 * @author     gfalk
 */
class OptionsMenu extends Menu {

  private static final long serialVersionUID = 1L;

  /**
   *Constructor for the OptionsMenu object
   *
   * @since
   */
  OptionsMenu() {
    super("Options");
    add(new MenuItem("Exponent"));
    add(new MenuItem("Colours"));
    add(new MenuItem("Size"));
  }
}

