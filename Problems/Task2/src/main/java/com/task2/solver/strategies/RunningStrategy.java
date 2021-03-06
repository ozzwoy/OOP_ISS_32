package com.task2.solver.strategies;

import com.task2.utils.Pair;

import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;

public interface RunningStrategy {
    Iterator<List<Double>> initIterator(LinkedList<List<Double>> matrix);

    Pair<Double, Double> getCoefficients(List<Double> matrixRow, Pair<Double, Double> previous);
}
