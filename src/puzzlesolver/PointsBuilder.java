package puzzlesolver;

import java.util.LinkedList;

public class PointsBuilder {

  LinkedList<double[]> elements = new LinkedList<>();
  int length;

  public PointsBuilder(double... initialElements) {
    add(initialElements);
  }

  public void add(double... newElements) {
    if (newElements != null && newElements.length != 0) {
      length += newElements.length;
      elements.add(newElements);
    }
  }

  public double[] toPoints() {
    double[] result = new double[length];
    int position = 0;
    for (double[] array : elements) {
      System.arraycopy(array, 0, result, position, array.length);
      position += array.length;
    }

    return result;
  }

  public int getLength() {
    return length;
  }
}
