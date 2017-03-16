package manlier.divide.and.conquer.algorithm;

/**
 * Created by manlier on 2017/3/15.
 */


import java.util.Arrays;

/**
 * 矩阵类
 */
public class Matrix {

    // 原始二维数组
    private double[][] originalMatrix;

    // 矩阵的行列数
    public final int rows, cols;

    // 矩阵的起始位置(对应于原始二位数组)
    private final int startI, startJ;

    private Matrix(double[][] originalMatrix, int rows, int cols) {
        this.originalMatrix = originalMatrix;
        this.cols = cols;
        this.rows = rows;
        this.startI = 0;
        this.startJ = 0;
    }

    private Matrix(double[][] originalMatrix, int rows, int cols, int startI, int startJ) {
        this.originalMatrix = originalMatrix;
        this.cols = cols;
        this.rows = rows;
        this.startI = startI;
        this.startJ = startJ;
    }

    private Matrix(int rows, int cols) {
        this(new double[rows][cols], rows, cols);
    }

    public void set(int i, int j, double value) {
        originalMatrix[startI + i][startJ + j] = value;
    }

    public double get(int i, int j) {
        return originalMatrix[startI + i][startJ + j];
    }

    /**
     * 将当前矩阵划分为（partsInHor * partsInVer）个矩阵
     * 为了兼顾矩阵并非为 n * n 的矩阵或是但n不为2的幂的矩阵，
     * 处理过程将矩阵分为三大部分：
     *  1)右下角最后一个元素，划分该元素只需执行1次
     *  2)与右下角对应的左上角的部分（该部分可以平均划分）即循环执行：(partsInHor - 1) * (partsInVer - 1)次
     *  3)除左下角和右下角最后一个元素外的其余部分 循环将执行 (partsInHor - 1) + (partsInVer - 1) 次
     *
     *  则循环共执行：partsInHor * partsInVer 次
     *  即耗时：theta(partsInHor * partsInVer)
     *
     * @param partsInHor 横轴方向上分为几部分
     * @param partsInVer 纵轴方向上分为几部分
     * @return 新矩阵, 其中的每一个元素都是一个矩阵
     */
    private Matrix[][] partition(int partsInHor, int partsInVer) {

        // TODO: 检查 leds, ords的大小是否合法

        Matrix[][] matrices = new Matrix[partsInHor][partsInVer];

        int dx = rows / partsInHor, dy = cols / partsInVer; // 小矩阵的每一部分的维度
        int rx = rows - partsInHor * dx, ry = cols - partsInHor * dy; // 未除尽的余数

        //               startJ
        //
        //            | *  ..  * |
        // startI ->      ....      <- startI + rows
        //            | *  ..  * |
        //
        //            startJ + cols
        int i, j = 0;

        // 完成左上部分的划分，需执行(h - 1) * (v - 1) 次
        for (i = 0; i < partsInHor - 1; i++) {
            for (j = 0; j < partsInVer - 1; j++) {
                matrices[i][j] = new Matrix(originalMatrix, dx, dy, startI + i * dx, startJ + j * dy);
            }
        }

        // 完成右下部分的划分，只执行1次
        matrices[i][j] = new Matrix(originalMatrix, dx + rx, dy + ry, startI + i * dx, startJ + j * dy);

        // 完成其余部分的划分, 循环共执行 (h - 1) + (v - 1)次
        for (int k = i - 1; k >= 0; k--) {
            matrices[k][i] = new Matrix(originalMatrix, dy, rx + dx, startI +  k * dy, startJ + i * dx);
        }
        for (int k = j - 1; k >= 0; k--) {
            matrices[j][k] = new Matrix(originalMatrix, rx + dx, dy, startJ + j * dy, startI + k * dy);
        }

        return matrices;
    }

    /**
     * 将矩阵划分为4个，每个有n^2/4个元素
     * 花费 theta(1) 时间
     *
     * @return 包含4个子矩阵的新矩阵
     */
    public Matrix[][] partition() {
        return partition(2, 2);
    }


    public Matrix multiplyBy(Matrix other) {
        // TODO: 判断两个矩阵是否可以相乘
        Matrix C = new Matrix(this.rows, other.cols);

        // 若是 n * n 的矩阵
        if (rows == cols)
            return squareMatrixMultiplyRecursive(this, other);

        multiplyBy(other, C);

        return C;
    }


    /**
     * 乘以另一个矩阵
     * 对于行列数为 m * n 与 n * p 的矩阵相乘来说：
     * 将循环 m * n * p 次,
     * 即：将花费 theta(mnp)的时间
     * 特殊地，对于 n * n 的矩阵，有 theta(n^3)
     *
     * @param B 被乘数
     * @param C 相乘的结果
     */
    private void multiplyBy(Matrix B, Matrix C) {
        int m = this.rows;
        int n = B.cols;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                C.set(i, j, 0);
                for (int k = 0; k < this.cols; k++) {
                    C.set(i, j, this.get(i, k) * B.get(k, j) + C.get(i, j));
                }
            }
        }
    }

    /**
     * 对于方阵采用的分治算法
     * 显然的对于 n=1 的情况， T(n) = theta(1)
     * 当 n > 1 时，划分矩阵需要 theta(1) 时间
     * 8次递归调用需要 8T(n/2)时间
     * 4次矩阵加法需要 4 * theta(n^2 / 4) = theta(n^2)时间
     * 因此
     * T(n) = theta(1) {n = 1}     或
     * T(n) = 8T(n/2) + theta(n^2)
     * 利用住方法对其求解，得T(n) = theta(n^3)
     *
     * @param A 方阵A
     * @param B 方阵B
     * @return 相乘的结果
     */
    private Matrix squareMatrixMultiplyRecursive(Matrix A, Matrix B) {
        int m = A.rows, n = B.cols;
        Matrix C = new Matrix(m, n);
        // 对于n不为2的幂的情况，划分到最后将出现 1 * n 或 n * 1的情况
        if (m == 1 || n == 1)
            A.multiplyBy(B, C);
        else {
            Matrix[][]
                    subMatrixsA = A.partition(),
                    subMatrixsB = B.partition(),
                    subMatrixsC = C.partition();

            // C11 = A11 * B11 + A12 * B21
            squareMatrixMultiplyRecursive(subMatrixsA[0][0], subMatrixsB[0][0])
                    .add(squareMatrixMultiplyRecursive(subMatrixsA[0][1], subMatrixsB[1][0]), subMatrixsC[0][0]);
            // C12 = A11 * B12 + A12 * B22
            squareMatrixMultiplyRecursive(subMatrixsA[0][0], subMatrixsB[0][1])
                    .add(squareMatrixMultiplyRecursive(subMatrixsA[0][1], subMatrixsB[1][1]), subMatrixsC[0][1]);
            // C21 = A21 * B22 + A21 * B11
            squareMatrixMultiplyRecursive(subMatrixsA[1][0], subMatrixsB[0][0])
                    .add(squareMatrixMultiplyRecursive(subMatrixsA[1][1], subMatrixsB[1][0]), subMatrixsC[1][0]);
            // C22 = A21 * B12 + A22 * B22
            squareMatrixMultiplyRecursive(subMatrixsA[1][0], subMatrixsB[0][1])
                    .add(squareMatrixMultiplyRecursive(subMatrixsA[1][1], subMatrixsB[1][1]), subMatrixsC[1][1]);

        }
        return C;
    }

    /**
     * 执行矩阵加法
     * 将花费 theta(m * n)时间
     *
     * @param other        另一个矩阵
     * @param targetMatrix 目标矩阵
     */
    private void add(Matrix other, Matrix targetMatrix) {
        // TODO: 判断是否可以相加
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                targetMatrix.set(i, j, this.get(i, j) + other.get(i, j));
            }
        }
    }

    private void subtract(Matrix other, Matrix targetMatrix) {
        // TODO: 判断是否可以相减
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                targetMatrix.set(i, j, this.get(i, j) - other.get(i, j));
            }
        }
    }

    public Matrix add(Matrix other) {
        Matrix result = new Matrix(rows, cols);
        add(other, result);
        return result;
    }

    public Matrix subtract(Matrix other) {
        Matrix result = new Matrix(rows, cols);
        subtract(other, result);
        return result;
    }

    /**
     * 从一个原始二维数组生成一个矩阵
     *
     * @param originalMatrix 原始二维数组
     * @return 矩阵
     */
    public static Matrix from(double[][] originalMatrix) {
        // TODO: 检查rows, cols的大小是否合法，同时判断是否为不规则数组，不规则数组为不合法
        return new Matrix(originalMatrix, originalMatrix.length, originalMatrix[0].length);
    }

    /**
     * 从一个原始二维数组生成一个矩阵
     * 并指定行列数
     *
     * @param originalMatrix 始二维数组
     * @param rows           矩阵行数
     * @param cols           矩阵列数
     * @return 矩阵
     */
    public static Matrix from(double[][] originalMatrix, int rows, int cols) {
        // TODO: 检查rows, cols的大小是否合法，同时判断是否为不规则数组，不规则数组为不合法
        return new Matrix(originalMatrix, rows, cols);
    }

    /**
     * 创建一个空矩阵
     *
     * @param rows 矩阵行数
     * @param cols 矩阵列数
     * @return 空矩阵
     */
    public static Matrix create(int rows, int cols) {
        // TODO: 检查rows, cols的大小是否合法
        return new Matrix(rows, cols);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Matrix matrix = (Matrix) o;

        return rows == matrix.rows && cols == matrix.cols && startI == matrix.startI && startJ == matrix.startJ && Arrays.deepEquals(originalMatrix, matrix.originalMatrix);
    }

    @Override
    public int hashCode() {
        int result = Arrays.deepHashCode(originalMatrix);
        result = 31 * result + rows;
        result = 31 * result + cols;
        result = 31 * result + startI;
        result = 31 * result + startJ;
        return result;
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder("\n{\n");

        for (int i = startI; i < rows + startI; i++) {
            builder.append("  | ");
            for (int j = startJ; j < cols + startJ; j++) {
                builder.append(String.format("%-6.2f", originalMatrix[i][j])).append(" ");
            }

            builder.append("|\n");
        }

        builder.append("  rows=").append(rows)
                .append(", cols=").append(cols)
                .append(", startI=").append(startI)
                .append(", startJ=").append(startJ)
                .append("\n}");

        return builder.toString();
    }
}
