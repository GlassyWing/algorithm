package manlier.algebra.algorithm;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by manlier on 2017/3/13.
 */
public class AlgbraTest {

    @Test
    public void testHorner() {
        // This should be '5'
        System.out.println(new Horner().calculate(new double[]{1, 2}, 2));
    }

}