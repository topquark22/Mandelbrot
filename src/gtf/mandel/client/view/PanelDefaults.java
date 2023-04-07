package gtf.mandel.client.view;

import gtf.mandel.client.CopyrightInfo;
import gtf.mandel.client.Defaults;

import java.awt.Dimension;

/**
 * Contains defaults for a new FractalPanel. Note that we do not specify a Region;
 * this is set by MandelbrotPanel or JuliaPanel, because they could have
 * different default regions.
 *
 *@author     gtf
 */
class PanelDefaults {

  CopyrightInfo copyrightInfo;
  int limit;
  Dimension canvasSize;

  /**
   * Constructor for the PanelDefaults object.
   *
   *@param  copyrightInfo  The copyright information
   *@param  limit          The calculation limit
   *@param  canvasSize     The canvas size
   */
  PanelDefaults(CopyrightInfo copyrightInfo, int limit, Dimension canvasSize) {
    this.copyrightInfo = copyrightInfo;
    this.limit = limit;
    this.canvasSize = canvasSize;
  }

  /**
   * Constructor for the PanelDefaults object.
   */
  public PanelDefaults() {
    this.copyrightInfo = new CopyrightInfo();
    Defaults defaults = Defaults.getInstance();
    this.limit = defaults.getLimit();
    this.canvasSize = defaults.getCanvasSize();
  }

  /**
   * Gets the copyright information.
   */
  public CopyrightInfo getCopyrightInfo() {
    return copyrightInfo;
  }

  /**
   * Gets the canvas size.
   */
  public Dimension getCanvasSize() {
    return canvasSize;
  }

  /**
   * Gets the calculation limit.
   */
  public int getLimit() {
    return limit;
  }
}
