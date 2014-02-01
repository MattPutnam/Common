package common.swing.table;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

/**
 * Table model backed by a {@link List}.
 * @param <T> the type for the list
 * 
 * @author Matt Putnam
 */
public abstract class ListTableModel<T> extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	
	private List<T> _list;
	
	private List<TableModelListener> _listeners;

	/**
	 * Creates a new ListTableModel with an empty list
	 */
	public ListTableModel() {
		_list = new ArrayList<>();
		_listeners = new LinkedList<>();
	}
	
	/**
	 * Sets the list
	 * @param list the list to set
	 */
	public void setList(List<T> list) {
		_list = list;
		notifyTableDataChanged();
	}
	
	/**
	 * Notifies all listeners that the table data has changed, potentially
	 * including all items
	 */
	void notifyTableDataChanged() {
		for (final TableModelListener listener : _listeners) {
			listener.tableChanged(new TableModelEvent(this));
		}
	}
	
	/**
	 * Gets the list of items.  The returned list is the same instance
	 * backing the table model, but changes to it will not be reflected
	 * without triggering the table to refresh.
	 * @return the underlying list
	 */
	public List<T> getList() {
		return _list;
	}
	
	/**
	 * Declares the column names in this model.  The length of this
	 * array is used to determine the number of columns
	 * @return an array of the column names
	 */
	public abstract String[] declareColumns();
	
	/**
	 * Resolve the object at the given column
	 * @param row the row object
	 * @param column the column
	 * @return
	 */
	public abstract Object resolveValue(T row, int column);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Class<?> getColumnClass(int column) {
		return Object.class;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Default implementation uses {@link #declareColumns()}
	 * to determine the column count
	 */
	@Override
	public int getColumnCount() {
		return declareColumns().length;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Default implementation uses {@link #declareColumns()}
	 * to determine the column name
	 */
	@Override
	public String getColumnName(int column) {
		return declareColumns()[column];
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Default implementation uses the underlying list's size
	 */
	@Override
	public int getRowCount() {
		return _list.size();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Default implementation uses {@link #resolveValue(Object, int)}
	 */
	@Override
	public Object getValueAt(int row, int column) {
		return resolveValue(_list.get(row), column);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addTableModelListener(TableModelListener listener) {
		_listeners.add(listener);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeTableModelListener(TableModelListener listener) {
		_listeners.remove(listener);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Default implementation throws IllegalStateException always
	 */
	@Override
	public void setValueAt(Object value, int row, int column) {
		throw new IllegalStateException("Table not editable!");
	}

}
