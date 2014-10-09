package common.swing;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 * Arranges components in a simple grid using a GroupLayout.  Components are
 * aligned vertically by {@link Alignment#LEADING} and horizontally by
 * {@link Alignment#BASELINE}.
 * 
 * @author Matt Putnam
 */
public class SimpleGrid extends JPanel {
  private static final long serialVersionUID = 1L;
  
  public SimpleGrid(JComponent[][] grid) {
    this(grid, Alignment.BASELINE, Alignment.LEADING);
  }
  
  /**
   * Creates a new SimpleGrid with the given components.  The array must be
   * rectangular (that is, all rows having the same length).  Holes can be
   * given with <tt>null</tt>.
   * @param grid - the array of components
   */
  public SimpleGrid(JComponent[][] grid, Alignment rowAlignment, Alignment columnAlignment) {
    super();
    if (grid == null || grid.length == 0 || grid[0].length == 0)
      throw new IllegalArgumentException("Grid must be non-null and at least 1x1");
    init(grid, rowAlignment, columnAlignment);
  }

  private void init(JComponent[][] grid, Alignment rowAlignment, Alignment columnAlignment) {
    final GroupLayout layout = new GroupLayout(this);
    setLayout(layout);
    
    final int numRows = grid.length;
    final int numCols = grid[0].length;
    
    final ParallelGroup[] rows = new ParallelGroup[numRows];
    for (int row = 0; row < numRows; ++row) {
      rows[row] = layout.createParallelGroup(rowAlignment);
      for (int col = 0; col < numCols; ++col)
        if (grid[row][col] != null)
          rows[row].addComponent(grid[row][col]);
    }
    
    final ParallelGroup[] cols = new ParallelGroup[numCols];
    for (int col = 0; col < numCols; ++col) {
      cols[col] = layout.createParallelGroup(columnAlignment);
      for (int row = 0; row < numRows; ++row)
        if (grid[row][col] != null)
          cols[col].addComponent(grid[row][col]);
    }
    
    final SequentialGroup horizontalGroup = layout.createSequentialGroup();
    for (final ParallelGroup colGroup : cols)
      horizontalGroup.addGroup(colGroup);
    
    final SequentialGroup verticalGroup = layout.createSequentialGroup();
    for (final ParallelGroup rowGroup : rows)
      verticalGroup.addGroup(rowGroup);
    
    layout.setHorizontalGroup(horizontalGroup);
    layout.setVerticalGroup(verticalGroup);
  }
}
