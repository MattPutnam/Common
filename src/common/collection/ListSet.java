package common.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * A Set implementation that is backed by a list so that the order is preserved.
 * @author Matt Putnam
 *
 * @param <E> - the type of element in the Set
 */
public class ListSet<E> implements Set<E> {
	private final List<E> _list;
	
	public ListSet() {
		_list = new ArrayList<>();
	}
	
	public ListSet(Collection<E> elements) {
		_list = new ArrayList<>(elements);
	}
	
	@Override
	public boolean add(E arg0) {
		if (!_list.contains(arg0)) {
			_list.add(arg0);
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public boolean addAll(Collection<? extends E> arg0) {
		boolean result = false;
		for (final E e : arg0) {
			result = result || add(e);
		}
		return result;
	}
	
	@Override
	public void clear() {
		_list.clear();
	}
	
	@Override
	public boolean contains(Object arg0) {
		return _list.contains(arg0);
	}
	
	@Override
	public boolean containsAll(Collection<?> arg0) {
		return _list.containsAll(arg0);
	}
	
	@Override
	public boolean isEmpty() {
		return _list.isEmpty();
	}
	
	@Override
	public Iterator<E> iterator() {
		return _list.iterator();
	}
	
	@Override
	public boolean remove(Object arg0) {
		return _list.remove(arg0);
	}
	
	@Override
	public boolean removeAll(Collection<?> arg0) {
		return _list.removeAll(arg0);
	}
	
	@Override
	public boolean retainAll(Collection<?> arg0) {
		return _list.retainAll(arg0);
	}
	
	@Override
	public int size() {
		return _list.size();
	}
	
	@Override
	public Object[] toArray() {
		return _list.toArray();
	}
	
	@Override
	public <T> T[] toArray(T[] arg0) {
		return _list.toArray(arg0);
	}
	
	@Override
	public String toString() {
		return _list.toString();
	}
	
	@Override
	public boolean equals(Object arg0) {
		return _list.equals(((ListSet<?>) arg0)._list);
	}
	
	@Override
	public int hashCode() {
		return _list.hashCode();
	}
	
}
