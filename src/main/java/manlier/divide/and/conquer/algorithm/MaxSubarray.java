package manlier.divide.and.conquer.algorithm;

/**
 * Created by manlier on 2017/3/14.
 */

import manlier.util.algorithm.ThreeTuple;

import java.util.List;

/**
 * 寻找最大子数组
 */
public class MaxSubarray {

    private List<Double> source;

    public MaxSubarray(List<Double> source) {
        this.source = source;
    }

    public List<Double> getSource() {
        return source;
    }

    public ThreeTuple<Integer, Integer, Double> findMaximumSubarray() {
        return findMaximumSubarray(0, source.size() - 1);
    }

    public double findMaximumSubarrayInLinear() {
        return findMaximumSubarrayInLinear(0, source.size() - 1);
    }

    /**
     * 寻找最大子数组的递归实现
     * 该算法类似于 插入排序
     * 故总代价为 theta(n * lgn)
     *
     * @param low  数组最小边界
     * @param high 数组最大边界
     * @return 三元组
     * (
     * maxLeft,  // 最大子数组最小边界
     * maxRight, // 最大子数组最大边界
     * leftSum + rightSum  // 最大子数组和
     * )
     */
    public ThreeTuple<Integer, Integer, Double> findMaximumSubarray(int low, int high) {

        ThreeTuple<Integer, Integer, Double> leftSubarray, rightSubarray, crossSubarray;

        if (high == low)
            return new ThreeTuple<>(low, high, source.get(low));
        else {
            int mid = (low + high) >> 1;
            leftSubarray = findMaximumSubarray(low, mid);
            rightSubarray = findMaximumSubarray(mid + 1, high);
            crossSubarray = findMaxCrossingSubarray(low, mid, high);

            if (leftSubarray.third >= rightSubarray.third && leftSubarray.third >= crossSubarray.third)
                return leftSubarray;
            else if (rightSubarray.third >= leftSubarray.third && rightSubarray.third >= crossSubarray.third)
                return rightSubarray;
            else return crossSubarray;
        }
    }


    /**
     * 以非递归，线性方式找到最大子数组的和
     * 其代价为 theta(n)
     * 注意：
     * 1)使用该方式的前提是数组元素不能都为负数
     * 2)该算法没办法确定最大子数组的最小和最大边界
     *
     * @param low  数组最小边界
     * @param high 数组最大边界
     * @return 最大子数组的和
     */
    public double findMaximumSubarrayInLinear(int low, int high) {

        // 纪录目前为止找到的最大子数组的和
        double maxSum = source.get(low), thisSum = maxSum;

        for (int i = low + 1; i <= high; i++) {
            thisSum += source.get(i);
            if (thisSum > maxSum) {
                maxSum = thisSum;
            } else if (thisSum < 0) {  // 舍弃目前子数组
                thisSum = 0;
            }
        }

        return maxSum;
    }

    /**
     * 寻找跨越中点的子数组，在中心点以左for循环迭代mid次
     * 在中心点右侧for循环迭代(high - mid + 1)次
     * 则总共迭代 high + 1 = n次
     * 则总共贡献代价 theta(n)
     *
     * @param low  数组最小边界
     * @param mid  数组中点位置
     * @param high 数组最大边界
     * @return 返回一个三元组，元素分别为
     * (
     * maxLeft,  // 最大子数组最小边界
     * maxRight, // 最大子数组最大边界
     * leftSum + rightSum  // 最大子数组和
     * )
     */
    private ThreeTuple<Integer, Integer, Double> findMaxCrossingSubarray(int low, int mid, int high) {

        int maxLeft = mid, maxRight = mid + 1;
        double leftSum = source.get(maxLeft);
        double rightSum = source.get(maxRight);
        double sum = 0;

        for (int i = mid; i >= low; i--) {
            sum = sum + source.get(i);
            if (sum > leftSum) {
                leftSum = sum;
                maxLeft = i;
            }
        }
        sum = 0;
        for (int j = mid + 1; j <= high; j++) {
            sum = sum + source.get(j);
            if (sum > rightSum) {
                rightSum = sum;
                maxRight = j;
            }
        }

        return new ThreeTuple<>(maxLeft, maxRight, leftSum + rightSum);
    }

    public static MaxSubarray from(List<Double> source) {
        return new MaxSubarray(source);
    }
}
