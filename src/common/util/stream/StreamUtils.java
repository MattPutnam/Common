package common.util.stream;

import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import common.tuple.Pair;

/**
 * A collection of utilities for operating on Streams.  These are mostly
 * taken from things already present in Scala collections but lacking in
 * Java 8.
 * 
 * @author Matt Putnam
 */
public class StreamUtils {
  private StreamUtils() {}
  
  /**
   * Zips two streams together into one, using a given function to combine
   * corresponding elements.  The resulting stream's length is the minimum of
   * the two inputs; trailing unpaired elements in the longer stream are
   * dropped.
   * @param a the first Stream
   * @param b the second Stream
   * @param zipper the function combining corresponding elements of the streams
   * @return a Stream with <code>zipper</code> applied to each pair of elements
   */
  public static <A, B, C> Stream<C> zip(
      Stream<? extends A> a,
      Stream<? extends B> b,
      BiFunction<? super A, ? super B, ? extends C> zipper) {
    
    Objects.requireNonNull(zipper);
    @SuppressWarnings("unchecked")
    final Spliterator<A> aSpliterator = (Spliterator<A>) Objects.requireNonNull(a).spliterator();
    @SuppressWarnings("unchecked")
    final Spliterator<B> bSpliterator = (Spliterator<B>) Objects.requireNonNull(b).spliterator();

    // Zipping loses DISTINCT and SORTED characteristics
    final int characteristics = aSpliterator.characteristics() & 
                                bSpliterator.characteristics() &
                                ~(Spliterator.DISTINCT | Spliterator.SORTED);

    final long zipSize = ((characteristics & Spliterator.SIZED) != 0)
        ? Math.min(aSpliterator.getExactSizeIfKnown(), bSpliterator.getExactSizeIfKnown())
        : -1;

    final Iterator<A> aIterator = Spliterators.iterator(aSpliterator);
    final Iterator<B> bIterator = Spliterators.iterator(bSpliterator);
    final Iterator<C> cIterator = new Iterator<C>() {
      @Override
      public boolean hasNext() {
        return aIterator.hasNext() && bIterator.hasNext();
      }

      @Override
      public C next() {
        return zipper.apply(aIterator.next(), bIterator.next());
      }
    };

    final Spliterator<C> split = Spliterators.spliterator(cIterator, zipSize, characteristics);
    return StreamSupport.stream(split, (a.isParallel() || b.isParallel()));
  }
  
  /**
   * Zips two streams together into a single stream of pairs.  The resulting
   * stream's length is the minimum of the two inputs; trailing unpaired
   * elements in the longer stream are dropped.
   * @param a the first Stream
   * @param b the second Stream
   * @return a Stream of each paired element
   */
  public static <A, B> Stream<Pair<A, B>> zip(Stream<? extends A> a, Stream<? extends B> b) {
    return zip(a, b, (x, y) -> Pair.<A, B>make(x, y));
  }
  
  /**
   * Zips a stream with its (zero-based) index.
   * @param stream the Stream
   * @return the elements of <tt>stream</tt> paired with their indices
   */
  public static <T> Stream<Pair<T, Integer>> zipWithIndex(Stream<? extends T> stream) {
    return zip(stream, IntStream.iterate(0, x -> x+1).boxed());
  }
  
  /**
   * Finds the first element in the stream matching the given predicate, or
   * <tt>Optional.empty()</tt> if none is found
   * 
   * @param stream the Stream
   * @param predicate the Predicate
   * @return the first element in <tt>stream</tt> matching <tt>predicate</tt>,
   *         or <tt>Optional.empty()</tt> if none is found
   */
  public static <T> Optional<T> findFirst(Stream<T> stream, Predicate<? super T> predicate) {
    return stream.filter(predicate).findFirst();
  }
}
