package gtf.mandel.client.view;

import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.event.ActionListener;


public class MenuSupport extends Menu {
  
  private static final long serialVersionUID = 1L;

  public MenuSupport(String name) {
    super(name);
  }

  protected MenuItem addItem(String name, boolean enabled) {
    MenuItem item = new MenuItem(name);
    item.setEnabled(enabled);
    add(item);
    return item;
  }

  protected MenuItem addItem(String name, ActionListener callback) {
    MenuItem item = new MenuItem(name);
    item.setEnabled(callback != null);
    item.addActionListener(callback);
    add(item);
    return item;
  }
}
