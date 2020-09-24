package com.task2.solver;

import com.task2.solver.strategies.BackwardRunningStrategy;
import com.task2.solver.strategies.ForwardRunningStrategy;
import com.task2.utils.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TridiagonalMatrixEquationsSystemSolver {
    public static ArrayList<Double> solve(List<List<Double>> matrix) {
        LinkedList<List<Double>> matrixCopy = new LinkedList<>(matrix);
        ArrayList<Pair<Double, Double>> firstHalfCoefficients = null, secondHalfCoefficients = null;
        ArrayList<Double> firstHalfRoots = null, secondHalfRoots = null;
        ExecutorService executor = Executors.newSingleThreadExecutor();
        int meetPoint = matrix.size() / 2;

        Future<ArrayList<Pair<Double, Double>>> backwardRunner = executor.submit(new CoefficientsRunner
                (new BackwardRunningStrategy(), matrixCopy, meetPoint));

        try {
            firstHalfCoefficients = (new CoefficientsRunner(new ForwardRunningStrategy(),
                    matrixCopy, matrix.size() - meetPoint)).call();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            secondHalfCoefficients = backwardRunner.get();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println(e.getMessage());
        }

        Double alpha = firstHalfCoefficients.get(firstHalfCoefficients.size() - 1).getFirst();
        Double beta = firstHalfCoefficients.get(firstHalfCoefficients.size() - 1).getSecond();
        Double xi = secondHalfCoefficients.get(secondHalfCoefficients.size() - 1).getFirst();
        Double eta = secondHalfCoefficients.get(secondHalfCoefficients.size() - 1).getSecond();

        Double xStartForward = (eta + xi * beta) / (1.0 - xi * alpha);
        Double xStartBackward = (beta + alpha * eta) / (1.0 - xi * alpha);

        Future<ArrayList<Double>> firstHalfRootsFuture = executor.submit(new RootsCalculator(firstHalfCoefficients,
                                                                                             xStartBackward));
        executor.shutdown();

        try {
            secondHalfRoots = (new RootsCalculator(secondHalfCoefficients, xStartForward)).call();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            firstHalfRoots = firstHalfRootsFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println(e.getMessage());
        }

        Collections.reverse(firstHalfRoots);
        firstHalfRoots.addAll(secondHalfRoots);
        return firstHalfRoots;
    }
}
