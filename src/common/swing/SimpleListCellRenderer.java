package common.swing;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

public abstract class SimpleListCellRenderer<T> extends DefaultListCellRenderer {
  private static final long serialVersionUID = 3256315536528607443L;

  @SuppressWarnings("unchecked")
  @Override
  public final Component getListCellRendererComponent(JList<? extends Object> list,
      Object value, int index, boolean isSelected, boolean cellHasFocus) {
    final JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected,
        cellHasFocus);
    processLabel(label, (JList<T>) list, (T) value, index, isSelected, cellHasFocus);
    return label;
  }

  protected abstract void processLabel(JLabel label, JList<T> list, T value,
      int index, boolean isSelected, boolean cellHasFocus);
}
