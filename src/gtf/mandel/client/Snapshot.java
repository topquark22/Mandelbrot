package gtf.mandel.client;

import java.awt.image.ImageProducer;
import gtf.mandel.client.colour.*;
import gtf.mandel.model.*;

/**
 *  Responsible for converting and storing images. Also keeps track of
 *  which region and limit values correspond to the image.
 *
 *@author     gfalk
 */
public class Snapshot {

  /**
   * The dataset including image data
   */
  protected ImageProducer producer;

  /**
   * The region in the complex plane
   */
  protected Region region;

  /**
   * The iteration limit
   */
  protected int limit;

  /**
   * Constructor for the Snapshot object
   *
   *@param  dataset  The dataset including image data
   *@param  region   The region in the complex plane
   *@param  limit    The iteration limit
   */
  public Snapshot(MDataset dataset, ColorModelBase colorModel, Region region, int limit) {
    producer = new MImageProducer(dataset, colorModel);
    this.region = region;
    this.limit = limit;
  }

  /**
   * Gets the image
   *
   *@return    The image
   */
  public ImageProducer getProducer() {
    return producer;
  }

  /**
   * Gets the region attribute of the Snapshot object
   */
  public Region getRegion() {
    return region;
  }

  /**
   * Gets the limit attribute of the Snapshot object
   *
   *@return    The iteration limit
   */
  public int getLimit() {
    return limit;
  }
}
