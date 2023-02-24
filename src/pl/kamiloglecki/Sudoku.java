package pl.kamiloglecki;

import sac.graph.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sudoku extends GraphStateImpl {
    public static final int n = 3;
    public static final int n2 = n * n;
    private byte[][] board;
    private int zeros = n2 * n2;

    int getZeros() {
        return zeros;
    }

    public Sudoku() {
        board = new byte[n2][n2];
        for (int i = 0; i < n2; i++)
            for (int j = 0; j < n2; j++)
                board[i][j] = 0;
    }

    public Sudoku(Sudoku parent) {      //konstruktor kopiujący
        board = new byte[n2][n2];
        for (int i = 0; i < n2; i++)
            for (int j = 0; j < n2; j++)
                board[i][j] = parent.board[i][j];
        zeros = parent.zeros;
    }
    //003020600900305001001806400008102900700000008006708200002609500800203009005010300
    public static void main(String[] args) {
        String sudokuTxt = "003020600000005001001800400008002900700000008000708200002609500800203009005010300";   //sudoku tekst
        Sudoku s = new Sudoku();
        s.fromString(sudokuTxt);
        System.out.println(s);
        System.out.println(s.isLegal());
        System.out.println(s.zeros);
        //System.out.println(s.isSolution());

        Sudoku.setHFunction(new EmptyPlacesHeuristics());

        GraphSearchConfigurator conf = new GraphSearchConfigurator();
        conf.setWantedNumberOfSolutions(Integer.MAX_VALUE); //ile stanów ma sie liczyć

        GraphSearchAlgorithm algo = new BestFirstSearch(s, conf);
        algo.execute();
        List<GraphState> solutions = algo.getSolutions();
        for(GraphState sol:solutions) {
            System.out.println(sol);
            System.out.println("---");
        }

        System.out.println("Time [ms]: " + algo.getDurationTime());
        System.out.println("Closed: " + algo.getClosedStatesCount());
        System.out.println("Open: " + algo.getOpenSet().size());
        System.out.println("Solutions: " + solutions.size());
    }

    public void fromString(String txt) {
        int k = 0;
        for (int i = 0; i < n2; i++)
            for (int j = 0; j < n2; j++, k++)
                board[i][j] = Byte.valueOf(txt.substring(k, k + 1));
        refreshZeros();
    }

    public String toString() {
        StringBuilder txt = new StringBuilder();
        for (int i = 0; i < n2; i++) {
            for (int j = 0; j < n2; j++)
                txt.append(board[i][j] + ",");
            txt.append("\n");
        }
        return txt.toString();
    }

    public boolean isLegal() {
        byte[] group = new byte[n2];
        //wiersze
        for (int i = 0; i < n2; i++) {
            for (int j = 0; j < n2; j++)
                group[j] = board[i][j];
            if (!isGroupLegal(group))
                return false;
        }
        //kolumny
        for (int i = 0; i < n2; i++) {
            for (int j = 0; j < n2; j++)
                group[j] = board[j][i];
            if (!isGroupLegal(group))
                return false;
        }
        //kwadraty
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int q = 0;
                for (int k = 0; k < n; k++) {
                    for (int l = 0; l < n; l++) {
                        group[q++] = board[i * n + k][j * n + l];
                    }
                }
                if (!isGroupLegal(group))
                    return false;
            }
        }
        return true;
    }

    private boolean isGroupLegal(byte[] group) {
        boolean[] visited = new boolean[n2 + 1];
        for (int i = 1; i < n2 + 1; i++) {
            visited[i] = false;
        }
        for (int i = 0; i < n2; i++) {
            if (group[i] > 0) {
                if (visited[group[i]])
                    return false;
                visited[group[i]] = true;
            }
        }
        return true;
    }

    private void refreshZeros() {
        zeros = 0;
        for (int i = 0; i < n2; i++) {
            for (int j = 0; j < n2; j++)
                if (board[i][j] == 0)
                    zeros++;
        }
    }

    public boolean isSolution() {
        return ((zeros == 0) && (isLegal()));
    }

    @Override
    public List<GraphState> generateChildren() {
        List<GraphState> children = new ArrayList();
        int i=0, j=0;
        zeroFinder:
        for(; i<n2; i++) {
            for(j=0; j<n2; j++) {
                if(board[i][j] == 0)
                    break zeroFinder;
            }
        }
        if(i == n2)
            return children;
        for(int k=1; k<=n2; k++) {
            Sudoku child = new Sudoku(this);
            child.board[i][j] = (byte) k;
            if(child.isLegal()) {
                children.add(child);
                child.zeros--;
            }
        }
        return children;
    }

    @Override
    public int hashCode() {
        //return toString().hashCode();
        byte[] sudokuFlat = new byte[n2 * n2];
        int k=0;
        for(int i=0; i<n2;i++)
            for(int j=0; j<n2; j++)
                sudokuFlat[k++] = board[i][j];
        return Arrays.hashCode(sudokuFlat);
    }
}