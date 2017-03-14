package manlier.sort.algorithm;

/**
 * Created by manlier on 2017/3/13.
 */

import manlier.util.algorithm.TwoTuple;

import java.util.*;

/**
 * 归并排序
 */
public class MergeSort<T extends Comparable<T>> extends AbstractSort<T> {

    // 用于保存找到的逆序对(inversion)
    private ArrayList<TwoTuple<T, T>> inversions = new ArrayList<>();

    public MergeSort(List<T> source) {
        super(source);
    }

    /**
     * 此处设置新的‘比对器’函数，以处理含有‘哨兵’元素的情况
     *
     * @param comparator '比对器' 函数
     * @return AbstractSort<T>
     */
    @Override
    public AbstractSort<T> in(Comparator<T> comparator) {
        return super.in((o1, o2) -> {
            if (o1 == null) return 1;
            else if (o2 == null) return -1;
            else return comparator.compare(o1, o2);
        });
    }

    @Override
    public MergeSort<T> sort() {
        mergeSort(0, source.size() - 1);
        return this;
    }

    /**
     * 分治合并，
     * 由于分治时采用二分法，则第i层有2^i个结点，每个结点合并时贡献代价 c(n/2^i)
     * 每一层的代价则为 2^i * c(n/2^i) = c(n)
     * 而共有 lgn + 1 层
     * 则总代价为 cn * (lgn + 1) = cn*lgn + cn
     * 即：theta(n * lgn)
     *
     * @param p 起始指针
     * @param r 末尾指针(数组中最后一位的指针)
     */
    private void mergeSort(int p, int r) {
        if (p < r) {
            int q = (p + r) / 2;
            mergeSort(p, q);
            mergeSort(q + 1, r);
            merge(p, q, r);
        }
    }

    /**
     * 合并操作，除循环操作外，其余部分可看作常数时间
     * 则代价为 theta(n), n = r - p + 1
     *
     * @param p
     * @param q
     * @param r
     */
    @SuppressWarnings("unchecked")
    private void merge(int p, int q, int r) {

        int n1 = q - p + 1, n2 = r - q;

        // 从原数组中复制一半的元素，左右各一半
        ArrayList<T> L = new ArrayList(Arrays.asList(Arrays.copyOfRange(source.toArray(), p, q + 1)));
        ArrayList<T> R = new ArrayList(Arrays.asList(Arrays.copyOfRange(source.toArray(), q + 1, r + 1)));

        // 设置‘哨兵’元素，以避免在每个基本步骤必须检查是否有堆为空
        L.add(n1, null);
        R.add(n2, null);

        int i = 0, j = 0;
        for (int k = p; k <= r; k++) {
            if (comparator.compare(L.get(i), R.get(j)) <= 0) {
                source.set(k, L.get(i));
                ++i;
            } else {
                source.set(k, R.get(j));
                if(L.get(i) != null) {
                    inversions.add(new TwoTuple<>(L.get(i), R.get(j)));
                }
                ++j;
            }
        }
    }

    public ArrayList<TwoTuple<T, T>> getInversions() {
        return inversions;
    }

    public static <T extends Comparable<T>> MergeSort<T> from(ArrayList<T> source) {
        return new MergeSort<T>(source);
    }
}
