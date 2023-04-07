package gtf.mandel.client.event;

import java.util.EventListener;

/**
 * A listener for events from the main application window.
 *
 *@author   gtf
 */
public interface LaunchListener extends EventListener {

  public void fractalRequested(LaunchEvent e);

}

