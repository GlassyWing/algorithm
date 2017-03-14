package manlier.sort.algorithm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by manlier on 2017/3/13.
 */

/**
 * 冒泡算法
 * @param <T>
 */
public class BubbleSort<T extends Comparable<T>> extends AbstractSort<T> {

    public BubbleSort(List<T> source) {
        super(source);
    }

    /**
     * 冒泡排序最佳和最差所花代价均为 theta(n^2)
     * 故为稳定算法
     * @return self
     */
    @Override
    public AbstractSort<T> sort() {
        for (int i = source.size() - 1; i > 0; --i) {
            for (int j = 0; j < i; ++j) {
                if (comparator.compare(source.get(j), source.get(j + 1)) > 0) {
                    T key = source.get(j + 1);
                    source.set(j + 1, source.get(j));
                    source.set(j, key);
                }
            }
        }
        return this;
    }

    public static  <T extends Comparable<T>> BubbleSort<T> from(ArrayList<T> source) {
        return new BubbleSort<T>(source);
    }
}
