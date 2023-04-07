package gtf.mandel.client.controller;

import java.util.Stack;

import gtf.mandel.common.ServerConnection;
import gtf.mandel.client.Snapshot;
import gtf.mandel.client.colour.ColorModelBase;
import gtf.mandel.client.view.FractalFrame;
import gtf.mandel.model.Region;

/**
 * An extension of FractalController that can keep a history
 * of the current session.
 *
 * @author     gfalk
 */
class BacktrackableFractalController extends FractalController {

  private Stack<Snapshot> stack;

  /**
   *Constructor for the MStackController object.
   *
   * @param frame       The frame controlled by this controller
   * @param connection  The server connection for this controller
   */
  BacktrackableFractalController(FractalFrame frame, ServerConnection connection, ColorModelBase colorModel, Region initialRegion, int initialLimit) {
    super(frame, connection, colorModel, initialRegion, initialLimit);
    stack = new Stack<Snapshot>();
  }

  /**
   * What do do with a new snapshot besides display it:
   * In this case we save a copy.
   *
   * @param  snap  Description of Parameter
   * @since
   */
  protected void update(Snapshot snap) {
    stack.push(snap);
    super.update(snap);
  }

  /**
   * What happens when back is pushed:
   * In this case we update to previous snap.
   */
  public void backPressed() {
    Snapshot snap;
    switch (stack.size()) {
    case 0:
      snap = null;
      break;
    case 1:
      snap = (Snapshot) stack.peek();
      break;
    default:
      stack.pop();
      snap = stack.lastElement();
    }
    super.update(snap);
  }

  /**
   * What happens when reset is pushed:
   * Update to initial (first) image if there is one, and clear history.
   */
  public void resetPressed() {
    if (!stack.empty()) {
      Snapshot first = stack.firstElement();
      stack = new Stack<Snapshot>();
      stack.push(first);
      super.update(first);
    }
  }
  
  /**
   * Clear the history if the panel was resized (prevents gibble).
   *
   * @since
   */
  public void panelResized() {
    stack = new Stack<Snapshot>();
    super.panelResized();
  }
}
