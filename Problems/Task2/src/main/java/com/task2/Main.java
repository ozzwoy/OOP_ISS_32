package com.task2;

import com.task2.solver.TridiagonalMatrixEquationsSystemSolver;
import com.task2.solver.exceptions.WrongMatrixElementsNumberException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        final int SIZE = 5;
        List<List<Double>> matrix = new ArrayList<>() {
            {
                add(Arrays.asList(null, 2.0, -1.0, -25.0));
                add(Arrays.asList(-3.0, 8.0, -1.0, 72.0));
                add(Arrays.asList(-5.0, 12.0, 2.0, -69.0));
                add(Arrays.asList(-6.0, 18.0, -4.0, -156.0));
                add(Arrays.asList(-5.0, 10.0, null, 20.0));
            }
        };

        try {
            System.out.println(TridiagonalMatrixEquationsSystemSolver.solve(matrix));
        } catch (WrongMatrixElementsNumberException e) {
            System.out.println(e.getMessage());
        }
    }
}
