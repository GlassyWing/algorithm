package manlier.divide.and.conquer.algorithm;

import org.junit.Test;

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

}