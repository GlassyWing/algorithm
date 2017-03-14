package manlier.sort.algorithm;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Consumer;

import static org.junit.Assert.*;

/**
 * Created by manlier on 2017/3/12.
 */
public class SortTest {

    private ArrayList<Integer> source = new ArrayList<>(Arrays.asList(1, 3, 9, 7, 8, 13, 29, 10));

    @Before
    public void timerStart() {

    }

    @After
    public void timerOver() {

    }

    @Test
    public void insertSort() {
        ArrayList<Integer> sortedList = InsertionSort.from(source).sortInRecursive().getSource();
        System.out.println(sortedList);
    }

    @Test
    public void selectionSort() {
        ArrayList<Integer> sortedList = SelectionSort.from(source).sort().getSource();
        System.out.println(sortedList);
    }

    @Test
    public void mergeSort() {
        System.out.println(MergeSort.from(source).sort().getInversions());
    }

    @Test
    public void bubbleSort() {
        System.out.println(BubbleSort.from(source).sort().getSource());
    }
}