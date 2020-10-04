package com.task2;

import com.task2.solver.TridiagonalMatrixEquationsSystemSolver;
import com.task2.solver.exceptions.WrongMatrixElementsNumberException;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainTest {
    public MainTest() { }

    @Test
    public void checkOnSmallMatrix() throws WrongMatrixElementsNumberException {
        List<List<Double>> matrix = new ArrayList<>() {
            {
                add(Arrays.asList(null, 2.0, -1.0, -25.0));
                add(Arrays.asList(-3.0, 8.0, -1.0, 72.0));
                add(Arrays.asList(-5.0, 10.0, null, 20.0));
            }
        };

        Assert.assertEquals(0.0, SolutionChecker.getError(matrix,
                TridiagonalMatrixEquationsSystemSolver.solve(matrix)), 10e-7);
    }

    @Test
    public void checkOnOrdinaryMatrix() throws WrongMatrixElementsNumberException {
        List<List<Double>> matrix = new ArrayList<>() {
            {
                add(Arrays.asList(null, 2.0, -1.0, -25.0));
                add(Arrays.asList(-3.0, 8.0, -1.0, 72.0));
                add(Arrays.asList(-5.0, 12.0, 2.0, -69.0));
                add(Arrays.asList(-6.0, 18.0, -4.0, -156.0));
                add(Arrays.asList(-5.0, 10.0, null, 20.0));
            }
        };

        Assert.assertEquals(0.0, SolutionChecker.getError(matrix,
                TridiagonalMatrixEquationsSystemSolver.solve(matrix)), 10e-7);
    }

    @Test
    public void checkOnBigMatrix() throws WrongMatrixElementsNumberException {
        List<List<Double>> matrix = new ArrayList<>() {
            {
                add(Arrays.asList(null, 2.0, -1.0, -25.0));
                add(Arrays.asList(-3.0, 8.0, -1.0, 72.0));
                add(Arrays.asList(-5.0, 12.0, 2.0, -69.0));
                add(Arrays.asList(-6.0, 18.0, -4.0, -156.0));
                add(Arrays.asList(34.0, 2.0, -41.0, 5.0));
                add(Arrays.asList(21.0, -5.0, -13.0, -123.0));
                add(Arrays.asList(-11.0, 19.0, 76.0, 39.0));
                add(Arrays.asList(46.0, 23.0, 12.0, 56.0));
                add(Arrays.asList(75.0, 23.0, -10.0, 243.0));
                add(Arrays.asList(34.0, -56.0, -32.0, -564.0));
                add(Arrays.asList(-5.0, 10.0, null, 20.0));
            }
        };

        Assert.assertEquals(0.0, SolutionChecker.getError(matrix,
                TridiagonalMatrixEquationsSystemSolver.solve(matrix)), 10e-7);
    }
}
