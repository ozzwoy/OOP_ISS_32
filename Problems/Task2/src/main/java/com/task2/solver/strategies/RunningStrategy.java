package com.task2.solver.strategies;

import com.task2.utils.Pair;
import java.util.ArrayList;

public interface RunningStrategy {
    int ELEMENTS_IN_MATRIX_ROW = 4;

    Pair<Double, Double> getCoefficients(ArrayList<Double> matrixRow, Pair<Double, Double> previous);
}
