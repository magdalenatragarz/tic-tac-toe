import player.ComputerPlayer;
import player.ManualPlayer;
import tictactoe.GameBoard;
import tictactoe.TicTacToe;

public class Application {

    public static void main(String[] args) {
        GameBoard gameBoard = new GameBoard(10,3);
        System.out.println(gameBoard.toString());
        TicTacToe xo = new TicTacToe(gameBoard,new ComputerPlayer(),new ManualPlayer());
        xo.play();
    }
}
