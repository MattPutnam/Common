package common.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p>List that fires events when it is modified.  Attach a
 * {@link ListListener} to listen to changes.</p>
 * 
 * <p>Note that a NotifyingList cannot tell if an item is changed directly.
 * Users of this class should call {@link #notifyChange(int)} or
 * {@link #notifyChange(Object)} to let listeners know of the change.</p>
 * 
 * @author Matt Putnam
 */
public class NotifyingList<E> extends ArrayList<E> {
	private transient List<ListListener<E>> _listeners;
	
	public NotifyingList() {
		super();
	}
	
	public NotifyingList(int capacity) {
		super(capacity);
	}
	
	public NotifyingList(Collection<? extends E> elements) {
		super(elements);
	}
	
	@Override
	public boolean add(E e) {
		super.add(e);
		notifyAdd(e, size()-1);
		return true;
	}
	
	@Override
	public void add(int index, E element) {
		super.add(index, element);
		notifyAdd(element, index);
	}
	
	@Override
	public boolean addAll(Collection<? extends E> c) {
		for (E e : c) {
			this.add(e);
		}
		return c.size() > 0;
	}
	
	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		for (E e : c) {
			this.add(index++, e);
		}
		return c.size() > 0;
	}
	
	@Override
	public void clear() {
		for (int index = size()-1; index >= 0; --index)
			this.remove(index);
	}
	
	@Override
	public NotifyingList<E> clone() {
		return (NotifyingList<E>) super.clone();
	}
	
	@Override
	public E remove(int index) {
		E e = super.remove(index);
		notifyRemove(e, index);
		return e;
	}
	
	@Override
	public boolean removeAll(Collection<?> c) {
		boolean result = false;
		for (Object o : c) {
			result |= this.remove(o);
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean remove(Object o) {
		final int index = indexOf(o);
		if (index != -1) {
			super.remove(index);
			notifyRemove((E) o, index);
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void removeRange(int fromIndex, int toIndex) {
		for (int i = toIndex-1; i >= fromIndex; --i) {
			remove(i);
		}
	}
	
	@Override
	public boolean retainAll(Collection<?> c) {
		boolean result = false;
		for (Object o : c) {
			final int index = indexOf(o);
			if (index != -1) {
				remove(index);
				result = true;
			}
		}
		return result;
	}
	
	@Override
	public E set(int index, E element) {
		super.set(index, element);
		for (ListListener<E> listener : accessListeners()) {
			listener.elementSet(new ListEvent<>(element, index));
		}
		return element;
	}
	
	public void addListener(ListListener<E> listener) {
		accessListeners().add(listener);
	}
	
	public void removeListener(ListListener<E> listener) {
		accessListeners().remove(listener);
	}
	
	public void notifyChange(int i) {
		final E e = get(i);
		for (ListListener<E> listener : accessListeners()) {
			listener.elementModified(new ListEvent<>(e, i));
		}
	}
	
	public void notifyChange(E item) {
		notifyChange(indexOf(item));
	}
	
	private void notifyAdd(E element, int index) {
		ListEvent<E> event = new ListEvent<>(element, index);
		for (ListListener<E> listener : accessListeners()) {
			listener.elementAdded(event);
		}
	}
	
	private void notifyRemove(E element, int index) {
		ListEvent<E> event = new ListEvent<>(element, index);
		for (ListListener<E> listener : accessListeners()) {
			listener.elementRemoved(event);
		}
	}
	
	private List<ListListener<E>> accessListeners() {
		if (_listeners == null)
			_listeners = new ArrayList<>();
		
		return _listeners;
	}
	
}
