package common.swing;

import java.util.LinkedList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import common.collection.BiMap;

/**
 * Utility for creating a panel of radio buttons to select from an enum.  The
 * radio button labels are generated from the enum values' <tt>toString</tt>
 * method.  Also provided is a mechanism for listening to changes to the
 * selection. 
 * 
 * @author Matt Putnam
 * @param <E> the enum type
 */
public class RadioButtonPanel<E extends Enum<E>> extends JPanel {
  private static final long serialVersionUID = 1L;

  private final BiMap<ButtonModel, E> _map;
  private final ButtonGroup _group;
  
  private final List<SelectionListener<E>> _listeners;
  
  /**
   * Creates a new RadioButtonPanel
   * @param enumValues all of the enum values, usually retrieved via
   *        <tt>EnumName.values()</tt>, but could be a subset as well.
   * @param initialSelection the initial element to select.  Cannot be null.
   * @param vertical <tt>true</tt> to lay out the buttons vertically,
   *                 </tt>false</tt> to lay them out horizontally
   */
  public RadioButtonPanel(E[] enumValues, E initialSelection, boolean vertical) {
    _map = new BiMap<>();
    _group = new ButtonGroup();
    
    _listeners = new LinkedList<>();
    
    setLayout(new BoxLayout(this, vertical ? BoxLayout.PAGE_AXIS : BoxLayout.LINE_AXIS));
    
    for (final E e : enumValues) {
      final JRadioButton button = new JRadioButton(e.toString());
      button.addActionListener(evt -> _listeners.forEach(l -> l.itemSelected(new SelectionEvent<>(e))));
      
      _group.add(button);
      _map.put(button.getModel(), e);
      
      add(button);
    }
    
    setSelectedValue(initialSelection);
  }
  
  /**
   * Sets the selected value.  If this changes the selection, listeners are
   * notified.
   * @param e the value to select
   */
  public void setSelectedValue(E e) {
    final E currentSelection = getSelectedValue();
    if (currentSelection != e) {
      _map.getReverse(e).setSelected(true);
      _listeners.forEach(l -> l.itemSelected(new SelectionEvent<>(e)));
    }
  }
  
  /**
   * @return the currently selected value
   */
  public E getSelectedValue() {
    return _map.get(_group.getSelection());
  }
  
  /**
   * Adds a {@link SelectionListener} to this
   * @param listener the listener to add
   */
  public void addSelectionListener(SelectionListener<E> listener) {
    _listeners.add(listener);
  }
  
  /**
   * Removes the given {@link SelectionListener}
   * @param listener the listener to remove
   */
  public void removeSelectionListener(SelectionListener<E> listener) {
    _listeners.remove(listener);
  }
  
  /**
   * Event for communicating selection changes.  Contains only the item
   * that was selected
   * 
   * @author Matt Putnam
   * @param <E> the enum type
   */
  public static class SelectionEvent<E extends Enum<E>> {
    private final E _selected;
    
    private SelectionEvent(E selected) {
      _selected = selected;
    }
    
    /**
     * @return the enum value that was selected in this event
     */
    public E getSelectedValue() {
      return _selected;
    }
  }
  
  /**
   * Listener for listening to radio button presses.
   * 
   * @author Matt Putnam
   * @param <E> the enum type
   */
  @FunctionalInterface
  public static interface SelectionListener<E extends Enum<E>> {
    public void itemSelected(SelectionEvent<E> e);
  }
}
