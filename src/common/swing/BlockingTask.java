package common.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.RootPaneContainer;
import javax.swing.SwingConstants;

/**
 * Task which runs on a non-Swing thread and blocks the UI while it runs.
 * 
 * @author Matt Putnam
 */
public final class BlockingTask extends Thread {
  private final RootPaneContainer _rootPaneContainer;
  private final Runnable _logic;
  
  private final Component _savedGlass;
  private final Component _glass;
  
  /**
   * Creates a new BlockingTask with a given window and logic.  Uses a default
   * panel for blocking.
   * @param rootPaneContainer the top level frame or dialog to block
   * @param logic the logic to run in a non-Swing thread
   */
  public BlockingTask(RootPaneContainer rootPaneContainer, Runnable logic) {
    this(rootPaneContainer, logic, createDefaultGlass());
  }
  
  /**
   * Creates a new BlockingTask with a given window, logic, and blocking component
   * @param rootPaneContainer the top level frame or dialog to block
   * @param logic the logic to run in a non-Swing thread
   * @param blocker the Component to use to block the UI
   */
  public BlockingTask(RootPaneContainer rootPaneContainer, Runnable logic, Component blocker) {
    _rootPaneContainer = rootPaneContainer;
    _logic = logic;
    
    _savedGlass = _rootPaneContainer.getGlassPane();
    _glass = blocker;
  }
  
  @Override
  public void run() {
    SwingUtils.doInSwing(new Runnable() {
      @Override
      public void run() {
        _rootPaneContainer.setGlassPane(_glass);
        _glass.setVisible(true);
      }
    }, true);
    
    _logic.run();
    
    SwingUtils.doInSwing(new Runnable() {
      @Override
      public void run() {
        _glass.setVisible(false);
        _rootPaneContainer.setGlassPane(_savedGlass);
      }
    }, false);
  }
  
  private static JPanel createDefaultGlass() {
    final JPanel result = new JPanel(new BorderLayout());
    
    final JLabel label = new JLabel("Please wait...", SwingConstants.CENTER);
    label.setFont(label.getFont().deriveFont(Font.ITALIC));
    result.add(label, BorderLayout.CENTER);
    
    result.setBackground(new Color(0, 0, 0, 10));
    result.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    return result;
  }
}
