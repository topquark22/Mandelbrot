package gtf.mandel.client.controller;

import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collection;

import gtf.mandel.client.LocalBroker;
import gtf.mandel.client.colour.ColorModelBase;
import gtf.mandel.client.event.LaunchEvent;
import gtf.mandel.client.event.LaunchListener;
import gtf.mandel.client.view.LaunchFrame;
import gtf.mandel.client.view.LaunchPanel;
import gtf.mandel.configuration.ColorModelDesc;
import gtf.mandel.configuration.Configuration;
import gtf.mandel.configuration.ConfigurationException;
import gtf.mandel.configuration.FormulaDesc;
import gtf.mandel.formula.Formula;
import gtf.mandel.model.Region;


/**
 * The controller for the main (initial) frame.
 * 
 * @author gtf
 */
public class MainLauncher implements LaunchListener {

  private LaunchFrame frame;

  /**
   * The main panel controller
   */
  private LaunchPanel panel;

  private Configuration configuration;

  /**
   * Constructor for the LaunchController object.
   */
  public MainLauncher() {
    try {
      configuration = Configuration.getInstance();
    } catch (ConfigurationException e) {
      throw new RuntimeException("Cannot configure application", e);
    }// TODO: Exception handling
    frame = new LaunchFrame();
    frame.setLayout(new GridLayout(1, 1));
    panel = makePanel();
    panel.addListener(this);
    frame.setSize(panel.getPreferredSize());
    frame.add(panel);
    frame.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
  }

  private LaunchPanel makePanel() {
    LaunchPanel panel = new LaunchPanel();
    // populate formulas
    Collection<FormulaDesc> formulas = configuration.getFormulas();
    for (FormulaDesc desc : formulas) {
      panel.addFormula(desc.getName());
    }
    // populate colour models
    Collection<ColorModelDesc> colorModels = configuration.getColorModels();
    for (ColorModelDesc desc : colorModels) {
      panel.addColorModel(desc.getName());
    }
    return panel;
  }

  public void start() {
    frame.init();
  }

  public void fractalRequested(LaunchEvent event) {
    String formulaName = event.getFormulaName();
    String colorModelName = event.getColorModelName();
    int limit = event.getLimit();
    Region region = event.getRegion();

    try {
      Formula formula = configuration.getFormulaByName(formulaName);
      ColorModelBase colorModel = configuration
          .getColorModelByName(colorModelName);
      FractalLauncher fl = new FractalLauncher(new LocalBroker(), formula,
          colorModel, region, limit);
      if (event.isJulia()) {
        fl.createJulia(event.getJuliaValue(), region);
      } else {
        fl.createMandelbrot();
      }
      
    } catch (Exception e) {
      // TODO -- pop up error panel
      e.printStackTrace();
    }
  }

  /**
   * Main application entry point.
   * 
   * @param args
   */
  public static void main(String[] args) {
    new MainLauncher().start();
  }
}
