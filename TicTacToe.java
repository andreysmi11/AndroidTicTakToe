package edu.gmu.cs477.fall2020.tictactoegame;

public class TicTacToe {
    public static final int SIDE = 3;
    private int turn;
    private int game[][];
    public TicTacToe() {
        game = new int[SIDE][SIDE];
        resetGame();
    }
    public int play(int row, int column) {
        int currentTurn = turn;
        if (row >= 0 && column >= 0 && row < SIDE && column < SIDE
        && game[row][column] == 0) {
            game[row][column] = turn;
            if (turn == 1) turn = 2; else turn = 1;
            return currentTurn;
        } else return 0;
    }
    public int whoWon() {
        int rows = checkRows();
        if (rows > 0) return rows;
        int columns = checkColumns();
        if (columns > 0) return columns;
        int diagonals = checkDiagonals();
        if (diagonals > 0) return diagonals;
        return 0;
    }
    protected int checkRows() {
        for (int row = 0; row < SIDE; row++)
            if (game[row][0] != 0 && game[row][0] == game[row][1] &&
                    game[row][0] == game[row][2])  return game[row][0];
            return 0;
    }
    protected int checkColumns() {
        for (int col = 0; col < SIDE; col++)
            if (game[0][col] != 0 && game[0][col] == game[1][col] &&
                    game[0][col] == game[2][col])  return game[0][col];
        return 0;
    }
    protected int checkDiagonals() {
        if (game[0][0] != 0 && game[0][0] == game [1][1] &&
        game[0][0] == game [2][2]) return game[0][0];
        if (game[0][2] != 0 && game [0][2] == game[1][1] &&
        game [0][2] == game[2][0]) return game[0][2];
        return 0;
    }
    public boolean canNotPlay() {
        boolean result = true;
        for (int i=0;i<SIDE;i++)
            for (int j=0;j<SIDE;j++)
                if (game[i][j]==0) result = false;
                return result;
    }
    public boolean isGameOver() {
        return canNotPlay() || (whoWon() > 0);
    }
    public void resetGame() {
        for (int i=0;i<SIDE;i++)
            for (int j=0;j<SIDE;j++) game[i][j] = 0;
            turn = 1;
    }
    public String result() {
        if (whoWon() > 0) return "Player " + whoWon() +" won!";
        else if (canNotPlay()) return "Tie Game";
        else return"Play!";
    }
}
