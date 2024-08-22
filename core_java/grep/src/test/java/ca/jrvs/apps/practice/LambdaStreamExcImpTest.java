package ca.jrvs.apps.practice;

import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class LambdaStreamExcImpTest {

    private LambdaStreamExcImp lambdaStreamExcImp;

    @Before
    public void setup() {
        lambdaStreamExcImp = new LambdaStreamExcImp();
    }

    @Test
    public void createStrStream() {
        Stream<String> result = lambdaStreamExcImp.createStrStream("1", "2", "3");
        assertEquals(3, result.count());
    }

    @Test
    public void createIntStream() {
        IntStream result = lambdaStreamExcImp.createIntStream(new int[] { 1, 2, 3 });
        assertEquals(3, result.count());
    }

    @Test
    public void createRangeIntStream() {
        IntStream result = lambdaStreamExcImp.createInStream(1, 4);
        assertEquals(3, result.count());
    }

    @Test
    public void createToUpperCase() {
        Set<String> answers = new HashSet<>();
        answers.add("ASDF");
        Stream<String> result = lambdaStreamExcImp.toUpperCase("asdf", "Asdf", "ASDF");
        result.forEach(value -> assertTrue(answers.contains(value)));
    }

    @Test
    public void createFilter() {
        Stream<String> result = lambdaStreamExcImp.filter(Stream.of("1", "a", "3"), "\\d");
        assertEquals(2, result.count());
    }

    @Test
    public void squareRootIntStream() {
        Set<Double> answers = new HashSet<>();
        answers.add(1.);
        answers.add(2.);
        answers.add(3.);
        IntStream input = lambdaStreamExcImp.createIntStream(new int[] { 1, 4, 9 });
        DoubleStream result = lambdaStreamExcImp.squareRootIntStream(input);
        result.forEach(value -> assertTrue(answers.contains(value)));
    }

    @Test
    public void getOdd() {
        IntStream input = lambdaStreamExcImp.createInStream(1, 3);
        IntStream result = lambdaStreamExcImp.getOdd(input);
        assertEquals(1, result.count());
    }

    @Test
    public void flatNestedInt() {
        Set<Integer> answers = new HashSet<>();
        answers.add(1);
        answers.add(4);
        answers.add(9);
        answers.add(16);
        answers.add(25);
        answers.add(36);
        Stream<List<Integer>> input = Stream.of(Arrays.asList(1, 2, 3), Arrays.asList(4, 5, 6));
        Stream<Integer> result = lambdaStreamExcImp.flatNestedInt(input);
        result.forEach(value -> assertTrue(answers.contains(value)));
    }
}
