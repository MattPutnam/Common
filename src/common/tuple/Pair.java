package common.tuple;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import common.Copyable;

public class Pair<A, B> implements Copyable<Pair<A, B>>, Iterable<Object>, Serializable {
  private static final long serialVersionUID = 1L;
  
  private final A _a;
  private final B _b;
  
  public static <X, Y> Pair<X, Y> make(X x, Y y) {
    return new Pair<>(x, y);
  }
  
  /**
   * Comparator that sorts first by the first element, then by the second element.
   * @author Matt Putnam
   */
  public static class PairComparator<X extends Comparable<? super X>,
                     Y extends Comparable<? super Y>>
      implements Comparator<Pair<X, Y>> {
    @Override
    public int compare(Pair<X, Y> arg0, Pair<X, Y> arg1) {
      final int temp = arg0._a.compareTo(arg1._a);
      if (temp != 0) {
        return temp;
      } else {
        return arg0._b.compareTo(arg1._b);
      }
    }
  }
  
  public Pair(A a, B b) {
    _a = a;
    _b = b;
  }
  
  public final A _1() {
    return _a;
  }
  
  public final B _2() {
    return _b;
  }

  public Pair<B, A> swap() {
    return new Pair<>(_b, _a);
  }
  
  public Map<A, B> toMap() {
    final Map<A, B> result = new HashMap<>();
    result.put(_a, _b);
    return result;
  }
  
  public static <X, Y> List<Pair<X, Y>> fromMap(Map<X, Y> map) {
    final List<Pair<X, Y>> result = new ArrayList<>(map.size());
    for (final Map.Entry<X, Y> entry : map.entrySet()) {
      result.add(Pair.make(entry.getKey(), entry.getValue()));
    }
    return result;
  }
  
  public static <X, Y> Map<X, Y> toMap(Collection<Pair<X, Y>> collection) {
    final Map<X, Y> result = new HashMap<>();
    for (final Pair<X, Y> pair : collection) {
      result.put(pair._1(), pair._2());
    }
    return result;
  }
  
  public List<Object> toList() {
    final List<Object> result = new ArrayList<>(2);
    result.add(_a); result.add(_b);
    return result;
  }
  
  @Override
  public String toString() {
    return "Pair[" + _a + ", " + _b + "]";
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj == null) return false;
    if (obj == this) return true;
    @SuppressWarnings("unchecked")
    final Pair<A, B> p = (Pair<A, B>) obj;
    return this._a.equals(p._a) && this._b.equals(p._b);
  }
  
  @Override
  public int hashCode() {
    return 31*_a.hashCode() + _b.hashCode();
  }

  @Override
  public Pair<A, B> copy() {
    return new Pair<>(_a, _b);
  }

  @Override
  public Iterator<Object> iterator() {
    return toList().iterator();
  }
}
