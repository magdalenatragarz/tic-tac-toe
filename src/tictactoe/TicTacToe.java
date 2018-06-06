package tictactoe;

import player.Move;
import player.Player;

public class TicTacToe {
    private GameBoard gameBoard;
    private Player player1;
    private Player player2;


    public TicTacToe(GameBoard gameBoard, Player player1, Player player2) {
        this.player1 = player1;
        player1.setIsX(true);
        this.player2 = player2;
        player2.setIsX(false);
        this.gameBoard = gameBoard;
    }

    private void move() {
        Move move;
        if ((gameBoard.level + 1) % 2 == 1) {
            move = player1.move(gameBoard);
            while (gameBoard.fields[move.getX()][move.getY()].isSet()) {
                System.out.println("Miejsce jest już zajęte, spróbu ponownie");
                move = player1.move(gameBoard);
            }
        } else {
            move = player2.move(gameBoard);
            while (gameBoard.fields[move.getX()][move.getY()].isSet()) {
                System.out.println("Miejsce jest już zajęte, spróbu ponownie");
                move = player2.move(gameBoard);
            }
        }
        gameBoard.level++;
        gameBoard.fields[move.getX()][move.getY()].set(gameBoard.level);
        if (move.isX()) {
            gameBoard.xMoves.add(move);
        } else {
            gameBoard.oMoves.add(move);
        }
    }


    public void play() {
        while (!gameBoard.checkIfWins()) {
            if (gameBoard.checkIfDraw()) {
                System.out.println("REMIS");
                return;
            }
            move();
            System.out.println(gameBoard.toString());
        }
        boolean isX = gameBoard.chechWhoWins();
        if (isX) {
            System.out.println("WYGRANA X");
        } else {
            System.out.println("WYGRANA O");
        }

    }
}
