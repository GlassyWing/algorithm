package manlier.algebra.algorithm;

/**
 * Created by manlier on 2017/3/13.
 */

/**
 * 霍纳规则，计算一元多次多项式
 */
public class Horner {

    /**
     * @param coefficient 系数
     * @param x x的值
     * @return 计算完成的结果
     */
    public double calculate(double[] coefficient, double x) {
        double y = 0;
        for (int i = coefficient.length - 1; i >= 0; --i) {
            y = coefficient[i] + x * y;
        }
        return  y;
    }
}
