import java.util.Scanner;

public class TicTacToc {
    private GameBoard gameBoard;

    public TicTacToc(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    private void move(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Wspolrzedne ruchu: \n");
        int x =scanner.nextInt();
        int y =scanner.nextInt();
        gameBoard.level ++;
        gameBoard.fields[y][x].set(gameBoard.level);
        gameBoard.fields[y][x].setLevel(gameBoard.level);
        if(gameBoard.fields[y][x].isX()){
            gameBoard.xMoves.add(new Move(y,x,true));
        }else{
            gameBoard.oMoves.add(new Move(y,x,false));
        }
    }

    public void play(){
        while (!gameBoard.checkIfWins()){
            move();
            System.out.println(gameBoard.xMoves.toString());
            System.out.println(gameBoard.oMoves.toString());
            System.out.println(gameBoard.toString());
        }

    }
}
