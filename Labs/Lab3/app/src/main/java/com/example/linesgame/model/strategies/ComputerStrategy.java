package com.example.linesgame.model.strategies;

import com.example.linesgame.model.Color;
import com.example.linesgame.model.utils.Position;

import java.util.List;

public interface ComputerStrategy {
    List<Color> generateNextColors();
    List<Position> choosePositions(List<Position> freePositions);
}
