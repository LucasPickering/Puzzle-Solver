package puzzlesolver;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public final class Funcs {

  /**
   * Exponentially searches for the last element in the given sorted list that matches the element at
   * {@param index}. The giv3en liStarts the search at {@param index}, and moves left or right
   * according to {@param left}. Exponential searching means that the first step looks 1 element
   * ahead, then 2 elements ahead of that, then 4, 8, ..., until an element is found that does not
   * match the element at {@param index}. The search then starts back at 0 and repeats until it finds
   * a change from matching to non-matching on the first step (1-element step), meaning it finds the
   * edge of the matching elements.
   *
   * @param <E>        the type of element in the list
   * @param list       the sorted list to search (non-null, non-empty)
   * @param comparator a comparator to compare the elements in the list (non-null)
   * @param index      the index at which to start the search (in list bounds)
   * @param left       true to search to the left, false to search to the right
   * @return the index of the last element in the list for which {@code comparator.compare(list[i],
   * list[i + 1]) == 0}
   * @throws NullPointerException      if {@code list == null || comparator == null}
   * @throws IndexOutOfBoundsException if {@code index} is out of bounds for the list
   */
  public static <E> int expSearch(List<E> list, Comparator<E> comparator, int index,
                                  boolean left) {
    Objects.requireNonNull(list);
    Objects.requireNonNull(comparator);
    if (index < 0 || index >= list.size()) {
      throw new IndexOutOfBoundsException(index + " is out of bounds");
    }
    for (int i = 0; true; i++) {
      // The index of the next element to check; grows exponentially
      int nextIndex = index + (1 << i) * (left ? -1 : 1);
      final boolean oob = left ? nextIndex < 0 : nextIndex >= list.size();

      // If the nextIndex is out of bounds or the element at that index doesn't match p
      if (oob || comparator.compare(list.get(index), list.get(nextIndex)) != 0) {
        // If this is the first iteration in the loop (nextIndex == index - 1), stop
        if (i == 0) {
          return index; // Return this index
        }
        i = -1; // Reset i to reset the exponentiation after this iteration
      } else { // If we should keep exponentially looking
        index = nextIndex; // Set current index to next and let it loop again
      }
    }
  }

  /**
   * Generates a random number in the range [min, max].
   *
   * @param random the random generator to be used
   * @param min    the minimum of the number
   * @param max    the maximum of the number
   */
  public static double randomInRange(Random random, double min, double max) {
    return random.nextDouble() * (max - min) + min;
  }

  /**
   * Generates a random number in the range [min, max], and randomly negates it (on a 50-50 chance).
   *
   * @param random the random generator to be used
   * @param min    the minimum of the number
   * @param max    the maximum of the number
   */
  public static double randomInRangeNegate(Random random, double min, double max) {
    return randomInRange(random, min, max) * (random.nextInt(2) == 0 ? -1 : 1);
  }
}
