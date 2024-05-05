package Connect4;

import sac.State;
import sac.StateFunction;

public class HeuristicFunction extends StateFunction {
    public double calculate(State state) {
        Connect4 connect4 = (Connect4) state;

        if (connect4.isWinner() && !connect4.isMaximizingTurnNow())     //wariant z wygraną gracza
            return Double.POSITIVE_INFINITY;
        else if (connect4.isWinner() && connect4.isMaximizingTurnNow())     //wariant z wygraną AI
            return Double.NEGATIVE_INFINITY;
        else {
            char[][] board = connect4.getBoard();
            char playerSymbol = connect4.player;
            char aiSymbol = connect4.ai;
            double counterPlayer = 0;
            double counterAI = 0;

            for(int i = 0; i < connect4.m; i++) {        //wiersze
                for(int j = 0; j < connect4.n - 3; j++) {
                    if(board[i][j] != playerSymbol && board[i][j + 1] != playerSymbol && board[i][j + 2] != playerSymbol && board[i][j + 3] != playerSymbol)
                        counterAI++;
                    if(board[i][j] != aiSymbol && board[i][j + 1] != aiSymbol && board[i][j + 2] != aiSymbol && board[i][j + 3] != aiSymbol)
                        counterPlayer++;
                }
            }

            for(int i = 0; i < connect4.m - 3; i++) {    //kolumny
                for(int j = 0; j < connect4.n; j++) {
                    if(board[i][j] != playerSymbol && board[i + 1][j] != playerSymbol && board[i + 2][j] != playerSymbol && board[i + 3][j] != playerSymbol)
                        counterAI++;
                    if(board[i][j] != aiSymbol && board[i + 1][j] != aiSymbol && board[i + 2][j] != aiSymbol && board[i + 3][j] != aiSymbol)
                        counterPlayer++;
                }
            }

            for(int i = 0; i < connect4.m - 3; i++) {       //ukosy góra-dół
                for (int j = 0; j < connect4.n - 3; j++) {
                    if(board[i][j] != playerSymbol && board[i + 1][j + 1] != playerSymbol && board[i + 2][j + 2] != playerSymbol && board[i + 3][j + 3] != playerSymbol)
                        counterAI++;
                    if(board[i][j] != aiSymbol && board[i + 1][j + 1] != aiSymbol && board[i + 2][j + 2] != aiSymbol && board[i + 3][j + 3] != aiSymbol)
                        counterPlayer++;
                }
            }

            for(int i = 0; i < connect4.m - 3; i++) {        //ukosy dół-góra
                for(int j = 3; j < connect4.n; j++) {
                    if(board[i][j] != playerSymbol && board[i + 1][j - 1] != playerSymbol && board[i + 2][j - 2] != playerSymbol && board[i + 3][j - 3] != playerSymbol)
                        counterAI++;
                    if(board[i][j] != aiSymbol && board[i + 1][j - 1] != aiSymbol && board[i + 2][j - 2] != aiSymbol && board[i + 3][j - 3] != aiSymbol)
                        counterPlayer++;
                }
            }

            double counter = -(counterAI - counterPlayer);        //AI jest minimalizujące
            return counter;
        }
    }
}

