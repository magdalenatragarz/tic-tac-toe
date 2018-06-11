package player;

import tictactoe.GameBoard;

import java.util.Scanner;

public class ManualPlayer implements Player {

    boolean isX;


    @Override
    public Move move(GameBoard gameBoard) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Wspolrzedne ruchu:");
        int x = scanner.nextInt();
        int y = scanner.nextInt();
        return new Move(x, y, isX);
    }

    @Override
    public void setIsX(boolean isX) {
        this.isX = isX;
    }
}
