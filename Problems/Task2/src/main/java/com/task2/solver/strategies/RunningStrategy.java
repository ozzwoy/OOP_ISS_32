package com.task2.solver.strategies;

import com.task2.utils.Pair;

import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;

public interface RunningStrategy {
    int ELEMENTS_IN_MATRIX_ROW = 4;

    Iterator<List<Double>> initIterator(LinkedList<List<Double>> matrix);

    Pair<Double, Double> getCoefficients(List<Double> matrixRow, Pair<Double, Double> previous);
}
