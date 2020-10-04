package com.task2;

import java.util.List;

public class SolutionChecker {
    public static double getError(List<List<Double>> coefficientsMatrix, List<Double> roots) {
        double error = 0;

        error += Math.pow(roots.get(0) * coefficientsMatrix.get(0).get(1) +
                          roots.get(1) * coefficientsMatrix.get(0).get(2) - coefficientsMatrix.get(0).get(3), 2);

        for (int i = 1; i < roots.size() - 1; i++) {
            error += Math.pow(roots.get(i - 1) * coefficientsMatrix.get(i).get(0) +
                              roots.get(i) * coefficientsMatrix.get(i).get(1) +
                              roots.get(i + 1) * coefficientsMatrix.get(i).get(2) -
                              coefficientsMatrix.get(i).get(3), 2);
        }

        error += Math.pow(roots.get(roots.size() - 2) * coefficientsMatrix.get(coefficientsMatrix.size() - 1).get(0) +
                          roots.get(roots.size() - 1) * coefficientsMatrix.get(coefficientsMatrix.size() - 1).get(1) -
                          coefficientsMatrix.get(coefficientsMatrix.size() - 1).get(3), 2);

        return error;
    }
}
