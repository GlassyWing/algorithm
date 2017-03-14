package manlier.sort.algorithm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by manlier on 2017/3/12.
 */

/**
 * 插入排序
 *
 * @param <T>
 */
public class InsertionSort<T extends Comparable<T>> extends AbstractSort<T> {

    public InsertionSort(List<T> source) {
        super(source);
    }

    public InsertionSort<T> in(Comparator<T> comparator) {
        this.comparator = comparator;
        return this;
    }


    /**
     * 由以下对循环的分析，可知其渐进上界为 O(n^2), 此时数序列与期望的序列相反
     * 渐进下界为 Omega(n)
     *
     * @return InsertionSort<T>
     */
    public InsertionSort<T> sort() {
        for (int j = 1; j < source.size(); j++) {                            // n
            T key = source.get(j);                                           // n - 1
            // Insert source[j] into the sorted sequence source[1..j-1]
            int i = j - 1;                                                   // n - 1
            while (i >= 0 && comparator.compare(key, source.get(i)) < 0) {   // (n - 1) * j
                source.set(i + 1, source.get(i));                            // (n - 1) * (j - 1)
                --i;                                                         // (n - 1) * (j - 1)
            }
            source.set(i + 1, key);                                          // n - 1
        }
        return this;
    }

    /**
     * 以下是以递归的形式进行插入排序
     *
     * @return
     */
    public InsertionSort<T> sortInRecursive() {

        sort(0, source.size() - 1);

        return this;
    }

    /**
     * 递归排序，其最坏条件下的递归表达式为 T(n) = theta(1){n = 1}
     * 或 T(n) = T(n - 1) + theta(n) {n > 1}
     *
     * @param p 起始指针
     * @param r 末尾指针（指向最后一个元素）
     */
    private void sort(int p, int r) {

        if (p < r) {
            sort(p, r - 1);
            insert(p, r);
        }

    }

    /**
     * 插入操作，每次插入A[n]之前，A[0..n-1]都已经排好序，
     * 因此最坏的情况下，将贡献theta(n)的代价
     *
     * @param p 起始指针
     * @param r 要插入的数的位置指针
     */
    private void insert(int p, int r) {
        T key = source.get(r);
        int i = r - 1;
        while (i >= p && comparator.compare(source.get(i), key) > 0) {
            source.set(i + 1, source.get(i));
            --i;
        }
        source.set(i + 1, key);
    }

    public static <T extends Comparable<T>> InsertionSort<T> from(ArrayList<T> source) {
        return new InsertionSort<>(source);
    }
}
