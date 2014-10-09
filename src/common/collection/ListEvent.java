package common.collection;

/**
 * An event capturing changes to a {@link NotifyingList}.
 * 
 * @author Matt Putnam
 *
 * @param <E> - the type of the LinkedList that fires this
 */
public class ListEvent<E> {
  private final E _element;
  private final int _index;
  
  ListEvent(E element, int index) {
    _element = element;
    _index = index;
  }
  
  /**
   * @return the element which was added, removed, or changed.
   */
  public E getElement() {
    return _element;
  }
  
  /**
   * @return the index of the element which was added, removed, or changed
   */
  public int getIndex() {
    return _index;
  }
}
