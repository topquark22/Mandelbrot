package gtf.mandel.client.view;


/**
 * File menu for the main screen.
 *
 * @author     gtf
 */
class LaunchFileMenu extends MenuSupport {

  private static final long serialVersionUID = 1L;

  /**
   *Constructor for the MainFileMenu object
   */
  LaunchFileMenu() {
    super("File");
    addItem("Go to..", false);
    addItem("Open snapshot", false);
  }
}
