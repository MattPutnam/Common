package common.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import common.swing.icon.ArrowIcon;

/**
 * Panel that wraps another component and can be collapsed into just a header.
 * The CollapsiblePanel always starts expanded, use {@link #setExpanded(boolean)}
 * to programmatically expand or collapse the component.
 */
public class CollapsiblePanel extends JPanel {
  private static final long serialVersionUID = 1L;
  
  /**
   * {@link #HORIZONTAL} CollapsiblePanels display their wrapped component
   * to the right of the button and are suitable for long narrow horizontal
   * child components.  {@link VERTICAL} CollapsiblePanels display their
   * wrapped component below the button and are suitable for any child component.
   */
  public static enum Orientation { HORIZONTAL, VERTICAL }
  
  private final JComponent _child;
  private final JCheckBox _expandCheckBox;
  
  private boolean _expanded = true;
  
  /**
   * Creates a CollapsiblePanel
   * @param child the child component
   * @param orientation the Orientation
   * @param title the name for the expand/collapse button
   * @param tooltip the tooltip for the expand/collapse button
   * @param additionalComponents any additional components to put next to
   * the collapse button.  The components are laid out in a horizontal
   * {@link Box}, so use {@link Box#createHorizontalGlue()} and
   * {@link Box#createHorizontalStrut(int)} to control spacing
   */
  public CollapsiblePanel(JComponent child, Orientation orientation,
      String title, String tooltip, Component... additionalComponents) {
    _child = child;
    
    _expandCheckBox = new JCheckBox(title);
    _expandCheckBox.setBorder(new EmptyBorder(0, 4, 0, 0));
    _expandCheckBox.setToolTipText(tooltip);
    _expandCheckBox.setHorizontalTextPosition(JCheckBox.RIGHT);
    _expandCheckBox.setSelectedIcon(new ArrowIcon(ArrowIcon.SOUTH));
    _expandCheckBox.setIcon(new ArrowIcon(ArrowIcon.EAST));
    _expandCheckBox.setSelected(true);
    
    _expandCheckBox.addActionListener(e -> setExpanded(_expandCheckBox.isSelected()));
    
    final Box collapseBox = Box.createHorizontalBox();
    collapseBox.add(_expandCheckBox);
    for (final Component component: additionalComponents)
      collapseBox.add(component);
    
    setLayout(new BorderLayout());
    add(_child, BorderLayout.CENTER);
    add(collapseBox, orientation == Orientation.HORIZONTAL ? BorderLayout.WEST: BorderLayout.NORTH);
  }
  
  /**
   * Programmatically expands or collapses this CollapsiblePanel
   * @param expanded <tt>true</tt> to expand, <tt>false</tt> to collapse
   */
  public void setExpanded(boolean expanded) {
    if (_expanded != expanded) {
      _expanded = expanded;
      
      _expandCheckBox.setSelected(expanded);
      
      if (expanded) {
        add(_child, BorderLayout.CENTER);
      } else {
        remove(_child);
      }
      revalidate();
    }
  }
  
  /**
   * @return whether or not the CollapsiblePanel is expanded
   */
  public boolean isExpanded() {
    return _expanded;
  }
  
  @Override
  public void setFont(Font font) {
    super.setFont(font);
    if (_expandCheckBox != null)
      _expandCheckBox.setFont(font);
  }
  
  @Override
  public void setForeground(Color fg) {
    super.setForeground(fg);
    if (_expandCheckBox != null)
      _expandCheckBox.setForeground(fg);
  }
}
