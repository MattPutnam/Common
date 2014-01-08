package common.collection;

/**
 * Implements {@link ListListener} with no-op implementations as a convenience.
 * This class also provides the method {@link #anyChange(ListEvent)},
 * which is called by each of the ListListener methods.  If you want to override
 * {@link #elementAdded(ListEvent)}, {@link #elementRemoved(ListEvent)}, or
 * {@link #elementSet(ListEvent)} and still receive notifications via
 * anyChange(ListEvent), then those overrides must call super.
 * 
 * @author Matt Putnam
 */
public class ListAdapter<E> implements ListListener<E> {
	@Override
	public void elementAdded(ListEvent<E> event) {
		anyChange(event);
	}
	
	@Override
	public void elementRemoved(ListEvent<E> event) {
		anyChange(event);
	}
	
	@Override
	public void elementSet(ListEvent<E> event) {
		anyChange(event);
	}
	
	@Override
	public void elementModified(ListEvent<E> event) {
		anyChange(event);
	}
	
	/**
	 * Called if any change has occurred.
	 * @param event - the associated ListEvent
	 */
	public void anyChange(ListEvent<E> event) {}

	
}
