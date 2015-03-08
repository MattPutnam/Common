package common.swing;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import common.collection.BiMap;

public class RadioButtonPanel<E extends Enum<E>> extends JPanel {
  private static final long serialVersionUID = 1L;

  private final BiMap<ButtonModel, E> _map;
  private final ButtonGroup _group;
  
  public RadioButtonPanel(E[] enumValues, E initialSelection, boolean vertical) {
    _map = new BiMap<>();
    _group = new ButtonGroup();
    
    setLayout(new BoxLayout(this, vertical ? BoxLayout.PAGE_AXIS : BoxLayout.LINE_AXIS));
    
    for (final E e : enumValues) {
      final JRadioButton button = new JRadioButton(e.toString());
      _group.add(button);
      _map.put(button.getModel(), e);
      
      add(button);
    }
    
    setSelectedValue(initialSelection);
  }
  
  public void setSelectedValue(E e) {
    _map.getReverse(e).setSelected(true);
  }
  
  public E getSelectedValue() {
    return _map.get(_group.getSelection());
  }
}
