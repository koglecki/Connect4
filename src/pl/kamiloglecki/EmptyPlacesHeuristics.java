package pl.kamiloglecki;

import sac.State;
import sac.StateFunction;

public class EmptyPlacesHeuristics extends StateFunction {
    @Override
    public double calculate(State state) {
        Sudoku sudoku = (Sudoku) state;
        return sudoku.getZeros();
    }
}