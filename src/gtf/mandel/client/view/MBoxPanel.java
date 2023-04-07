package gtf.mandel.client.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.BoxLayout;

/**
 *  MBoxPanel is the main Panel that displays a canvas and
 *  the associated controls, prior the window being resized.
 *  The size of the MCanvas is set according to the initial
 *  parameters (unlike MFlexPanel, which sets the size
 *  of the MCanvas based on the layout manager constraints).
 *
 *  When a FractalFrame is opened, it initially uses an MBoxPanel.
 *  If the frame is resized, the MBoxPanel is replaced by an
 *  MFlexPanel.
 *
 * @author     gfalk
 */
class MBoxPanel extends FractalPanel {

  private static final long serialVersionUID = 1L;

  /**
   *Constructor for the MBoxPanel object
   */
  MBoxPanel(int limit) {
    Dimension canvasSize = defaults.getCanvasSize();
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    add(displayBar = new DisplayBar());
    canvas = new FractalCanvas(defaults.getCopyrightInfo(), canvasSize);
    add(canvas);
    add(controlBar = new ControlBar(limit));
    addComponentListener(
      new ComponentAdapter() {
        public void componentResized(ComponentEvent e) {
          LayoutManager layout = getLayout();
          if (layout != null && !(layout instanceof BorderLayout)) {
            removeAll();
            setLayout(new BorderLayout());
            add(displayBar, BorderLayout.NORTH);
            add(canvas, BorderLayout.CENTER);
            add(controlBar, BorderLayout.SOUTH);
          }
        }
      }
    );
  }
}

