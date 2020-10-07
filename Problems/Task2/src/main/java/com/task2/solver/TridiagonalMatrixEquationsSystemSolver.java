package com.task2.solver;

import com.task2.solver.exceptions.WrongMatrixSizeException;
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
    private static void checkMatrixDimensions(List<List<Double>> matrix) throws WrongMatrixSizeException {
        if (matrix.size() < 3) {
            throw new WrongMatrixSizeException("Wrong number of matrix rows, should be at least 3 rows.\n" +
                                               "Current number of rows: " + matrix.size() + ".");
        }

        for (int i = 0; i < matrix.size(); i++) {
            if (matrix.get(i).size() != 4) {
                throw new WrongMatrixSizeException("Wrong number of matrix columns, " +
                                                   "should be 4 elements in each row.\n" +
                                                   "Row #" + i + " has " + matrix.get(i).size() + " elements.");
            }
        }
    }

    private static Pair<ArrayList<Pair<Double, Double>>, ArrayList<Pair<Double, Double>>> calcCoefficients(
            LinkedList<List<Double>> matrix, ExecutorService executor, int meetPoint)
            throws ExecutionException, InterruptedException {
        ArrayList<Pair<Double, Double>> firstHalfCoefficients;
        ArrayList<Pair<Double, Double>> secondHalfCoefficients;

        Future<ArrayList<Pair<Double, Double>>> backwardRunnerResult = executor.submit(
                new RecurrentCoefficientsCalculator(new BackwardRunningStrategy(), matrix, meetPoint));
        firstHalfCoefficients = (new RecurrentCoefficientsCalculator(new ForwardRunningStrategy(), matrix,
                matrix.size() - meetPoint)).call();
        secondHalfCoefficients = backwardRunnerResult.get();

        return new Pair<>(firstHalfCoefficients, secondHalfCoefficients);
    }

    private static Pair<Double, Double> calcMeetPointValues(List<Pair<Double, Double>> firstHalfCoefficients,
                                                            List<Pair<Double, Double>> secondHalfCoefficients) {
        Double alpha = firstHalfCoefficients.get(firstHalfCoefficients.size() - 1).getFirst();
        Double beta = firstHalfCoefficients.get(firstHalfCoefficients.size() - 1).getSecond();
        Double xi = secondHalfCoefficients.get(secondHalfCoefficients.size() - 1).getFirst();
        Double eta = secondHalfCoefficients.get(secondHalfCoefficients.size() - 1).getSecond();

        Double xStartForward = (eta + xi * beta) / (1.0 - xi * alpha);
        Double xStartBackward = (beta + alpha * eta) / (1.0 - xi * alpha);

        return new Pair<>(xStartBackward, xStartForward);
    }

    private static ArrayList<Double> calcRoots(List<Pair<Double, Double>> firstHalfCoefficients,
                                               List<Pair<Double, Double>> secondHalfCoefficients, Double xStartBackward,
                                               Double xStartForward, ExecutorService executor)
                                              throws ExecutionException, InterruptedException {
        ArrayList<Double> firstHalfRoots;
        ArrayList<Double> secondHalfRoots;

        Future<ArrayList<Double>> firstHalfRootsFuture = executor.submit(new RootsCalculator(firstHalfCoefficients,
                                                                                             xStartBackward));
        secondHalfRoots = (new RootsCalculator(secondHalfCoefficients, xStartForward)).call();
        firstHalfRoots = firstHalfRootsFuture.get();

        Collections.reverse(firstHalfRoots);
        firstHalfRoots.addAll(secondHalfRoots);

        return firstHalfRoots;
    }

    public static ArrayList<Double> solve(List<List<Double>> coefficientsMatrix)
            throws WrongMatrixSizeException {
        checkMatrixDimensions(coefficientsMatrix);

        LinkedList<List<Double>> matrixCopy = new LinkedList<>(coefficientsMatrix);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        int meetPoint = coefficientsMatrix.size() / 2;
        ArrayList<Double> result = null;

        try {
            Pair<ArrayList<Pair<Double, Double>>, ArrayList<Pair<Double, Double>>> recurrentCoefficients =
                    calcCoefficients(matrixCopy, executor, meetPoint);
            List<Pair<Double, Double>> firstHalfCoefficients = recurrentCoefficients.getFirst();
            List<Pair<Double, Double>> secondHalfCoefficients = recurrentCoefficients.getSecond();

            Pair<Double, Double> meetPointValues = calcMeetPointValues(firstHalfCoefficients, secondHalfCoefficients);

            result = calcRoots(firstHalfCoefficients, secondHalfCoefficients, meetPointValues.getFirst(),
                               meetPointValues.getSecond(), executor);
        } catch(ExecutionException | InterruptedException e) {
            System.out.println("Some thread was interrupted or has thrown an exception.");
            e.printStackTrace();
        }

        executor.shutdown();
        return result;
    }
}
