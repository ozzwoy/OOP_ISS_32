package com.task2.solver.strategies;

import com.task2.utils.Pair;

import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;

public class BackwardRunningStrategy implements RunningStrategy {
    @Override
    public Iterator<List<Double>> initIterator(LinkedList<List<Double>> matrix) {
        return matrix.descendingIterator();
    }

    @Override
    public Pair<Double, Double> getCoefficients(List<Double> matrixRow, Pair<Double, Double> previous) {
        if (previous == null) {
            return new Pair<Double, Double>(-matrixRow.get(0) / matrixRow.get(1), matrixRow.get(3) / matrixRow.get(1));
        }
        Double denominator = matrixRow.get(1) + matrixRow.get(2) * previous.getFirst();
        return new Pair<Double, Double>(-matrixRow.get(0) / denominator,
                                        (matrixRow.get(3) - matrixRow.get(2) * previous.getSecond()) / denominator);
    }
}
