package common.swing.table;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import common.swing.SwingUtils;
import common.swing.VerificationException;
import common.swing.dialog.Dialog;

/**
 * A table backed by a {@link ListTableModel}, with a bar of buttons at the top
 * @author Matt Putnam
 *
 * @param <T> the type of item displayed in the table
 */
public abstract class ListTable<T> extends JPanel {
  private static final long serialVersionUID = 1L;
  
  private final JButton _addButton;
  private final JButton _editButton;
  private final JButton _deleteButton;
  
  private final JTable _table;
  private final ListTableModel<T> _tableModel;
  
  private final boolean _allowEdit;
  
  /**
   * Creates a new ListTable
   * @param list the list of items.  The list passed in is used directly
   * by reference; any changes made by editing the table are immediately
   * reflected in the passed-in reference
   * @param allowEdit whether or not to allow editing
   * @param allowMoveArrows whether or not to allow row reordering
   * @param addIcon the icon to use for the add action, or null to use "+"
   * @param editIcon the icon to use for the edit button, or null to use "Edit".
   * If <tt>allowEdit</tt> is false, this button is never shown.
   * @param deleteIcon the icon to use for the delete action, or null to use "-"
   * @param upIcon the icon to use for the move up action, or null to use "Move Up".
   * if <tt>allowMoveArrows</tt> is false, this button is never shown.
   * @param downIcon the icon to use for the move down action, or null to use "Move Down".
   * if <tt>allowMoveArrows</tt> is false, this button is never shown.
   */
  public ListTable(final List<T> list, boolean allowEdit, boolean allowMoveArrows,
      ImageIcon addIcon, ImageIcon editIcon, ImageIcon deleteIcon, ImageIcon upIcon, ImageIcon downIcon) {
    this(list, allowEdit, allowMoveArrows, null, addIcon, editIcon, deleteIcon, upIcon, downIcon);
  }
  
  /**
   * Creates a new ListTable
   * @param list the list of items.  The list passed in is used directly
   * by reference; any changes made by editing the table are immediately
   * reflected in the passed-in reference
   * @param allowEdit whether or not to allow editing
   * @param allowMoveArrows whether or not to allow row reordering
   * @param buttonLabel a label displayed on the left of the button bar,
   * or null to omit this label
   * @param addIcon the icon to use for the add action, or null to use "+"
   * @param editIcon the icon to use for the edit button, or null to use "Edit".
   * If <tt>allowEdit</tt> is false, this button is never shown.
   * @param deleteIcon the icon to use for the delete action, or null to use "-"
   * @param upIcon the icon to use for the move up action, or null to use "Move Up".
   * if <tt>allowMoveArrows</tt> is false, this button is never shown.
   * @param downIcon the icon to use for the move down action, or null to use "Move Down".
   * if <tt>allowMoveArrows</tt> is false, this button is never shown.
   * @param extraComponents any extra components, including extra buttons, to include
   * in the button bar.  Items are added after the add/edit/delete buttons but before
   * the up/down arrows (if present).  The button bar is constructed using a horizontal
   * {@link Boxlayout}, so use {@link Box#createHorizontalStrut(int)} to create space.
   */
  public ListTable(final List<T> list, boolean allowEdit, boolean allowMoveArrows, String buttonLabel,
      ImageIcon addIcon, ImageIcon editIcon, ImageIcon deleteIcon, ImageIcon upIcon, ImageIcon downIcon,
      Component... extraComponents) {
    super();
    
    _allowEdit = allowEdit;
    
    _addButton = addIcon == null ? new JButton("+") : SwingUtils.iconButton(addIcon);
    _addButton.setToolTipText("Add new " + declareTypeName());
    _addButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        takeActionOnAdd();
        _tableModel.notifyTableDataChanged();
      }
    });
    
    _editButton = editIcon == null ? new JButton("Edit") : SwingUtils.iconButton(editIcon);
    _editButton.setToolTipText("Edit selected " + declareTypeName());
    _editButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        takeActionOnEdit(_tableModel.getList().get(_table.getSelectedRow()));
        _tableModel.notifyTableDataChanged();
      }
    });
    
    _deleteButton = deleteIcon == null ? new JButton("-") : SwingUtils.iconButton(deleteIcon);
    _deleteButton.setToolTipText("Delete selected " + declareTypeName());
    _deleteButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        final List<T> selected = getSelectedRows();
        
        if (!Dialog.confirm(ListTable.this, "Are you sure you want to delete the selected items?  " +
            declareAdditionalDeleteWarning(selected), "Confirm Delete"))
          return;
        
        _tableModel.getList().removeAll(selected);
        
        takeActionAfterDelete(selected);
        _tableModel.notifyTableDataChanged();
      }
    });
    
    final JButton upButton = upIcon == null ? new JButton("Move Up") : SwingUtils.iconButton(upIcon);
    upButton.setToolTipText("Move selected " + declareTypeName() + " up");
    upButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        final int selectedRow = _table.getSelectedRow();
        final T selectedItem = list.get(selectedRow);
        list.remove(selectedRow);
        list.add(selectedRow-1, selectedItem);
        _table.setRowSelectionInterval(selectedRow-1, selectedRow-1);
        _tableModel.notifyTableDataChanged();
      }
    });
    
    final JButton downButton = downIcon == null ? new JButton("Move Down") : SwingUtils.iconButton(downIcon);
    downButton.setToolTipText("Move selected " + declareTypeName() + " down");
    downButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        final int selectedRow = _table.getSelectedRow();
        final T selectedItem = list.get(selectedRow);
        list.remove(selectedRow);
        list.add(selectedRow+1, selectedItem);
        _table.setRowSelectionInterval(selectedRow+1, selectedRow+1);
        _tableModel.notifyTableDataChanged();
      }
    });
    
    _table = createTable();
    _tableModel = createTableModel();
    _tableModel.setList(list);
    _table.setModel(_tableModel);
    
    _table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
      @Override
      public void valueChanged(ListSelectionEvent e) {
        final int numSelected = _table.getSelectedRowCount();
        final int selectedRow = _table.getSelectedRow();
        final T selectedItem = selectedRow == -1 ? null : _tableModel.getList().get(selectedRow);
        
        final boolean rowMovable = selectedRow >= 0 && isRowMovable(selectedRow, selectedItem);
        
        _editButton.setEnabled(numSelected == 1 && isRowEditable(selectedRow, selectedItem));
        _deleteButton.setEnabled(numSelected > 0 && allowDelete(getSelectedRows()));
        upButton.setEnabled(numSelected == 1 & selectedRow > 0 && rowMovable);
        downButton.setEnabled(numSelected == 1 && rowMovable && selectedRow < list.size()-1);
      }
    });
    _editButton.setEnabled(false);
    _deleteButton.setEnabled(false);
    upButton.setEnabled(false);
    downButton.setEnabled(false);
    
    _tableModel.addTableModelListener(e -> {
      for (int row = 0; row < _table.getRowCount(); ++row) {
        int rowHeight = _table.getRowHeight();
        for (int column = 0; column < _table.getColumnCount(); ++column) {
          Component comp = _table.prepareRenderer(_table.getCellRenderer(row, column), row, column);
          rowHeight = Math.max(rowHeight, comp.getPreferredSize().height);
        }
        _table.setRowHeight(row, rowHeight);
      }
      _table.revalidate();
      _table.repaint();
    });
    
    if (allowEdit) {
      _table.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
          if (e.getClickCount() == 2 && _editButton.isEnabled()) {
            _editButton.doClick();
          }
        }
      });
    }
    
    final Box top = Box.createHorizontalBox();
    if (buttonLabel != null) top.add(makeLabel(buttonLabel));
    top.add(_addButton);
    if (allowEdit) top.add(_editButton);
    top.add(_deleteButton);
    for (final Component extra : extraComponents) {
      top.add(extra);
    }
    if (allowMoveArrows) {
      top.add(Box.createHorizontalGlue());
      top.add(upButton);
      top.add(downButton);
    }
    
    final JScrollPane scrollPane = new JScrollPane(_table);
    
    setLayout(new BorderLayout());
    add(top, BorderLayout.NORTH);
    add(scrollPane, BorderLayout.CENTER);
  }
  
  private static JLabel makeLabel(String text) {
    final JLabel result = new JLabel(text);
    result.setBorder(new EmptyBorder(0, 8, 0, 8));
    return result;
  }
  
  /**
   * Gets the list of currently selected rows.
   * @return the list of currently selected rows
   */
  public List<T> getSelectedRows() {
    final List<T> selected = new ArrayList<>();
    for (final int index : _table.getSelectedRows()) {
      selected.add(accessTableModel().getList().get(index));
    }
    return selected;
  }

  /**
   * Verifies this table.  The default implementation verifies that there is
   * at least one item in the table.
   * @throws VerificationException if verification fails
   */
  public void verify() throws VerificationException {
    if (_tableModel.getList().isEmpty())
      throw new VerificationException("Please specify at least one " + declareTypeName(), _addButton);
  }
  
  /**
   * Programmatically trigger an add.
   */
  public void doAdd() {
    _addButton.doClick();
  }
  
  /**
   * Programmatically trigger an edit.  Will throw an exception if editing has been disabled
   * for the component or the number of selected rows is not 1.
   */
  public void doEdit() {
    if (!_allowEdit)
      throw new IllegalStateException("Edit not allowed!");
    
    if (!_editButton.isEnabled())
      throw new IllegalStateException("Cannot edit, number of selected rows != 1");
    
    _editButton.doClick();
  }
  
  /**
   * Override this method to specify a custom JTable.  The default implementation
   * returns "new JTable()".
   * @return the table to use
   */
  protected JTable createTable() {
    return new JTable();
  }
  
  /**
   * Creates the table model for this ListTable.
   * @return a new ListTableModel
   */
  protected abstract ListTableModel<T> createTableModel();
  
  /**
   * Provides direct access to this component's JTable
   * @return the JTable
   */
  public final JTable accessTable() {
    return _table;
  }
  
  /**
   * Provides direct access to the table's ListTableModel
   * @return the table's ListTableModel
   */
  public final ListTableModel<T> accessTableModel() {
    return _tableModel;
  }
  
  /**
   * Called when the add button is pressed.
   */
  protected abstract void takeActionOnAdd();
  
  /**
   * Override to specify the action to take when an edit is triggered.  Must be overridden
   * if editing is allowed, as the default implementation throws an exception.
   * @param item - the item to edit
   */
  protected void takeActionOnEdit(T item) {
    throw new IllegalStateException("Must override takeActionOnEdit(T) if editing is enabled!");
  }
  
  /**
   * Called during the delete action.  Override to specify any additional information in
   * the delete warning.  Text is added after the normal message.  Default implementation
   * returns an empty String.
   * @param toDelete - the list of items that were selected to be deleted
   * @return an additional warning to the delete warning.
   */
  protected String declareAdditionalDeleteWarning(List<T> toDelete) {
    return "";
  }
  
  /**
   * Called after any change to the selected rows.  Override to specify any additional
   * logic to determine whether a delete should be allowed.  Default implementation
   * returns <tt>true</tt>.
   * @param toDelete - the list of items that are selected
   * @return <tt>true</tt> if deleting the selected items should be allowed
   */
  protected boolean allowDelete(List<T> toDelete) {
    return true;
  }
  
  /**
   * Called after the delete action has been performed.  Override to specify any
   * additional logic to be taken to clean up those items.  Default implementation
   * does nothing.
   * @param removed - the list of items that were just deleted
   */
  protected void takeActionAfterDelete(List<T> removed) {}
  
  /**
   * Declares the type name for this table.  Used in warning dialogs.
   * @return the type name for this table
   */
  protected abstract String declareTypeName();
  
  /**
   * Called after the table selection has been changed to a single row.  Override
   * to specify any additional logic to determine whether a row should be editable.
   * Will not be called if the table is not editable in general.
   * Default implementation returns <tt>true</tt>.
   * @param row - the row selected
   * @param selectedItem - the item selected
   * @return <tt>true</tt> if the row should be editable
   */
  protected boolean isRowEditable(int row, T selectedItem) {
    return true;
  }
  
  /**
   * Called after the table selection has been changed to a single row.  Override
   * to specify any additional logic to determine whether a row should be movable.
   * Will not be called if the table rows are not movable in general.
   * Default implementation returns <tt>true</tt>
   * @param row - the row selected
   * @param selectedItem - the item selected
   * @return <tt>true</tt> if the row should be movable
   */
  protected boolean isRowMovable(int row, T selectedItem) {
    return true;
  }
}
