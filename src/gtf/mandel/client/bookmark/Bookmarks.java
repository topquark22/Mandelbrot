package gtf.mandel.client.bookmark;

import java.util.Vector;


/**
 * Store a folder of bookmarks and info about the bookmarks file.
 *
 *@author     gfalk
 */
public final class Bookmarks {

  private Folder folder;

  /**
   * The bookmarks.
   *
   *@since v7.0
   */
  protected Vector<Bookmark> bookmarks;

  /**
   *Constructor for the Bookmarks object
   *
   *@since
   */
  public Bookmarks() {
    bookmarks = new Vector<Bookmark>();
  }

  /**
   *Gets the summary names to display (e.g. in a dropdown list)
   *
   *@return    The displayNames value
   *@since
   */
  public String[] displayNames() {
    String[] names = new String[bookmarks.size()];
    int j = 0;
    for (Bookmark b: bookmarks) {
      names[j++] = b.getDescription();
    }
    return names;
  }

  public void validate() throws BookmarksException {
    // if needed
  }

  public void setFolder(Folder folder) {
    this.folder = folder;
  }

  Folder getFolder() {
    return folder;
  }
}
