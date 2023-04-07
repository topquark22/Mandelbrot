package gtf.mandel.client;

import gtf.mandel.common.ServerConnection;
import gtf.mandel.formula.Formula;

/**
 * Allows the client to get a connection to the server.
 *
 * @author     gfalk
 */
public interface Broker {

  /**
   * Get a new connection to the server for computing Mandelbrot sets,
   * using a given Formula.
   *
   * @param  formula                         The formula to use
   * @return                                 A connection to the server
   * @since
   */
  ServerConnection newConnection(Formula formula);
}
