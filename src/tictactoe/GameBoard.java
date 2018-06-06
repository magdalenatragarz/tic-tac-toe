package tictactoe;

import player.HypotheticalMove;
import player.Move;

import java.util.ArrayList;
import java.util.List;

public class GameBoard {

    private final int size;
    private final int toWin;
    public int level;
    public BoardField[][] fields;
    public List<Move> xMoves;
    public List<Move> oMoves;

    public GameBoard(int size, int toWin) {
        this.size = size;
        this.toWin = toWin;
        this.level = 0;

        xMoves = new ArrayList<>();
        oMoves = new ArrayList<>();
        fields = new BoardField[size][size];

        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                fields[x][y] = new BoardField(0);
            }
        }
    }

    public GameBoard(GameBoard gameBoard) {
        this.size = gameBoard.size;
        this.toWin = gameBoard.toWin;
        this.level = gameBoard.level;
        this.fields = new BoardField[size][size];
        for (int x = 0; x < size; x++) {
            System.arraycopy(gameBoard.fields[x], 0, fields[x], 0, size);
        }
        this.xMoves = new ArrayList<>(gameBoard.xMoves);
        this.oMoves = new ArrayList<>(gameBoard.oMoves);
    }

    public void move(HypotheticalMove hypotheticalMove, boolean isX) {
        if (isX) {
            fields[hypotheticalMove.getX()][hypotheticalMove.getY()].set(1);
        } else {
            fields[hypotheticalMove.getX()][hypotheticalMove.getY()].set(2);
        }
    }

    public void remove(HypotheticalMove hypotheticalMove) {
        fields[hypotheticalMove.getX()][hypotheticalMove.getY()].unset();
    }


    public int getSize() {
        return size;
    }

    public boolean checkNeighbourhood(int x, int y) {

        int counter = 0;

        while (hasWestNeighbour(x - counter, y)) {
            counter++;
            if (counter + 1 == toWin) return true;
        }

        counter = 0;

        while (hasEastNeighbour(x + counter, y)) {
            counter++;
            if (counter + 1 == toWin) return true;
        }

        counter = 0;

        while (hasNorthNeighbour(x, y - counter)) {
            counter++;
            if (counter + 1 == toWin) return true;
        }

        counter = 0;

        while (hasSouthNeighbour(x, y + counter)) {
            counter++;
            if (counter + 1 == toWin) return true;
        }

        counter = 0;

        while (hasSouthEastNeighbour(x + counter, y + counter)) {
            counter++;
            if (counter + 1 == toWin) return true;
        }

        counter = 0;

        while (hasSouthWestNeighbour(x - counter, y + counter)) {
            counter++;
            if (counter + 1 == toWin) return true;
        }

        counter = 0;

        while (hasNorthWestNeighbour(x - counter, y - counter)) {
            counter++;
            if (counter + 1 == toWin) return true;
        }

        counter = 0;

        while (hasNorthEastNeighbour(x + counter, y - counter)) {
            counter++;
            if (counter + 1 == toWin) return true;
        }

        return false;

    }


    public boolean hasWestNeighbour(int x, int y) {
        return (x > 0 && y < size && y < size && fields[x][y].isSet() && fields[x - 1][y].isSet() && (fields[x][y].isX() == fields[x - 1][y].isX()));
    }

    public boolean hasEastNeighbour(int x, int y) {
        return x +1< size && y < size && fields[x + 1][y].isSet() && fields[x][y].isSet() && (fields[x + 1][y].isX() == fields[x][y].isX());
    }

    public boolean hasNorthNeighbour(int x, int y) {
        return y > 0 && x < size && y < size && fields[x][y - 1].isSet() && fields[x][y].isSet() && (fields[x][y - 1].isX() == fields[x][y].isX());
    }

    public boolean hasSouthNeighbour(int x, int y) {
        return x < size && y +1 < size && fields[x][y + 1].isSet() && fields[x][y].isSet() && (fields[x][y + 1].isX() == fields[x][y].isX());
    }

    public boolean hasSouthEastNeighbour(int x, int y) {
        return x + 1 < size && y +1 < size && fields[x + 1][y + 1].isSet() && fields[x][y].isSet() && (fields[x + 1][y + 1].isX() == fields[x][y].isX());
    }

    public boolean hasSouthWestNeighbour(int x, int y) {
        return x > 0 && x < size && y +1 < size && fields[x - 1][y + 1].isSet() && fields[x][y].isSet() && (fields[x - 1][y + 1].isX() == fields[x][y].isX());
    }

    public boolean hasNorthEastNeighbour(int x, int y) {
        return y > 0 && x +1< size && y < size && fields[x + 1][y - 1].isSet() && fields[x][y].isSet() && (fields[x + 1][y - 1].isX() == fields[x][y].isX());
    }

    public boolean hasNorthWestNeighbour(int x, int y) {
        return x > 0 && y > 0 && x < size && y < size && fields[x - 1][y - 1].isSet() && fields[x][y].isSet() && (fields[x - 1][y - 1].isX() == fields[x][y].isX());
    }


    public boolean checkIfWins() {
        for (Move move : xMoves) {
            if (checkNeighbourhood(move.getX(), move.getY())) return true;
        }
        for (Move move : oMoves) {
            if (checkNeighbourhood(move.getX(), move.getY())) return true;
        }
        return false;
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("    ");
        for (int i = 0; i < size; i++) {
            builder.append("  ").append(i).append(" ");
        }
        builder.append("\n");
        for (int y = 0; y < size; y++) {
            builder.append("  ").append(y).append(" ");
            for (int x = 0; x < size; x++) {
                if (fields[x][y].isSet()) {
                    if (fields[x][y].isX()) {
                        builder.append("| X ");
                    } else {
                        builder.append("| O ");
                    }
                } else {
                    builder.append("| _ ");
                }
            }
            builder.append("\n");
        }
        return builder.toString();
    }

}
