package player;

import tictactoe.GameBoard;

public class HypotheticalMove implements MoveInterface {
    private int x;
    private int y;

    public HypotheticalMove(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int getX() {
        return x;
    }

    public boolean hasWestNeighbour(GameBoard gameBoard) {
        return (x > 0 && y < gameBoard.getSize() && y < gameBoard.getSize() && gameBoard.fields[x-1][y].isSet() && !gameBoard.fields[x][y].isSet());
    }

    public boolean hasEastNeighbour(GameBoard gameBoard) {
        return x < gameBoard.getSize() && y < gameBoard.getSize() && gameBoard.fields[x+1][y].isSet()  && !gameBoard.fields[x][y].isSet();
    }

    public  boolean hasNorthNeighbour(GameBoard gameBoard) {
        return y > 0 && x < gameBoard.getSize() && y < gameBoard.getSize() && gameBoard.fields[x][y-1].isSet()  && !gameBoard.fields[x][y].isSet();
    }

    public boolean hasSouthNeighbour(GameBoard gameBoard) {
        return x < gameBoard.getSize() && y < gameBoard.getSize() && gameBoard.fields[x][y+1].isSet() && !gameBoard.fields[x][y].isSet();
    }

    public boolean hasSouthEastNeighbour(GameBoard gameBoard) {
        return x < gameBoard.getSize() && y < gameBoard.getSize() && gameBoard.fields[x+1][y+1].isSet() && !gameBoard.fields[x][y].isSet();
    }

    public boolean hasSouthWestNeighbour(GameBoard gameBoard) {
        return x > 0 && x < gameBoard.getSize() && y < gameBoard.getSize() && gameBoard.fields[x-1][y+1].isSet() && !gameBoard.fields[x][y].isSet();
    }

    public boolean hasNorthEastNeighbour(GameBoard gameBoard) {
        return y > 0 && x < gameBoard.getSize() && y < gameBoard.getSize() && gameBoard.fields[x+1][y-1].isSet() && !gameBoard.fields[x][y].isSet();
    }

    public boolean hasNorthWestNeighbour(GameBoard gameBoard) {
        return x > 0 && y > 0 && x < gameBoard.getSize() && y < gameBoard.getSize() && gameBoard.fields[x-1][y-1].isSet() && !gameBoard.fields[x][y].isSet();
    }

    @Override
    public int getY() {
        return y;
    }
}
