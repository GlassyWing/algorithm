package manlier.divide.and.conquer.algorithm;

import org.junit.Test;

import javax.management.loading.MLet;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by manlier on 2017/3/14.
 */
public class DivideAndConquerTest {

    private List<Double> source = Arrays.asList(13d, -3d, -25d, 20d, -3d, -16d, -23d, 18d, 20d, -7d, 12d, -5d, -22d, 15d, -4d, 7d);

    @Test
    public void maxSubarray() {
        System.out.println(MaxSubarray.from(source).findMaximumSubarrayInLinear());
        System.out.println(MaxSubarray.from(source).findMaximumSubarray());
    }

    private double[][] originalA = {
            {1, 2, 3},
            {4, 5, 6}
    }, originalB = {
            {1, 4},
            {2, 5},
            {3, 6}
    }, originalC = {
            {1, 2, 3, 6},
            {3, 4, 9, 11},
            {4, 4, 1, 10},
            {5, 5, 7, 3}
    }, originalD = {
            {3, 4},
            {7, 6},
            {9, 8}
    }, originalE = {
            {1, 2, 3},
            {3, 4, 9},
            {4, 4, 1},

    }, originalF = {
            {1, 2, 3, 7},
            {3, 4, 9, 1},
            {4, 4, 1, 3},
            {2, 5, 3, 3},

    }, originalG = {
            {1, 2, 3},
            {3, 4, 9},
            {4, 4, 1},
    };

    @Test
    public void testMatrixMultiply() {
        System.out.println("not square matrix multiply");
        Matrix matrixA = Matrix.from(originalA), matrixB = Matrix.from(originalB);
        System.out.println(matrixA.multiplyBy(matrixB));

        System.out.println("square matrix multiply");
        Matrix matrixE = Matrix.from(originalE), matrixG = Matrix.from(originalG);
        System.out.println(matrixE.multiplyBy(matrixG));
    }

    @Test
    public void testMatrixPartition() {
        Matrix matrixE = Matrix.from(originalF);
        System.out.println(Arrays.deepToString(matrixE.partition()));
    }

    @Test
    public void testMatrixAddition() {
        System.out.println(Matrix.from(originalB).add(Matrix.from(originalD)));
    }

}