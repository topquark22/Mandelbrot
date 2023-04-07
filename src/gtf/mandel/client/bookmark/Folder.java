package gtf.mandel.client.bookmark;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Store a list of bookmarks.
 *
 *@author     gfalk
 */
public final class Folder implements Bookmarkable {

  /**
   * The bookmarks.
   *
   * @type List<Bookmarkable>
   */
  private List<Bookmarkable> bookmarks;

  /**
   * The title.
   */
  private String title;

  /**
   * Constructor for the Bookmarks object.
   */
  public Folder() {
    bookmarks = new ArrayList<Bookmarkable>();
  }

  /**
   * Sets the title.
   *
   * @param title The new title
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * Gets the title.
   */
  public String getTitle() {
    return title;
  }

  /**
   * Add a bookmark to the list.
   *
   *@param  bookmark  The bookmark to add.
   *@since
   */
  public void addBookmark(Bookmark bookmark) {
    bookmarks.add(bookmark);
  }

  /**
   * Add a folder to the list.
   *
   *@param  folder  The folder to add.
   *@since
   */
  public void addFolder(Folder folder) {
    bookmarks.add(folder);
  }

  /**
   * Gets the number of bookmarks listed.
   */
  public int getSize() {
    int size = 0;
    Iterator<Bookmarkable> i = bookmarks.iterator();
    while (i.hasNext()) {
      Bookmarkable bm = i.next();
      size += bm.getSize();
    }
    return size;
  }

  /**
   * Returns an immutable List of all the bookmarks.
   */
  public List<Bookmarkable> getBookmarks() {
    return Collections.unmodifiableList(bookmarks);
  }

  public String toString() {
    StringBuffer buf = new StringBuffer("[");
    if (title != null) {
      buf.append("title=\"" + title + "\", ");
    }
    Iterator<Bookmarkable> i = bookmarks.iterator();
    while (i.hasNext()) {
      Bookmarkable bm = i.next();
      buf.append(bm.toString());
      if (i.hasNext()) {
        buf.append(", ");
      }
    }
    buf.append("]");
    return buf.toString();
  }
}
