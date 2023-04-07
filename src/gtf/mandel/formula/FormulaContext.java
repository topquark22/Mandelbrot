package gtf.mandel.formula;

import java.util.Collection;
import gtf.mandel.configuration.FormulaParam; // TODO: Do not access directly
import gtf.mandel.model.Region;
import gtf.mandel.client.ContextBase;

/**
 * A context for a formula, containing the name of the formula along with any
 * custom parameters. Encapsulates parameters about a formula that were specified
 * in the deployment descriptor.
 *
 *@author   gtf
 */
public class FormulaContext extends ContextBase {

  private static final long serialVersionUID = 1L;

  private Region initialRegion;

  /**
   * Initial Julia region, or null if Julia not supported
   */
  private Region initialJuliaRegion;

  /**
   * Sets the initial starting region.
   *
   *@param initialRegion the new initial starting region
   */
  public void setInitialRegion(Region initialRegion) {
    this.initialRegion = initialRegion;
  }

  /**
   * Get the initial starting region
   *
   *@return   The initial starting region
   */
  public Region getInitialRegion() {
    return initialRegion;
  }

  /**
   * Sets the initial starting region for Julia sets.
   *
   *@param initialJuliaRegion the new initial starting region for Julia sets
   */
  public void setInitialJuliaRegion(Region initialJuliaRegion) {
    this.initialJuliaRegion = initialJuliaRegion;
  }

  /**
   * Get the initial starting region for Julia sets
   *
   *@return   The initial starting region for Julia sets
   */
  public Region getInitialJuliaRegion() {
    return initialJuliaRegion;
  }

  public void setAttributes(Collection<FormulaParam> attrs) {
    for (FormulaParam param : attrs) {
      setAttribute(param.getName(), param.getValue());
    }
  }
}
