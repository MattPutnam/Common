package common.collection.buffer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * A fixed size circular FIFO buffer.
 * 
 * @author Matt Putnam
 * @param <T> the type to store
 */
public class FixedSizeBuffer<T> {
  private final LinkedList<T> _buffer;
  private final int _size;
  
  /**
   * Constructs a new FixedSizeBuffer with the given size
   * @param size the size
   */
  public FixedSizeBuffer(int size) {
    _buffer = new LinkedList<>();
    _size = size;
  }
  
  /**
   * Adds an item to the buffer.  If the buffer is full, the oldest element
   * is removed and returned.
   * @param newItem the new item to add
   * @return the removed item if one was removed, otherwise null
   */
  public synchronized T add(T newItem) {
    T removed = null;
    if (_buffer.size() == _size) {
      removed = _buffer.remove();
    }
    _buffer.add(newItem);
    return removed;
  }
  
  public synchronized int size() {
    return _buffer.size();
  }
  
  /**
   * Clears the buffer.
   */
  public synchronized void clear() {
    _buffer.clear();
  }
  
  /**
   * @return a copy of the items in the buffer
   */
  public synchronized List<T> getItems() {
    return new ArrayList<>(_buffer);
  }
}
