package gtf.mandel.client.view;

import java.awt.*;

/**
 * Bookmarks menu for the main window.
 *
 * @author     gtf
 */
class BookmarksMenu extends Menu {

  private static final long serialVersionUID = 1L;

  /**
   * Constructor for the BookmarksMenu object
   */
  BookmarksMenu() {
    super("Bookmarks");
    add(new MenuItem("New bookmark"));
    add(new MenuItem("Edit bookmarks"));
    add(new MenuItem("-"));
    /* ... populate from bookmarks.xml */
    // ...
  }
}

