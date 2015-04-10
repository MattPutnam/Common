package common.collection.buffer;

import java.util.Arrays;
import java.util.stream.DoubleStream;

public class FixedSizeDoubleBuffer {
  private final double[] _buffer;
  private final int _bufferSize;
  private int _ptr;
  
  private int _size;
  private boolean _atCapacity;
  
  public FixedSizeDoubleBuffer(int size) {
    _buffer = new double[size];
    _bufferSize = size;
    _ptr = 0;
    
    _size = 0;
    _atCapacity = false;
  }
  
  public synchronized double add(double newItem) {
    final double removed = _atCapacity ? _buffer[_ptr] : 0;
    _buffer[_ptr] = newItem;
    _ptr = (_ptr+1) % _bufferSize;
    
    if (!_atCapacity) {
      ++_size;
      _atCapacity = _size == _bufferSize;
    }
    
    return removed;
  }
  
  public synchronized int size() {
    return _size;
  }
  
  public synchronized void clear() {
    _size = 0;
    _atCapacity = false;
  }
  
  public synchronized double[] getValues() {
    double[] result = new double[_size];
    for (int srcPtr = (_ptr-1+_bufferSize)%_bufferSize, destPtr = _size-1;
       destPtr >= 0;
       srcPtr = (srcPtr-1+_bufferSize)%_bufferSize, --destPtr)
      result[destPtr] = _buffer[srcPtr];
    return result;
  }
  
  public DoubleStream stream() {
    return Arrays.stream(getValues());
  }
}
