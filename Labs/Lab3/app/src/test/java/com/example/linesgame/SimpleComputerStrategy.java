package com.example.linesgame;

import com.example.linesgame.model.Color;
import com.example.linesgame.model.strategies.ComputerStrategy;
import com.example.linesgame.model.LinesGameModel;
import com.example.linesgame.model.utils.Position;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
* The class implements strategy in a way that it chooses only red color and puts it on a first free place.
* Positions are compared with native comparator.
* */
public class SimpleComputerStrategy implements ComputerStrategy {

    @Override
    public List<Color> generateNextColors() {
        List<Color> result = new ArrayList<>(LinesGameModel.NEXT_COLORS_NUM);
        for (int i = 0; i < LinesGameModel.NEXT_COLORS_NUM; i++) {
            result.add(Color.RED);
        }
        return result;
    }

    @Override
    public List<Position> choosePositions(List<Position> freePositions) {
        List<Position> copy = new ArrayList<Position>() {
            {
                addAll(freePositions);
            }
        };
        Collections.sort(copy);
        int posNum = Integer.min(freePositions.size(), LinesGameModel.NEXT_COLORS_NUM);
        List<Position> result = new ArrayList<>(posNum);
        for (int i = 0; i < posNum; i++) {
            result.add(copy.get(i));
        }
        return result;
    }
}
