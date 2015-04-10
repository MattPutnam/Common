package common.collection.buffer;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * A fixed size FIFO buffer of type <tt>int</tt>.  Implemented as an array
 * 
 * @author Matt Putnam
 * @see FixedSizeBuffer
 * @see FixedSizeDoubleBuffer
 */
public class FixedSizeIntBuffer {
  private final int[] _buffer;
  private final int _bufferSize;
  private int _ptr;
  
  private int _size;
  private boolean _atCapacity;
  
  /**
   * Creates a FixedSizeIntBuffer with the given size
   * @param size the size of the buffer
   */
  public FixedSizeIntBuffer(int size) {
    _buffer = new int[size];
    _bufferSize = size;
    _ptr = 0;
    
    _size = 0;
    _atCapacity = false;
  }
  
  /**
   * Adds a new item to the buffer.  If the buffer was full, the oldest element
   * is removed and returned.
   * @param newItem
   * @return
   */
  public synchronized int add(int newItem) {
    final int removed = _atCapacity ? _buffer[_ptr] : 0;
    _buffer[_ptr] = newItem;
    _ptr = (_ptr+1) % _bufferSize;
    
    if (!_atCapacity) {
      ++_size;
      _atCapacity = _size == _bufferSize;
    }
    
    return removed;
  }
  
  public synchronized void fill(int value) {
    for (int i = 0; i < _size; ++i)
      add(value);
  }
  
  public synchronized int size() {
    return _size;
  }
  
  public synchronized void clear() {
    _size = 0;
    _atCapacity = false;
  }
  
  public synchronized int[] getValues() {
    int[] result = new int[_size];
    for (int srcPtr = (_ptr-1+_bufferSize)%_bufferSize, destPtr = _size-1;
       destPtr >= 0;
       srcPtr = (srcPtr-1+_bufferSize)%_bufferSize, --destPtr)
      result[destPtr] = _buffer[srcPtr];
    return result;
  }
  
  public IntStream stream() {
    return Arrays.stream(getValues());
  }
}
