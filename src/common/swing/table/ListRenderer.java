package common.swing.table;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import common.Utils;

public class ListRenderer extends DefaultTableCellRenderer {
	private final String _delimiter;
	
	public ListRenderer() {
		this(", ");
	}
	
	public ListRenderer(String delimiter) {
		super();
		_delimiter = delimiter;
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		JLabel label = (JLabel) c;
		Iterable<?> i = (Iterable<?>) value;
		
		label.setText(i == null || i.iterator().hasNext() ? Utils.mkString(i, _delimiter) : "None");
		return label;
	}
}
