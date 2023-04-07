package gtf.mandel.client.view;

import java.awt.BorderLayout;

/**
 *  MFlexPanel is the main Panel that displays a fractal and
 *  the associated controls, subsequent to the window being resized.
 *  The size of the canvas is set according to the layout
 *  manager constraints (unlike MBoxPanel, which sets the size of the
 *  panel based on the initial size of the MCanvas).
 *
 *  When a FractalFrame is opened, it initially uses an MBoxPanel.
 *  If the frame is resized, the MBoxPanel is replaced by an MFlexPanel.
 *
 * @author     gfalk
 */
class MFlexPanel extends FractalPanel {

  private static final long serialVersionUID = 1L;

  /**
   * Constructor for the MFlexPanel object.
   */
  public MFlexPanel(int limit) {
    setLayout(new BorderLayout());
    displayBar = new DisplayBar();
    add(displayBar, BorderLayout.NORTH);
    canvas = new FractalCanvas(defaults.getCopyrightInfo());
    add(canvas, BorderLayout.CENTER);
    controlBar = new ControlBar(limit);
    add(controlBar, BorderLayout.SOUTH);
  }
}

