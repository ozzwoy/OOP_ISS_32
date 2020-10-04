package com.task2.solver;

import com.task2.solver.strategies.RunningStrategy;
import com.task2.utils.Pair;

import java.util.*;
import java.util.concurrent.Callable;

public class RecurrentCoefficientsCalculator implements Callable<ArrayList<Pair<Double, Double>>> {
    private LinkedList<List<Double>> matrix;
    private RunningStrategy strategy;
    private ArrayList<Pair<Double, Double>> result;
    private int resultSize;

    public RecurrentCoefficientsCalculator(RunningStrategy strategy, LinkedList<List<Double>> matrix, int resultSize) {
        this.strategy = strategy;
        this.matrix = matrix;
        this.resultSize = resultSize;
        this.result = new ArrayList<>(resultSize);
    }

    @Override
    public ArrayList<Pair<Double, Double>> call() {
        Iterator<List<Double>> iterator = strategy.initIterator(matrix);

        if (iterator.hasNext()) {
            result.add(strategy.getCoefficients(iterator.next(), null));
            for (int i = 1; i < resultSize; i++) {
                result.add(strategy.getCoefficients(iterator.next(), result.get(i - 1)));
            }
        }
        return result;
    }
}
