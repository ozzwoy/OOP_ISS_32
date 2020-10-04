package com.task2.solver;

import com.task2.utils.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class RootsCalculator implements Callable<ArrayList<Double>> {
    private List<Pair<Double, Double>> coefficients;
    private ArrayList<Double> result;

    public RootsCalculator(List<Pair<Double, Double>> coefficients, Double firstRoot) {
        this.coefficients = coefficients;
        this.result = new ArrayList<>(coefficients.size());
        this.result.add(firstRoot);
    }

    @Override
    public ArrayList<Double> call() {
        Pair<Double, Double> temp;

        for (int i = 1; i < coefficients.size(); i++) {
            temp = coefficients.get(coefficients.size() - 1 - i);
            result.add(temp.getFirst() * result.get(i - 1) + temp.getSecond());
        }
        return result;
    }
}
