package gtf.mandel.client.view;

import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/**
 * The Help menu in the main menu bar.
 *
 * @author     gfalk
 */
class HelpMenu extends Menu {

  private static final long serialVersionUID = 1L;

  /**
   * Constructor for the HelpMenu object
   */
  HelpMenu() {
    super("Help");
    MenuItem controlsItem = new MenuItem("Controls");
    controlsItem.addActionListener(new AbstractAction("controls") {

      private static final long serialVersionUID = 1L;

      public void actionPerformed(ActionEvent e) {
        System.out.println("'Controls' menu item pressed");
      }
    });
    add(controlsItem);
    MenuItem aboutItem = new MenuItem("About");
    aboutItem.addActionListener(new AbstractAction("about") {

      private static final long serialVersionUID = 1L;

      public void actionPerformed(ActionEvent e) {
        System.out.println("'About' menu item pressed");
      }
    });
    add(aboutItem);
  }
}
