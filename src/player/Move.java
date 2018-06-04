package player;

import tictactoe.GameBoard;

public class Move implements MoveInterface {
    private int x;
    private int y;
    private boolean isX;

    public Move(int x, int y, boolean isX) {
        this.x = x;
        this.y = y;
        this.isX = isX;
    }

    public boolean isX() {
        return isX;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }



    public boolean hasWestNeighbour(GameBoard gameBoard) {
        return (x > 0 && y < gameBoard.getSize() && y < gameBoard.getSize() && gameBoard.fields[x-1][y].isSet());
    }

    public boolean hasEastNeighbour(GameBoard gameBoard) {
        return x < gameBoard.getSize() && y < gameBoard.getSize() && gameBoard.fields[x+1][y].isSet();
    }

    public  boolean hasNorthNeighbour(GameBoard gameBoard) {
        return y > 0 && x < gameBoard.getSize() && y < gameBoard.getSize() && gameBoard.fields[x][y-1].isSet();
    }

    public boolean hasSouthNeighbour(GameBoard gameBoard) {
        return x < gameBoard.getSize() && y < gameBoard.getSize() && gameBoard.fields[x][y+1].isSet();
    }

    public boolean hasSouthEastNeighbour(GameBoard gameBoard) {
        //System.out.println("south east:"+x+","+y+","+isX+"=?="+gameBoard.fields[x+1][y+1].isX()+", "+ gameBoard.fields[x][y].getLevel() + "eh" + gameBoard.fields[x+1][y+1].getLevel());
        return x < gameBoard.getSize() && y < gameBoard.getSize() && gameBoard.fields[x+1][y+1].isSet();
    }

    public boolean hasSouthWestNeighbour(GameBoard gameBoard) {
        return x > 0 && x < gameBoard.getSize() && y < gameBoard.getSize() && gameBoard.fields[x-1][y+1].isSet();
    }

    public boolean hasNorthEastNeighbour(GameBoard gameBoard) {
        return y > 0 && x < gameBoard.getSize() && y < gameBoard.getSize() && gameBoard.fields[x+1][y-1].isSet();
    }

    public boolean hasNorthWestNeighbour(GameBoard gameBoard) {
        return x > 0 && y > 0 && x < gameBoard.getSize() && y < gameBoard.getSize() && gameBoard.fields[x-1][y-1].isSet();
    }

    @Override
    public String toString() {
        return "Move{" +
                "x=" + x +
                ", y=" + y +
                ", isX=" + isX +
                '}';
    }
}
