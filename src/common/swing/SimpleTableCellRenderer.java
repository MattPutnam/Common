package common.swing;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public abstract class SimpleTableCellRenderer<T> extends DefaultTableCellRenderer {
  private static final long serialVersionUID = -7560312418803488198L;

  @SuppressWarnings("unchecked")
  @Override
  public final Component getTableCellRendererComponent(JTable table, Object value,
      boolean isSelected, boolean hasFocus, int row, int column) {
    final JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    processLabel(label, table, (T) value, isSelected, hasFocus, row, column);
    return label;
  }
  
  protected abstract void processLabel(JLabel label, JTable table, T value, boolean isSelected, boolean hasFocus, int row, int column);
}
