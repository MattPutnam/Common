package common.collection;

/**
 * Listens to changes to a {@link NotifyingList}.
 * 
 * @author Matt Putnam
 *
 * @param <E> - the type of the NotifyingList that this is attached to
 */
public interface ListListener<E> {
  /**
   * Called any time an element is added to the associated NotifyingList.  In
   * the case of a modification that adds multiple elements, this method is
   * called once for each element.
   * @param event - the associated ListEvent
   */
  public void elementAdded(ListEvent<E> event);
  
  /**
   * Called any time an element is removed from the associated NotifyingList.
   * In the case of a modification that removes multiple elements, this
   * method is called once for each element.
   * @param event - the associated ListEvent
   */
  public void elementRemoved(ListEvent<E> event);
  
  /**
   * Called when an element is changed via a call to
   * {@link NotifyingList#set(int, Object)} in the associated NotifyingList.
   * @param event - the associated ListEvent
   */
  public void elementSet(ListEvent<E> event);
  
  /**
   * Called when NotifyingList.notifyChange is called on the associated NotifyingList
   * @param event - the associated ListEvent
   */
  public void elementModified(ListEvent<E> event);
}
