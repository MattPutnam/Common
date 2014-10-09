package common;

/**
 * A typesafe alternative to Object.clone()
 * @author Matt Putnam
 *
 * @param <T>
 */
public interface Copyable<T> {
  public T copy();
}
