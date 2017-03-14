package manlier.sort.algorithm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by manlier on 2017/3/13.
 */

/**
 * 排序算法实例的抽象类，方便研究
 * @param <T>
 */
public abstract class AbstractSort<T extends Comparable<T>> {

    protected List<T> source;

    protected Comparator<T> comparator;

    protected AbstractSort(List<T> source) {
        this.source = source;
        this.in(Comparable::compareTo);
    }

    public List<T> getSource() {
        return source;
    }

    public AbstractSort<T> in(Comparator<T> comparator) {
        this.comparator = comparator;
        return this;
    }


    public abstract AbstractSort<T> sort();

}
