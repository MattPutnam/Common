package common.swing;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JPanel;

public class ColorPreviewPanel extends JPanel {
  private static final long serialVersionUID = 1L;
  
  private final JPanel _panel;
  private final JButton _button;
  
  private Color _selected;
  
  public ColorPreviewPanel(final Color initial) {
    _panel = new JPanel();
    _button = SwingUtils.button("Select", e -> {
      final Color selected = JColorChooser.showDialog(_panel, "Choose Color", _selected);
      if (selected != null) {
        _panel.setBackground(selected);
        _selected = selected;
      }
    });
    
    _selected = initial;
    
    SwingUtils.freezeSize(_panel, 40, _button.getPreferredSize().height-8);
    _panel.setBackground(initial);
    _panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    
    setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    add(_panel);
    add(_button);
    setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
  }
  
  public Color getSelectedColor() {
    return _selected;
  }
}
