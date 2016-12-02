package puzzlesolver.arrays;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class PointsBuilder implements ArrayBuilder<Double> {

    private List<Double[]> elements = new LinkedList<>();
    private int size = 0;

    public PointsBuilder(Double... initialElements) {
        addAll(initialElements);
    }

    @Override
    public void addAll(Double... elements) {
        Objects.requireNonNull(elements);
        if (elements.length != 0) {
            this.elements.add(elements);
            size += elements.length;
        }
    }

    @Override
    public void addAll(Collection<? extends Double> c) {
        Objects.requireNonNull(c);
        if (c.size() != 0) {
            elements.add(c.toArray(new Double[c.size()]));
            size += c.size();
        }
    }

    @Override
    public void add(Double e) {
        Objects.requireNonNull(e);
        elements.add(new Double[]{e});
        ++size;
    }

    @Override
    public Double[] toPoints() {
        Double[] result = new Double[size()];

        int position = 0;
        for (Double[] array : elements) {
            System.arraycopy(array, 0, result, position, array.length);
            position += array.length;
        }

        return result;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        elements.forEach(doubles -> sb.append(Arrays.toString(doubles)));
        return sb.toString();
    }
}
