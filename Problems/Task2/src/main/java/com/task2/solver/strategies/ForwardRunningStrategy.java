package com.task2.solver.strategies;

import com.task2.utils.Pair;

import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;

public class ForwardRunningStrategy implements RunningStrategy {
    @Override
    public Iterator<List<Double>> initIterator(LinkedList<List<Double>> matrix) {
        return matrix.iterator();
    }

    @Override
    public Pair<Double, Double> getCoefficients(List<Double> matrixRow, Pair<Double, Double> previous)
            throws IllegalArgumentException {
        if (matrixRow.size() != ELEMENTS_IN_MATRIX_ROW) {
            throw new IllegalArgumentException("there should be only " + ELEMENTS_IN_MATRIX_ROW
                                               + " in a matrix row");
        }
        if (previous == null) {
            return new Pair<Double, Double>(-matrixRow.get(2) / matrixRow.get(1), matrixRow.get(3) / matrixRow.get(1));
        }
        Double denominator = matrixRow.get(1) + matrixRow.get(0) * previous.getFirst();
        return new Pair<Double, Double>(-matrixRow.get(2) / denominator,
                                        (matrixRow.get(3) - matrixRow.get(0) * previous.getSecond())
                                        / denominator);
    }
}
