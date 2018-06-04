package player;

import tictactoe.GameBoard;

public interface Player {

    Move move(GameBoard gameBoard);

    void setIsX(boolean isX);

}
