package studio.avis.salary;

public class Range<V extends Comparable<V>> {

    private final V min;
    private final V max;

    public static <V extends Comparable<V>> Range<V> of(V min, V max) {
        return new Range<V>(min, max);
    }

    private Range(V min, V max) {
        this.min = min;
        this.max = max;
    }

    public V getMin() {
        return min;
    }

    public V getMax() {
        return max;
    }

    public boolean contains(V value) {
        return min.compareTo(value) <= 0 && max.compareTo(value) > 0;
    }
}
