package manlier.sort.algorithm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by manlier on 2017/3/12.
 */

/**
 * 选择排序
 *
 * @param <T>
 */
public class SelectionSort<T extends Comparable<T>> extends AbstractSort<T> {


    public SelectionSort(List<T> source) {
        super(source);
    }

    public SelectionSort<T> sort() {

        for (int i = 0; i < source.size(); i++) {
            T min = source.get(i);
            for (int j = i + 1; j < source.size(); j++) {
                if (comparator.compare(source.get(j), min) < 0) {
                    source.set(i, source.get(j));
                    source.set(j, min);
                    break;
                }
            }
        }
        return this;
    }

    public static <T extends Comparable<T>> SelectionSort<T> from(ArrayList<T> source) {
        return new SelectionSort<>(source);
    }
}
