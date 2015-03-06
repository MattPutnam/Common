package common.tuple;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import common.Copyable;

public class Triple<A, B, C> implements Copyable<Triple<A, B, C>>, Iterable<Object>, Serializable {
  private static final long serialVersionUID = 1L;
  
  private final A _a;
  private final B _b;
  private final C _c;
  
  public static <X, Y, Z> Triple<X, Y, Z> make(X x, Y y, Z z) {
    return new Triple<>(x, y, z);
  }
  
  /**
   * Comparator that sorts by first element, then second element, then third element.
   * @author Matt Putnam
   */
  public class TripleComparator<X extends Comparable<? super X>,
                  Y extends Comparable<? super Y>,
                  Z extends Comparable<? super Z>>
      implements Comparator<Triple<X, Y, Z>> {
    @Override
    public int compare(Triple<X, Y, Z> o1, Triple<X, Y, Z> o2) {
      int temp = o1._a.compareTo(o2._a);
      if (temp != 0) {
        return temp;
      } else {
        temp = o1._b.compareTo(o2._b);
        if (temp != 0) {
          return temp;
        } else {
          return o1._c.compareTo(o2._c);
        }
      }
    }
  }
  
  public Triple(A a, B b, C c) {
    _a = a;
    _b = b;
    _c = c;
  }
  
  public final A _1() {
    return _a;
  }
  
  public final B _2() {
    return _b;
  }
  
  public final C _3() {
    return _c;
  }
  
  public List<Object> toList() {
    final List<Object> result = new ArrayList<>(3);
    result.add(_a); result.add(_b); result.add(_c);
    return result;
  }
  
  @Override
  public String toString() {
    return "Triple[" + _a + ", " + _b + ", " + _c + "]";
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj == null) return false;
    if (obj == this) return true;
    @SuppressWarnings("unchecked")
    final Triple<A, B, C> t = (Triple<A, B, C>) obj;
    return this._a.equals(t._a) &&
         this._b.equals(t._b) &&
         this._c.equals(t._c);
  }
  
  @Override
  public int hashCode() {
    int hashCode = _a.hashCode();
    hashCode = 31*hashCode + _b.hashCode();
    hashCode = 31*hashCode + _c.hashCode();
    return hashCode;
  }

  @Override
  public Triple<A, B, C> copy() {
    return new Triple<>(_a, _b, _c);
  }

  @Override
  public Iterator<Object> iterator() {
    return toList().iterator();
  }
}
