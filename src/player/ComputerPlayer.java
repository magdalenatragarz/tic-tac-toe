package player;

import tictactoe.GameBoard;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ComputerPlayer implements Player {
    private boolean isX;

    @Override
    public Move move(GameBoard gameBoard) {
        List<HypotheticalMove> hypotheticalMoves = findMoorNeighborhood(gameBoard);

        if (hypotheticalMoves.size() == 0) return new Move(gameBoard.getSize() / 2, gameBoard.getSize() / 2, isX);
        int max = 0;
        int functionvalue;
        HypotheticalMove hypotheticalMoveMax = null;
        GameBoard newGameBoard = new GameBoard(gameBoard);

        for (HypotheticalMove hypotheticalMove : hypotheticalMoves) {
            functionvalue = utilityFunction(newGameBoard, hypotheticalMove);
            System.out.println("utility of (" + hypotheticalMove.getX() + "," + hypotheticalMove.getY() + " " + functionvalue);
            if (functionvalue > max) {
                max = functionvalue;
                hypotheticalMoveMax = hypotheticalMove;
            }
        }

        return new Move(hypotheticalMoveMax.getX(), hypotheticalMoveMax.getY(), isX);
    }

    public int utilityFunction(GameBoard gameBoard, HypotheticalMove hypotheticalMove) {
        int value = 0;

        gameBoard.move(hypotheticalMove, isX);

        if (isX) {
            for (Move xMove : gameBoard.xMoves) {
                value += checkRepeats(xMove, gameBoard, 2);
                value += checkRepeats(xMove, gameBoard, 3);
                value += checkRepeats(xMove, gameBoard, 4);
                value += checkRepeats(xMove, gameBoard, 5);
            }
        } else {
            for (Move oMove : gameBoard.oMoves) {
                value += checkRepeats(oMove, gameBoard, 2);
                value += checkRepeats(oMove, gameBoard, 3);
                value += checkRepeats(oMove, gameBoard, 4);
                value += checkRepeats(oMove, gameBoard, 5);
            }
        }
        gameBoard.remove(hypotheticalMove);

        return value;
    }

    int checkRepeats(MoveInterface move, GameBoard gameBoard, int howMuchRepeats) {
        int payoff = 0;

        int counter = 0;

        while (gameBoard.hasWestNeighbour(move.getX() - counter, move.getY())) {
            counter++;
            payoff += counter * 100;
            if (move.getX() > 0 && move.getY() > 0 && move.getX() - 1 < gameBoard.getSize() && move.getY() - 1 < gameBoard.getSize() && (counter == 3 || counter == 4)) {
                if (!gameBoard.fields[move.getX() - counter - 1][move.getY()].isSet() && !gameBoard.fields[move.getX() + 1][move.getY()].isSet())
                    payoff += 500;
            }
            if (counter == 4) payoff += 10000;
        }

        counter = 0;

        while (gameBoard.hasEastNeighbour(move.getX() + counter, move.getY())) {
            counter++;
            payoff += counter * 100;
            if (move.getX() > 0 && move.getY() > 0 && move.getX() - 1 < gameBoard.getSize() && move.getY() - 1 < gameBoard.getSize() && (counter == 3 || counter == 4)) {
                if (!gameBoard.fields[move.getX() + counter + 1][move.getY()].isSet() && !gameBoard.fields[move.getX() - 1][move.getY()].isSet())
                    payoff += 500;
            }
            if (counter == 4) payoff += 10000;
        }

        counter = 0;

        while (gameBoard.hasSouthNeighbour(move.getX(), move.getY() + counter)) {
            counter++;
            payoff += counter * 100;
            if (move.getX() > 0 && move.getY() > 0 && move.getX() - 1 < gameBoard.getSize() && move.getY() - 1 < gameBoard.getSize() && (counter == 3 || counter == 4)) {
                if (!gameBoard.fields[move.getX()][move.getY() + counter + 1].isSet() && !gameBoard.fields[move.getX()][move.getY() + 1].isSet())
                    payoff += 500;
            }
            if (counter == 4) payoff += 10000;
        }

        counter = 0;

        while (gameBoard.hasNorthNeighbour(move.getX(), move.getY() - counter)) {
            counter++;
            payoff += counter * 100;
            if (move.getX() > 0 && move.getY() > 0 && move.getX() - 1 < gameBoard.getSize() && move.getY() - 1 < gameBoard.getSize() && (counter == 3 || counter == 4)) {
                if (!gameBoard.fields[move.getX()][move.getY() - counter - 1].isSet() && !gameBoard.fields[move.getX()][move.getY() - 1].isSet())
                    payoff += 500;
            }
            if (counter == 4) payoff += 10000;
        }

        counter = 0;

        while (gameBoard.hasSouthWestNeighbour(move.getX() - counter, move.getY() + counter)) {
            counter++;
            payoff += counter * 100;
            if (move.getX() > 0 && move.getY() > 0 && move.getX() - 1 < gameBoard.getSize() && move.getY() - 1 < gameBoard.getSize() && (counter == 3 || counter == 4)) {
                if (!gameBoard.fields[move.getX() - counter - 1][move.getY() + counter + 1].isSet() && !gameBoard.fields[move.getX() + 1][move.getY() - 1].isSet())
                    payoff += 500;
            }
            if (counter == 4) payoff += 10000;
        }

        counter = 0;

        while (gameBoard.hasNorthWestNeighbour(move.getX() - counter, move.getY() - counter)) {
            counter++;
            payoff += counter * 100;
            if (move.getX() > 0 && move.getY() > 0 && move.getX() - 1 < gameBoard.getSize() && move.getY() - 1 < gameBoard.getSize() && (counter == 3 || counter == 4)) {
                if (!gameBoard.fields[move.getX() - counter - 1][move.getY() - counter - 1].isSet() && !gameBoard.fields[move.getX() + 1][move.getY() + 1].isSet())
                    payoff += 500;
            }
            if (counter == 4) payoff += 10000;
        }

        counter = 0;
        while (gameBoard.hasNorthEastNeighbour(move.getX() + counter, move.getY() - counter)) {
            counter++;
            payoff += counter * 100;
            if (move.getX() > 0 && move.getY() > 0 && move.getX() - 1 < gameBoard.getSize() && move.getY() - 1 < gameBoard.getSize() && (counter == 3 || counter == 4)) {
                if (!gameBoard.fields[move.getX() + counter + 1][move.getY() - counter - 1].isSet() && !gameBoard.fields[move.getX() - 1][move.getY() + 1].isSet())
                    payoff += 500;
            }
            if (counter == 4) payoff += 10000;
        }

        counter = 0;
        while (gameBoard.hasSouthEastNeighbour(move.getX() + counter, move.getY() + counter)) {
            counter++;
            payoff += counter * 100;
            if (move.getX() > 0 && move.getY() > 0 && move.getX() - 1 < gameBoard.getSize() && move.getY() - 1 < gameBoard.getSize() && (counter == 3 || counter == 4)) {
                if (!gameBoard.fields[move.getX() + counter + 1][move.getY() + counter + 1].isSet() && !gameBoard.fields[move.getX() - 1][move.getY() - 1].isSet())
                    payoff += 500;
            }
            if (counter == 4) payoff += 10000;
        }

        return payoff;
    }


    List<HypotheticalMove> findMoorNeighborhood(GameBoard gameBoard) {
        List<HypotheticalMove> hypotheticalMoves = new ArrayList<>();
        for (Move move : gameBoard.oMoves) {
            if (!move.hasEastNeighbour(gameBoard))
                hypotheticalMoves.add(new HypotheticalMove(move.getX() + 1, move.getY()));
            if (!move.hasWestNeighbour(gameBoard))
                hypotheticalMoves.add(new HypotheticalMove(move.getX() - 1, move.getY()));
            if (!move.hasNorthNeighbour(gameBoard))
                hypotheticalMoves.add(new HypotheticalMove(move.getX(), move.getY() - 1));
            if (!move.hasSouthNeighbour(gameBoard))
                hypotheticalMoves.add(new HypotheticalMove(move.getX(), move.getY() + 1));
            if (!move.hasNorthEastNeighbour(gameBoard))
                hypotheticalMoves.add(new HypotheticalMove(move.getX() + 1, move.getY() - 1));
            if (!move.hasNorthWestNeighbour(gameBoard))
                hypotheticalMoves.add(new HypotheticalMove(move.getX() - 1, move.getY() - 1));
            if (!move.hasSouthEastNeighbour(gameBoard))
                hypotheticalMoves.add(new HypotheticalMove(move.getX() + 1, move.getY() + 1));
            if (!move.hasSouthWestNeighbour(gameBoard))
                hypotheticalMoves.add(new HypotheticalMove(move.getX() - 1, move.getY() + 1));
        }
        for (Move move : gameBoard.xMoves) {
            if (!move.hasEastNeighbour(gameBoard))
                hypotheticalMoves.add(new HypotheticalMove(move.getX() + 1, move.getY()));
            if (!move.hasWestNeighbour(gameBoard))
                hypotheticalMoves.add(new HypotheticalMove(move.getX() - 1, move.getY()));
            if (!move.hasNorthNeighbour(gameBoard))
                hypotheticalMoves.add(new HypotheticalMove(move.getX(), move.getY() - 1));
            if (!move.hasSouthNeighbour(gameBoard))
                hypotheticalMoves.add(new HypotheticalMove(move.getX(), move.getY() + 1));
            if (!move.hasNorthEastNeighbour(gameBoard))
                hypotheticalMoves.add(new HypotheticalMove(move.getX() + 1, move.getY() - 1));
            if (!move.hasNorthWestNeighbour(gameBoard))
                hypotheticalMoves.add(new HypotheticalMove(move.getX() - 1, move.getY() - 1));
            if (!move.hasSouthEastNeighbour(gameBoard))
                hypotheticalMoves.add(new HypotheticalMove(move.getX() + 1, move.getY() + 1));
            if (!move.hasSouthWestNeighbour(gameBoard))
                hypotheticalMoves.add(new HypotheticalMove(move.getX() - 1, move.getY() + 1));
        }

        return hypotheticalMoves.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public void setIsX(boolean isX) {
        this.isX = isX;
    }
}
