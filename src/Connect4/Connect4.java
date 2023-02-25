package Connect4;

import sac.game.*;

import java.util.*;

public class Connect4 extends GameStateImpl {
    public static int m = 6;      //liczba wierszy    (min 4) była final
    public static int n = 7;      //liczba kolumn     (min 4) ta też   ten 1 niżej też jest final
    private static boolean startingPlayer = true;        //true - rozpoczyna gracz, false - rozpoczyna AI
    public static final char player = 'O', ai = 'X';       //symbole na planszy dla gracza i AI
    private boolean colorRed = false;
    //----------------------
    private char[][] board;
    private int playerMove = -1;    //nie zmieniać!

    public void main() {
        //Scanner scanner = new Scanner(System.in);
        //Connect4 game = new Connect4();
        Connect4.setHFunction(new HeuristicFunction());
        GameSearchAlgorithm algo = new AlphaBetaPruning();    //AlphaBetaPruning
        algo.setInitial(this);  //game
        GUI sketch = new GUI(this, n, m, startingPlayer, colorRed);
        GUI.runSketch(new String[] {"Connect4.GUI"}, sketch);

        while(!this.isWinner()) {
            if(this.isMaximizingTurnNow()) {    //jeżeli tura gracza
                while (true) {
                    System.out.println("player");
                    if (this.playerMove != -1) {
                        this.makeMove(this.playerMove);
                        this.setPlayerMove(-1);
                        break;
                    }
                }
                //System.out.println(game);
            }
            else {
                algo.execute();
                Map<String, Double> bestMoves = algo.getMovesScores();
                for (Map.Entry<String, Double> entry : bestMoves.entrySet())
                    System.out.println(entry.getKey() + " -> " + entry.getValue());
                String s = algo.getFirstBestMove();
                int aiMove = Integer.parseInt(s);
                this.makeMove(aiMove);
                sketch.setAIMove(aiMove);
                //System.out.println(game);
            }
        }
        System.out.print("Winner: ");
        if(this.isMaximizingTurnNow())
            System.out.println("AI");
        else
            System.out.println("player");
    }

    Connect4() {
        board = new char[m][n];
        for(int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                board[i][j] = '-';
        this.setMaximizingTurnNow(startingPlayer);        //gracz jest maksymalizujący, a AI minimalizujące
    }

    Connect4(int rows, int columns, boolean player, boolean color) {
        m = rows;
        n = columns;
        startingPlayer = player;
        colorRed = color;
        board = new char[m][n];
        for(int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                board[i][j] = '-';
        this.setMaximizingTurnNow(startingPlayer);
    }

    Connect4(Connect4 parent) {     //konstruktor kopiujący
        board = new char[m][n];
        for(int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                board[i][j] = parent.board[i][j];
        this.setMaximizingTurnNow(parent.isMaximizingTurnNow());
    }

    public void setPlayerMove(int m) {
        this.playerMove = m;
    }

    public char[][] getBoard() {
        return board;
    }

    public String toString() {
        StringBuilder txt = new StringBuilder();
        for(int i = 0; i <= 6 * n; i++)
            txt.append('-');
        txt.append("\n\n\n   ");
        for(int i = 0; i < n; i++)
            txt.append(i + "     ");
        txt.append("\n");
        for(int i = 0; i <= 6 * n; i++)
            txt.append('\u2014');
        txt.append("\n");

        for(int i = 0; i < m; i++) {
            txt.append("|");
            for (int j = 0; j < n; j++)
                txt.append("  " + board[i][j] + "  |");
            txt.append("\n");
            for (int j = 0; j <= 6 * n; j++)
                txt.append('\u2014');
            txt.append("\n");
        }
        return txt.toString();
    }

    public int hashCode() {
        char[] boardFlat = new char[m * n];
        int k = 0;
        for(int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                boardFlat[k++] = board[i][j];
        return Arrays.hashCode(boardFlat);
    }

    boolean makeMove(int column) {
        if(column < 0 || column >= n) {
            return false;
        }
        for(int i = m-1; i >= 0; i--) {
            if(board[i][column] == '-') {
                if(isMaximizingTurnNow())
                    board[i][column] = player;
                else
                    board[i][column] = ai;
                this.setMaximizingTurnNow(!isMaximizingTurnNow());
                return true;
            }
        }
        return false;
    }

    public boolean isWinner() {
        for(int i = 0; i < n; i++) {        //warunek sufitu
            if(board[0][i] != '-')
                return true;
        }

        for(int i = 1; i < m; i++) {        //wiersze
            for(int j = 0; j < n - 3; j++) {
                if(board[i][j] == player && board[i][j + 1] == player && board[i][j + 2] == player && board[i][j + 3] == player)
                    return true;
                if(board[i][j] == ai && board[i][j + 1] == ai && board[i][j + 2] == ai && board[i][j + 3] == ai)
                    return true;
            }
        }

        for(int i = 0; i < m - 3; i++) {    //kolumny
            for(int j = 0; j < n; j++) {
                if(board[i][j] == player && board[i + 1][j] == player && board[i + 2][j] == player && board[i + 3][j] == player)
                    return true;
                if(board[i][j] == ai && board[i + 1][j] == ai && board[i + 2][j] == ai && board[i + 3][j] == ai)
                    return true;
            }
        }

        for(int i = 0; i < m - 3; i++) {       //ukosy góra-dół
            for (int j = 0; j < n - 3; j++) {
                if(board[i][j] == player && board[i + 1][j + 1] == player && board[i + 2][j + 2] == player && board[i + 3][j + 3] == player)
                    return true;
                if(board[i][j] == ai && board[i + 1][j + 1] == ai && board[i + 2][j + 2] == ai && board[i + 3][j + 3] == ai)
                    return true;
            }
        }

        for(int i = 0; i < m - 3; i++) {        //ukosy dół-góra
            for(int j = 3; j < n; j++) {
                if(board[i][j] == player && board[i + 1][j - 1] == player && board[i + 2][j - 2] == player && board[i + 3][j - 3] == player)
                    return true;
                if(board[i][j] == ai && board[i + 1][j - 1] == ai && board[i + 2][j - 2] == ai && board[i + 3][j - 3] == ai)
                    return true;
            }
        }
        return false;
    }

    @Override
    public List<GameState> generateChildren() {
        List<GameState> children = new ArrayList();
        for(int i = 0; i < n; i++) {
            Connect4 child = new Connect4(this);
            if(child.makeMove(i)) {
                child.setMoveName(Integer.toString(i));
                children.add(child);
            }
        }
        return children;
    }

}






//    public static void main(String args[]) {
//        Scanner scanner = new Scanner(System.in);
//        Connect4 game = new Connect4();
//        Connect4.setHFunction(new HeuristicFunction());
//        GameSearchAlgorithm algo = new MinMax();    //AlphaBetaPruning
//        algo.setInitial(game);
//        System.out.println(game);
//
//        while(!game.isWinner()) {
//            if(game.isMaximizingTurnNow()) {    //jeżeli tura gracza
//                System.out.print("Enter a column number (0 - " + (n - 1) + "): ");
//                while (true) {
//                    int playerMove = scanner.nextInt();
//                    if (!game.makeMove(playerMove)) {
//                        System.out.print("Wrong number! Try again: ");
//                        continue;
//                    }
//                    break;
//                }
//                System.out.println(game);
//            }
//            else {
//                algo.execute();
//                Map<String, Double> bestMoves = algo.getMovesScores();
//                for (Map.Entry<String, Double> entry : bestMoves.entrySet())
//                    System.out.println(entry.getKey() + " -> " + entry.getValue());
//                String s = algo.getFirstBestMove();
//                int aiMove = Integer.parseInt(s);
//                game.makeMove(aiMove);
//                System.out.println(game);
//            }
//        }
//        System.out.print("Winner: ");
//        if(game.isMaximizingTurnNow())
//            System.out.println("AI");
//        else
//            System.out.println("player");
//    }