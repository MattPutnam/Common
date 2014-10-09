package common.tuple;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import common.Copyable;

public class Tuple4<A, B, C, D> implements Copyable<Tuple4<A, B, C, D>>, Iterable<Object>, Serializable {
  private static final long serialVersionUID = 1L;
  
  private final A _a;
  private final B _b;
  private final C _c;
  private final D _d;
  
  public static <W, X, Y, Z> Tuple4<W, X, Y, Z> make(W w, X x, Y y, Z z) {
    return new Tuple4<>(w, x, y, z);
  }
  
  /**
   * Comparator that sorts by first element, then second element, then third element, then fourth element.
   * @author Matt Putnam
   */
  public class Tuple4Comparator<W extends Comparable<? super W>,
                  X extends Comparable<? super X>,
                  Y extends Comparable<? super Y>,
                  Z extends Comparable<? super Z>>
      implements Comparator<Tuple4<W, X, Y, Z>> {
    @Override
    public int compare(Tuple4<W, X, Y, Z> o1, Tuple4<W, X, Y, Z> o2) {
      int temp = o1._a.compareTo(o2._a);
      if (temp != 0) {
        return temp;
      } else {
        temp = o1._b.compareTo(o2._b);
        if (temp != 0) {
          return temp;
        } else {
          temp = o1._c.compareTo(o2._c);
          if (temp != 0) {
            return temp;
          } else {
            return o1._d.compareTo(o2._d);
          }
        }
      }
    }
  }
  
  public Tuple4(A a, B b, C c, D d) {
    _a = a;
    _b = b;
    _c = c;
    _d = d;
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
  
  public final D _4() {
    return _d;
  }
  
  public List<Object> toList() {
    final List<Object> result = new ArrayList<>(4);
    result.add(_a); result.add(_b); result.add(_c); result.add(_d);
    return result;
  }
  
  @Override
  public String toString() {
    return "Tuple4[" + _a + ", " + _b + ", " + _c + ", " + _d + "]";
  }
  
  @Override
  public boolean equals(Object obj) {
    @SuppressWarnings("unchecked")
    final Tuple4<A, B, C, D> t = (Tuple4<A, B, C, D>) obj;
    return this._a.equals(t._a) &&
         this._b.equals(t._b) &&
         this._c.equals(t._c) &&
         this._d.equals(t._d);
  }
  
  @Override
  public int hashCode() {
    int hashCode = _a.hashCode();
    hashCode = 31*hashCode + _b.hashCode();
    hashCode = 31*hashCode + _c.hashCode();
    hashCode = 31*hashCode + _d.hashCode();
    return hashCode;
  }
  
  @Override
  public Tuple4<A, B, C, D> copy() {
    return new Tuple4<>(_a, _b, _c, _d);
  }
  
  @Override
  public Iterator<Object> iterator() {
    return toList().iterator();
  }
}
