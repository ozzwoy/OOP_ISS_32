package com.example.linesgame;

import com.example.linesgame.model.Color;
import com.example.linesgame.model.LinesGameModel;
import com.example.linesgame.model.exceptions.InvalidFieldSizeException;
import com.example.linesgame.model.strategies.RandomComputerStrategy;
import com.example.linesgame.model.utils.Position;

import org.junit.Assert;
import org.junit.Test;

public class LinesGameModelTest {

    @Test
    public void testConstructorsOnInvalidSize() {
        int size = 4;
        Assert.assertThrows("Field should be at least of size 5. Provided: " + size + ".",
                InvalidFieldSizeException.class, () -> new LinesGameModel(size));
        Assert.assertThrows("Field should be at least of size 5. Provided: " + size + ".",
                InvalidFieldSizeException.class,
                () -> new LinesGameModel(size, null));
    }

    @Test
    public void testInitialConfiguration() throws InvalidFieldSizeException {
        int size = 9;
        LinesGameModel model = new LinesGameModel(size, new SimpleComputerStrategy());
        for (int i = 0; i < 2; i++) {
            Assert.assertEquals(size, model.getSize());
            Assert.assertFalse(model.isEmptyAt(new Position(0, 0)));
            Assert.assertEquals(Color.RED, model.getColorAt(new Position(0, 0)));
            Assert.assertFalse(model.isGameOver());
            Assert.assertEquals(model.getSize() * model.getSize() -
                    LinesGameModel.NEXT_COLORS_NUM, model.getFreePositions().size());
            Assert.assertEquals(0, model.getPossibleMoves(new Position(4, 4)).size());
            Assert.assertEquals(model.getSize() * model.getSize() -
                            LinesGameModel.NEXT_COLORS_NUM,
                    model.getPossibleMoves(new Position(0, 0)).size());
            Assert.assertFalse(model.makeMove(new Position(0, 0), new Position(0, 1)));
            Assert.assertTrue(model.getNextColors().get(0) == Color.RED &&
                    model.getNextColors().get(1) == Color.RED &&
                    model.getNextColors().get(2) == Color.RED);
            model.restartGame();
        }
    }

    @Test
    public void testHorizontalChain() throws InvalidFieldSizeException {
        int size = 9;
        LinesGameModel model = new LinesGameModel(size, new SimpleComputerStrategy());
        Assert.assertTrue(model.makeMove(new Position(0, 0), new Position(1, 0)));
        Assert.assertEquals(model.getSize() * model.getSize() - 1,
                            model.getFreePositions().size());
    }

    @Test
    public void testVerticalChain() throws InvalidFieldSizeException {
        int size = 9;
        LinesGameModel model = new LinesGameModel(size, new SimpleComputerStrategy());
        model.makeMove(new Position(0, 0), new Position(1, 0));
        model.makeMove(new Position(1, 0), new Position(2, 0));
        model.makeMove(new Position(0, 0), new Position(1, 0));
        model.makeMove(new Position(1, 0), new Position(3, 0));
        model.makeMove(new Position(0, 0), new Position(1, 0));
        model.makeMove(new Position(1, 0), new Position(4, 0));
        model.makeMove(new Position(0, 1), new Position(1, 0));
        Assert.assertEquals(model.getSize() * model.getSize() - 1,
                            model.getFreePositions().size());
    }

    @Test
    public void testDiagonalChain() throws InvalidFieldSizeException {
        int size = 9;
        LinesGameModel model = new LinesGameModel(size, new SimpleComputerStrategy());
        model.makeMove(new Position(0, 0), new Position(1, 1));
        model.makeMove(new Position(1, 1), new Position(2, 2));
        model.makeMove(new Position(0, 0), new Position(1, 1));
        model.makeMove(new Position(1, 1), new Position(3, 3));
        model.makeMove(new Position(0, 0), new Position(1, 1));
        model.makeMove(new Position(1, 1), new Position(4, 4));
        model.makeMove(new Position(0, 1), new Position(1, 1));
        Assert.assertEquals(model.getSize() * model.getSize() - 1,
                model.getFreePositions().size());
    }

    @Test
    public void testAntidiagonalChain() throws InvalidFieldSizeException {
        int size = 9;
        LinesGameModel model = new LinesGameModel(size, new SimpleComputerStrategy());
        model.makeMove(new Position(0, 0), new Position(size - 1, size - 1));
        model.makeMove(new Position(size - 1, size - 1), new Position(size - 2, size - 2));
        model.makeMove(new Position(0, 0), new Position(size - 1, size - 1));
        model.makeMove(new Position(size - 1, size - 1), new Position(size - 3, size - 3));
        model.makeMove(new Position(0, 0), new Position(size - 1, size - 1));
        model.makeMove(new Position(size - 1, size - 1), new Position(size - 4, size - 4));
        model.makeMove(new Position(0, 0), new Position(size - 1, size - 1));
        model.makeMove(new Position(size - 1, size - 1), new Position(size - 5, size - 5));
        model.makeMove(new Position(0, 0), new Position(size - 6, size - 6));
        Assert.assertEquals(model.getSize() * model.getSize() - 2,
                model.getFreePositions().size());
    }

    @Test
    public void testCrossChainAndPathBlocking() throws InvalidFieldSizeException {
        int size = 9;
        LinesGameModel model = new LinesGameModel(size, new SimpleComputerStrategy());
        //vertical chain
        model.makeMove(new Position(0, 0), new Position(1, 0));
        model.makeMove(new Position(1, 0), new Position(2, 0));
        model.makeMove(new Position(0, 0), new Position(1, 0));
        model.makeMove(new Position(1, 0), new Position(3, 0));
        model.makeMove(new Position(0, 0), new Position(1, 0));
        model.makeMove(new Position(1, 0), new Position(4, 0));
        model.makeMove(new Position(0, 0), new Position(1, 0));
        model.makeMove(new Position(1, 0), new Position(5, 0));
        //horizontal chain
        model.makeMove(new Position(0, 0), new Position(5, 0));
        model.makeMove(new Position(5, 0), new Position(1, 1));
        model.makeMove(new Position(0, 0), new Position(1, 2));
        model.makeMove(new Position(1, 2), new Position(1, 3));
        model.makeMove(new Position(0, 0), new Position(1, 2));
        model.makeMove(new Position(1, 2), new Position(1, 4));
        model.makeMove(new Position(0, 0), new Position(3, 3));
        model.makeMove(new Position(3, 3), new Position(1, 2));
        //no straight path from (0,1) to (1,0)
        Assert.assertFalse(model.makeMove(new Position(0, 1), new Position(1, 0)));
        model.makeMove(new Position(0, 0), new Position(1, 0));
        Assert.assertEquals(model.getSize() * model.getSize() - 2,
                model.getFreePositions().size());
        //check score
        Assert.assertEquals(50, model.getScore());
    }

    @Test
    public void testRandomComputerStrategy() throws InvalidFieldSizeException {
        int size = 9;
        LinesGameModel model = new LinesGameModel(size, new RandomComputerStrategy());
        Assert.assertEquals(model.getSize() * model.getSize() -
                LinesGameModel.NEXT_COLORS_NUM, model.getFreePositions().size());
    }
}

