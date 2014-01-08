package common.tuple;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import common.Copyable;

public class Tuple5<A, B, C, D, E> implements Copyable<Tuple5<A, B, C, D, E>>, Iterable<Object> {
	private final A _a;
	private final B _b;
	private final C _c;
	private final D _d;
	private final E _e;
	
	public static <V, W, X, Y, Z> Tuple5<V, W, X, Y, Z> make(V v, W w, X x, Y y, Z z) {
		return new Tuple5<>(v, w, x, y, z);
	}
	
	/**
	 * Comparator that sorts by first element, then second element, then third
	 * element, then fourth element, then fifth element.
	 * @author Matt Putnam
	 */
	public class Tuple5Comparator<V extends Comparable<? super V>,
								  W extends Comparable<? super W>,
								  X extends Comparable<? super X>,
								  Y extends Comparable<? super Y>,
								  Z extends Comparable<? super Z>>
			implements Comparator<Tuple5<V, W, X, Y, Z>> {
		@Override
		public int compare(Tuple5<V, W, X, Y, Z> o1, Tuple5<V, W, X, Y, Z> o2) {
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
						temp = o1._d.compareTo(o2._d);
						if (temp != 0) {
							return temp;
						} else {
							return o1._e.compareTo(o2._e);
						}
					}
				}
			}
		}
	}
	
	public Tuple5(A a, B b, C c, D d, E e) {
		_a = a;
		_b = b;
		_c = c;
		_d = d;
		_e = e;
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
	
	public final E _5() {
		return _e;
	}
	
	public List<Object> toList() {
		final List<Object> result = new ArrayList<>(5);
		result.add(_a); result.add(_b); result.add(_c); result.add(_d); result.add(_e);
		return result;
	}
	
	@Override
	public String toString() {
		return "Tuple5[" + _a + ", " + _b + ", " + _c + ", " + _d + ", " + _e + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		@SuppressWarnings("unchecked")
		final Tuple5<A, B, C, D, E> t = (Tuple5<A, B, C, D, E>) obj;
		return this._a.equals(t._a) &&
			   this._b.equals(t._b) &&
			   this._c.equals(t._c) &&
			   this._d.equals(t._d) &&
			   this._e.equals(t._e);
	}
	
	@Override
	public int hashCode() {
		int hashCode = _a.hashCode();
		hashCode = 31*hashCode + _b.hashCode();
		hashCode = 31*hashCode + _c.hashCode();
		hashCode = 31*hashCode + _d.hashCode();
		hashCode = 31*hashCode + _e.hashCode();
		return hashCode;
	}
	
	@Override
	public Iterator<Object> iterator() {
		return toList().iterator();
	}
	
	@Override
	public Tuple5<A, B, C, D, E> copy() {
		return new Tuple5<>(_a, _b, _c, _d, _e);
	}
	
}
