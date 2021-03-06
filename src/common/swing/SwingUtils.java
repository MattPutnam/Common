package common.swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Arrays;

import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

/**
 * A collection of utilities for shaping and laying out Swing components,
 * creating menu items and buttons, and running things on the Swing thread.
 * 
 * @author Matt Putnam
 */
public final class SwingUtils {
  private static int SHORTCUT_MASK = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();

  private SwingUtils() {}
  
  /**
   * Creates a JPanel containing the component hugging the left side
   * @param component the component to wrap
   * @return a JPanel containing the component hugging the left side
   */
  public static JPanel hugWest(JComponent component) {
    final JPanel result = new JPanel(new BorderLayout());
    result.add(component, BorderLayout.WEST);
    return result;
  }
  
  /**
   * Creates a JPanel containing the component hugging the top side
   * @param component the component to wrap
   * @return a JPanel containing the component hugging the top side
   */
  public static JPanel hugNorth(JComponent component) {
    final JPanel result = new JPanel(new BorderLayout());
    result.add(component, BorderLayout.NORTH);
    return result;
  }
  
  /**
   * Creates a JPanel containing the component hugging the right side
   * @param component the component to wrap
   * @return a JPanel containing the component hugging the right side
   */
  public static JPanel hugEast(JComponent component) {
    final JPanel result = new JPanel(new BorderLayout());
    result.add(component, BorderLayout.EAST);
    return result;
  }
  
  /**
   * Creates a JPanel containing the component hugging the bottom side
   * @param component the component to wrap
   * @return a JPanel containing the component hugging the bottom side
   */
  public static JPanel hugSouth(JComponent component) {
    final JPanel result = new JPanel(new BorderLayout());
    result.add(component, BorderLayout.SOUTH);
    return result;
  }
  
  /**
   * Creates a horizontal box with the given components
   * @param components the components to put in the box
   * @return a horizontal box with the given components
   */
  public static Box buildRow(Component... components) {
    final Box result = Box.createHorizontalBox();
    Arrays.stream(components).forEach(result::add);
    return result;
  }
  
  /**
   * Places the given components in a horizontal Box with a glue at the end,
   * causing the components to align left.
   * @param components the components to add
   * @return the components in a horizontal Box, aligned left
   */
  public static Box buildLeftAlignedRow(Component... components) {
    final Box result = Box.createHorizontalBox();
    Arrays.stream(components).forEach(result::add);
    result.add(Box.createHorizontalGlue());
    return result;
  }
  
  /**
   * Places the given components in a horizontal Box with a glue at the front,
   * causing the components to align right.
   * @param components the components to add
   * @return the components in a horizontal box, aligned right
   */
  public static Box buildRightAlignedRow(Component... components) {
    final Box result = Box.createHorizontalBox();
    result.add(Box.createHorizontalGlue());
    Arrays.stream(components).forEach(result::add);
    return result;
  }
  
  /**
   * Places the given components in a horizontal Box with a glue on both sides,
   * causing the components to align center
   * @param components the components to add
   * @return the components in a horizontal box, aligned center
   */
  public static Box buildCenteredRow(Component... components) {
    final Box result = Box.createHorizontalBox();
    result.add(Box.createHorizontalGlue());
    Arrays.stream(components).forEach(result::add);
    result.add(Box.createHorizontalGlue());
    return result;
  }
  
  /**
   * Places the given components in a JPanel with a FlowLayout.
   * @param components the components to add
   * @return a JPanel with a FlowLayout with the given components
   */
  public static JPanel flowLayout(Component... components) {
    final JPanel result = new JPanel(); // FlowLayout is default
    Arrays.stream(components).forEach(result::add);
    return result;
  }
  
  /**
   * Freezes the height of the given component, preventing it from expanding
   * to fill any additional space.
   * @param component the component to freeze
   * @return the component passed in, for chaining purposes
   */
  public static JComponent freezeHeight(JComponent component) {
    return freezeHeight(component, component.getPreferredSize().height);
  }
  
  /**
   * Freezes the height of the given component to the given size, preventing
   * it from expanding to fill any additional space.
   * @param component the component to freeze
   * @param height the height to freeze the component to
   * @return the component passed in, for chaining purposes
   */
  public static JComponent freezeHeight(JComponent component, int height) {
    component.setMinimumSize(new Dimension(component.getMinimumSize().width, height));
    component.setPreferredSize(new Dimension(component.getPreferredSize().width, height));
    component.setMaximumSize(new Dimension(component.getMaximumSize().width, height));
    return component;
  }
  
  /**
   * Freezes the width of the given component, preventing it from expanding
   * to fill any additional space.
   * @param component the component to freeze
   * @return the component passed in, for chaining purposes
   */
  public static JComponent freezeWidth(JComponent component) {
    return freezeWidth(component, component.getPreferredSize().width);
  }
  
  /**
   * Freezes the width of the given component to the given size, preventing
   * it from expanding to fill any additional space.
   * @param component the component to freeze
   * @param width the width to freeze the component to
   * @return the component passed in, for chaining purposes
   */
  public static JComponent freezeWidth(JComponent component, int width) {
    component.setMinimumSize(new Dimension(width, component.getMinimumSize().height));
    component.setPreferredSize(new Dimension(width, component.getPreferredSize().height));
    component.setMaximumSize(new Dimension(width, component.getMaximumSize().height));
    return component;
  }
  
  /**
   * Freezes the size of the given component, preventing it from expanding
   * to fill any additional space.
   * @param component the component to freeze
   * @return the component passed in, for chaining purposes
   */
  public static JComponent freezeSize(JComponent component) {
    return freezeSize(component, component.getPreferredSize().width, component.getPreferredSize().height);
  }
  
  /**
   * Freezes the size of the given component to the given size, preventing
   * it from expanding to fill any additional space.
   * @param component the component to freeze
   * @param width the width to freeze the component to
   * @param height the height to freeze the component to
   * @return the component passed in, for chaining purposes
   */
  public static JComponent freezeSize(JComponent component, int width, int height) {
    component.setMinimumSize(new Dimension(width, height));
    component.setPreferredSize(new Dimension(width, height));
    component.setMaximumSize(new Dimension(width, height));
    return component;
  }

  /**
   * Finds the Window containing this component, or null if none found.
   * @param component the component to search
   * @return the Window containing the component
   */
  public static Window findContainingWindow(Component component) {
    Container container = component.getParent();
    while (container != null && !(container instanceof Window)) {
      container = container.getParent();
    }
    return (Window) container;
  }

  /**
   * Finds the Frame containing this component, or null if none found.
   * @param component the component to search
   * @return the Frame containing the component
   */
  public static Frame findContainingFrame(Component component) {
    Container container = component.getParent();
    while (container != null && !(container instanceof Frame)) {
      container = container.getParent();
    }
    return (Frame) container;
  }
  
  /**
   * Finds the Dialog containing this component, or null if none found.
   * @param component the component to search
   * @return the Dialog containing the component
   */
  public static Dialog findContainingDialog(Component component) {
    Container container = component.getParent();
    while (container != null && !(container instanceof Dialog)) {
      container = container.getParent();
    }
    return (Dialog) container;
  }

  /**
   * Convenience method for creating a JMenu
   * @param text the menu title
   * @param mnemonic the mnemonic for the menu
   * @return a new JMenu with the given title and mnemonic
   */
  public static JMenu menu(String text, char mnemonic) {
    final JMenu menu = new JMenu(text);
    menu.setMnemonic(mnemonic);
    return menu;
  }
  
  /**
   * Convenience method for creating a JMenuItem
   * @param text the menu item label
   * @param icon the icon
   * @param action the action to perform when the menu item is selected
   * @return
   */
  public static JMenuItem menuItem(String text, Icon icon, ActionListener action) {
    final JMenuItem item = new JMenuItem(text, icon);
    item.addActionListener(action);
    return item;
  }

  /**
   * Convenience method for creating a JMenuItem
   * @param text the menu item label
   * @param accelerator the accelerator key (shortcut key) for the menu item
   * @param mnemonic the mnemonic for the menu item
   * @return a new JMenuItem with the given title, accelerator, and mnemonic
   */
  public static JMenuItem menuItem(String text, char accelerator,
      char mnemonic) {
    final JMenuItem item = new JMenuItem(text);
    item.setAccelerator(KeyStroke.getKeyStroke(accelerator, SHORTCUT_MASK));
    item.setMnemonic(mnemonic);
    return item;
  }

  /**
   * Convenience method for creating a JMenuItem
   * @param text the menu item label
   * @param accelerator the accelerator key (shortcut key) for the menu item
   * @param mnemonic the mnemonic for the menu item
   * @param action the action to perform when the menu item is activated
   * @return a new JMenuItem with the given title, accelerator, mnemonic, and action
   */
  public static JMenuItem menuItem(String text, char accelerator,
      char mnemonic, ActionListener action) {
    final JMenuItem item = new JMenuItem(text);
    item.setAccelerator(KeyStroke.getKeyStroke(accelerator, SHORTCUT_MASK));
    item.setMnemonic(mnemonic);
    item.addActionListener(action);
    return item;
  }

  /**
   * Convenience method for creating a JMenuItem
   * @param text the menu item label
   * @param accelerator the accelerator key (shortcut key) for the menu item
   * @param additionalMask any additional masks for the accelerator, for example
   *   {@link InputEvent#SHIFT_MASK}.  Multiple masks can be specified by XORing them together.
   * @param mnemonic the mnemonic for the menu item
   * @param action the action to perform when the menu item is activated
   * @return a new JMenuItem with the given title, accelerator, mnemonic, and action
   */
  public static JMenuItem menuItem(String text, char accelerator,
      int additionalMask, char mnemonic, ActionListener action) {
    final JMenuItem item = new JMenuItem(text);
    item.setAccelerator(KeyStroke.getKeyStroke(accelerator, SHORTCUT_MASK ^ additionalMask));
    item.setMnemonic(mnemonic);
    item.addActionListener(action);
    return item;
  }
  
  /**
   * Convenience method for creating a JButton
   * @param text the text of the button
   * @param onClick the ActionListener to add
   * @return a button with the given text and ActionListener
   */
  public static JButton button(String text, ActionListener onClick) {
    final JButton result = new JButton(text);
    result.addActionListener(onClick);
    return result;
  }
  
  /**
   * Convenience method for creating a JButton
   * @param text the text of the button
   * @param tooltip the tooltip for the button
   * @param onClick the ActionListener to add
   * @return a button with the given text and ActionListener
   */
  public static JButton button(String text, String tooltip, ActionListener onClick) {
    final JButton result = new JButton(text);
    result.setToolTipText(tooltip);
    result.addActionListener(onClick);
    return result;
  }
  
  /**
   * Convenience method for creating buttons with an icon and no text.  Wraps
   * the button in an empty border to give it some space.
   * @param icon the icon to display
   * @return a new JButton with the given icon
   */
  public static JButton iconButton(Icon icon) {
    final JButton result = new JButton(icon);
    result.setBorder(new EmptyBorder(4, 4, 4, 4));
    return result;
  }
  
  /**
   * Convenience method for creating buttons with an icon and no text.  Wraps
   * the button in an empty border to give it some space.
   * @param icon the icon to display
   * @param action the action to perform on click
   * @return a new JButton with the given icon
   */
  public static JButton iconButton(Icon icon, Action action) {
    final JButton result = new JButton(action);
    result.setIcon(icon);
    result.setBorder(new EmptyBorder(4, 4, 4, 4));
    return result;
  }
  
  /**
   * Causes the given frame to simply go invisible when closed.  Calls
   * {@link JFrame#setDefaultCloseOperation(int)} with <tt>DO_NOTHING_ON_CLOSE</tt>,
   * then adds a {@link WindowListener} that calls {@link JFrame#setVisible(boolean)}
   * with false in {@link WindowListener#windowClosing(WindowEvent)}
   * @param frame the JFrame to make go invisible on close
   */
  public static void goInvisibleOnClose(final JFrame frame) {
    frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    frame.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        frame.setVisible(false);
      }
    });
  }
  
  /**
   * Creates a {@link ButtonGroup} and adds all the given buttons to it.
   * @param buttons the buttons to group
   * @return the created ButtonGroup
   */
  public static ButtonGroup group(AbstractButton... buttons) {
    final ButtonGroup group = new ButtonGroup();
    Arrays.stream(buttons).forEach(group::add);
    return group;
  }
  
  /**
   * Creates a {@link ButtonGroup} and adds all the given buttons to it, then
   * selects the first button
   * @param buttons the buttons to group
   * @return the created ButtonGroup
   */
  public static ButtonGroup groupAndSelectFirst(AbstractButton... buttons) {
    final ButtonGroup group = group(buttons);
    if (buttons.length > 0)
      buttons[0].setSelected(true);
    return group;
  }
  
  /**
   * Runs the given logic in the Swing event dispatch thread.  If the calling
   * thread already is the Swing thread, the logic is simply run immediately
   * (and will block), otherwise the logic is put in the event queue.
   * @param run the logic to run in the Swing thread
   * @param wait whether or not to block until the logic has completed.  Only
   * applicable when the calling thread is not the Swing thread.
   */
  public static void doInSwing(final Runnable run, final boolean wait) {
    try {
      if (SwingUtilities.isEventDispatchThread()) {
        run.run();
      } else {
        if (wait)
          SwingUtilities.invokeAndWait(run);
        else
          SwingUtilities.invokeLater(run);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  /**
   * Causes <tt>runnable.run()</tt> to be run in the swing thread after a given delay
   * @param runnable the task to run in the swing thread
   * @param delayMillis the delay in milliseconds
   */
  public static void doDelayedInSwing(final Runnable runnable, final long delayMillis) {
    new Thread(() -> {
      try {
        Thread.sleep(delayMillis);
        SwingUtilities.invokeLater(runnable);
      } catch (InterruptedException e) {
        // ignore
      }
    }).start();
  }
  
  /**
   * Throws an IllegalStateException if this thread is the Swing Event Thread.
   * @throws IllegalStateException if this thread is the Swing Event Thread
   */
  public static void throwIfEventThread() {
    if (SwingUtilities.isEventDispatchThread())
      throw new IllegalStateException("This method should NOT be called from the Swing Event Thread");
  }
  
  /**
   * Throws an IllegalStateException if this thread is not the Swing Event Thread.
   * @throws IllegalStateException if this thread is not the Swing Event Thread
   */
  public static void throwIfNotEventThread() {
    if (!SwingUtilities.isEventDispatchThread())
      throw new IllegalStateException("This method should ONLY be called from the Swing Event Thread");
  }
}
