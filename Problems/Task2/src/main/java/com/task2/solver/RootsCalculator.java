package com.task2.solver;

import com.task2.utils.Pair;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class RootsCalculator implements Callable<ArrayList<Double>> {
    private ArrayList<Pair<Double, Double>> coefficients;
    private ArrayList<Double> result;

    public RootsCalculator(ArrayList<Pair<Double, Double>> coefficients, Double firstRoot) {
        this.coefficients = coefficients;
        this.result = new ArrayList<>(coefficients.size());
        result.set(0, firstRoot);
    }

    @Override
    public ArrayList<Double> call() throws Exception {
        Pair<Double, Double> temp;

        for (int i = 1; i < result.size(); i++) {
            temp = coefficients.get(coefficients.size() - 2);
            result.set(i, temp.getFirst() * result.get(i - 1) + temp.getSecond());
        }

        return result;
    }
}
