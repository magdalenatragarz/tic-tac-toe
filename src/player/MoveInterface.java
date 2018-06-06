package player;

import tictactoe.GameBoard;

public interface MoveInterface {

    int getX();

    int getY();


    boolean hasWestNeighbour(GameBoard gameBoard);

    boolean hasEastNeighbour(GameBoard gameBoard);

    boolean hasNorthNeighbour(GameBoard gameBoard);

    boolean hasSouthNeighbour(GameBoard gameBoard);

    boolean hasNorthWestNeighbour(GameBoard gameBoard);

    boolean hasSouthEastNeighbour(GameBoard gameBoard);

    boolean hasSouthWestNeighbour(GameBoard gameBoard);

    boolean hasNorthEastNeighbour(GameBoard gameBoard);

}
