package puzzlesolver.arrays;

import java.util.Collection;

public interface ArrayBuilder<T> {

  /**
   * Add all elements of a {@link Collection}.
   *
   * @param c collection to add
   */
  void addAll(Collection<? extends T> c);

  /**
   * Add all elements of an array.
   *
   * @param a array to add
   */
  void addAll(T[] a);

  /**
   * Add element.
   *
   * @param e element to add
   */
  void add(T e);

  T[] toPoints();

  int size();
}
