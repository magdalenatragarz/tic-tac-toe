public class Application {

    public static void main(String[] args) {
        GameBoard gameBoard = new GameBoard(10,3);
        System.out.println(gameBoard.toString());
        TicTacToc xo = new TicTacToc(gameBoard);
        xo.play();
    }
}
