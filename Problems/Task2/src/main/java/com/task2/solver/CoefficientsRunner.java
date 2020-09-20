package com.task2.solver;

import com.task2.solver.strategies.RunningStrategy;
import com.task2.utils.Pair;
import java.util.*;
import java.util.concurrent.Callable;

public class CoefficientsRunner implements Callable<ArrayList<Pair<Double, Double>>> {
    private RunningStrategy strategy;
    private Iterator<ArrayList<Double>> matrixIterator;
    private ArrayList<Pair<Double, Double>> result;
    private int resultSize;

    public CoefficientsRunner(RunningStrategy strategy, Iterator<ArrayList<Double>> matrixIterator, int resultSize) {
        this.strategy = strategy;
        this.matrixIterator = matrixIterator;
        this.resultSize = resultSize;
        this.result = new ArrayList<Pair<Double, Double>>(resultSize);
    }

    @Override
    public ArrayList<Pair<Double, Double>> call() throws Exception {
        result.add(strategy.getCoefficients(matrixIterator.next(), null));
        for (int i = 1; i < resultSize; i++) {
            result.add(strategy.getCoefficients(matrixIterator.next(), result.get(i - 1)));
        }
        return result;
    }
}
