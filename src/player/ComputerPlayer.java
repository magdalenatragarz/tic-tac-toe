package player;

import tictactoe.GameBoard;

import java.util.*;
import java.util.stream.Collectors;

public class ComputerPlayer implements Player {
    private boolean isX;

    @Override
    public Move move(GameBoard gameBoard) {
        List<HypotheticalMove> hypotheticalMoves = findMoorNeighborhood(gameBoard);

        if (hypotheticalMoves.size() == 0) return new Move(gameBoard.getSize() / 2, gameBoard.getSize() / 2, isX);
        int max = 0;
        int functionvalue;
        HypotheticalMove hypotheticalMoveMax = hypotheticalMoves.get(0);
        GameBoard newGameBoard = new GameBoard(gameBoard);
        if (gameBoard.level > 0)
            for (HypotheticalMove hypotheticalMove : hypotheticalMoves) {
                functionvalue = heuristics(newGameBoard, hypotheticalMove);
                if (functionvalue > max) {
                    max = functionvalue;
                    hypotheticalMoveMax = hypotheticalMove;
                }
            }
        else {
            hypotheticalMoveMax = minimax(gameBoard);
        }

        return new Move(hypotheticalMoveMax.getX(), hypotheticalMoveMax.getY(), isX);
    }

    public int utilityFunction(GameBoard gameBoard) {
        return heuristics(gameBoard, true) - heuristics(gameBoard, false);
    }


    public int heuristics(GameBoard gameBoard, boolean isX) {
        int value = 0;

        if (isX) {
            for (Move xMove : gameBoard.xMoves) {
                value += checkRepeats(xMove, gameBoard);
                for (Move oMove : gameBoard.oMoves) {
                    value += checkRepeats(oMove, gameBoard);
                }
            }
        } else {
            for (Move oMove : gameBoard.oMoves) {
                value += checkRepeats(oMove, gameBoard);
                for (Move xMove : gameBoard.oMoves) {
                    value += checkRepeats(xMove, gameBoard);
                }
            }
        }
        return value;
    }


    public int heuristics(GameBoard gameBoard, HypotheticalMove hypotheticalMove) {
        int value = 0;

        gameBoard.move(hypotheticalMove, isX);

        if (isX) {
            for (Move xMove : gameBoard.xMoves) {
                value += checkRepeats(xMove, gameBoard);
            }
        } else {
            for (Move oMove : gameBoard.oMoves) {
                value += checkRepeats(oMove, gameBoard);
            }
        }
        gameBoard.remove(hypotheticalMove);
        gameBoard.move(hypotheticalMove, !isX);

        if (isX) {
            for (Move xMove : gameBoard.xMoves) {
                value += checkRepeats(xMove, gameBoard);
            }
        } else {
            for (Move oMove : gameBoard.oMoves) {
                value += checkRepeats(oMove, gameBoard);
            }
        }
        gameBoard.remove(hypotheticalMove);

        return value;
    }

    private HypotheticalMove minimax(GameBoard gameBoard) {
        List<HypotheticalMove> hypotheticalMoves1 = findMoorNeighborhood(gameBoard);
        int utilityValue;
        Map<HypotheticalMove, Integer> movesAndUtilityValueSet = new HashMap<>();

        List<Integer> x1 = new ArrayList<>();

        for (HypotheticalMove hypotheticalMove1 : hypotheticalMoves1) {
            gameBoard.move(hypotheticalMove1, true);
            List<HypotheticalMove> hypotheticalMoves2 = findMoorNeighborhood(gameBoard);
            for (HypotheticalMove hypotheticalMove2 : hypotheticalMoves2) {
                gameBoard.move(hypotheticalMove2, false);
                x1.add(utilityFunction(gameBoard));
                gameBoard.remove(hypotheticalMove2);
            }
            utilityValue = utilityFunction(gameBoard) - Collections.min(x1);
            movesAndUtilityValueSet.put(hypotheticalMove1, utilityValue);
            gameBoard.remove(hypotheticalMove1);
        }

        return max(movesAndUtilityValueSet).getKey();
    }

    Map.Entry<HypotheticalMove, Integer> max(Map<HypotheticalMove, Integer> map) {
        Map.Entry<HypotheticalMove, Integer> maxEntry = null;

        for (Map.Entry<HypotheticalMove, Integer> entry : map.entrySet()) {
            if (maxEntry == null || entry.getValue() > maxEntry.getValue()) {
                maxEntry = entry;
            }
        }
        return maxEntry;
    }

    int checkRepeats(MoveInterface move, GameBoard gameBoard) {
        int payoff = 0;

        int counter = 0;

        while (gameBoard.hasWestNeighbour(move.getX() - counter, move.getY())) {
            counter++;
            payoff += counter;
            if (move.getX() - counter - 1 > 0 && move.getY() >= 0 && move.getX() + 1 < gameBoard.getSize() && move.getY() - 1 < gameBoard.getSize() && (counter == 3 || counter == 4)) {
                if (!gameBoard.fields[move.getX() - counter - 1][move.getY()].isSet() && !gameBoard.fields[move.getX() + 1][move.getY()].isSet()) {
                    payoff += 7;
                } else {
                    payoff += 2;
                }
            }
            if (counter == 4) payoff += 20;
        }

        counter = 0;

        while (gameBoard.hasEastNeighbour(move.getX() + counter, move.getY())) {
            counter++;
            payoff += counter;
            if (move.getX() - 1 > 0 && move.getY() > 0 && move.getX() + counter + 1 < gameBoard.getSize() && (counter == 3 || counter == 4)) {
                if (!gameBoard.fields[move.getX() + counter + 1][move.getY()].isSet() && !gameBoard.fields[move.getX() - 1][move.getY()].isSet()) {
                    payoff += 7;
                } else {
                    payoff += 2;
                }
            }

            if (counter == 4) payoff += 20;
        }
        counter = 0;

        while (gameBoard.hasSouthNeighbour(move.getX(), move.getY() + counter)) {
            counter++;
            payoff += counter;
            if (move.getX() >= 0 && move.getY() >= 0 && move.getX() < gameBoard.getSize() && move.getY() + counter + 1 < gameBoard.getSize() && (counter == 3 || counter == 4)) {
                if (!gameBoard.fields[move.getX()][move.getY() + counter + 1].isSet() && !gameBoard.fields[move.getX()][move.getY() - 1].isSet()) {
                    payoff += 7;
                } else {
                    payoff += 2;
                }
            }

            if (counter == 4) payoff += 20;
        }
        counter = 0;

        while (gameBoard.hasNorthNeighbour(move.getX(), move.getY() - counter)) {
            counter++;
            payoff += counter;
            if (move.getX() >= 0 && move.getY() - counter - 1 >= 0 && move.getX() < gameBoard.getSize() && move.getY() + 1 < gameBoard.getSize() && (counter == 3 || counter == 4)) {
                if (!gameBoard.fields[move.getX()][move.getY() - counter - 1].isSet() && !gameBoard.fields[move.getX()][move.getY() + 1].isSet()) {
                    payoff += 7;
                } else {
                    payoff += 2;
                }
            }

            if (counter == 4) payoff += 20;
        }
        counter = 0;

        while (gameBoard.hasSouthWestNeighbour(move.getX() - counter, move.getY() + counter)) {
            counter++;
            payoff += counter;
            if (move.getX() - counter - 1 > 0 && move.getY() >= 0 && move.getX() + 1 < gameBoard.getSize() && move.getY() < gameBoard.getSize() && (counter == 3 || counter == 4)) {
                if (!gameBoard.fields[move.getX() - counter - 1][move.getY() + counter + 1].isSet() && !gameBoard.fields[move.getX() + 1][move.getY() - 1].isSet()) {
                    payoff += 7;
                } else {
                    payoff += 2;
                }
            }

            if (counter == 4) payoff += 20;
        }
        counter = 0;

        while (gameBoard.hasNorthWestNeighbour(move.getX() - counter, move.getY() - counter)) {
            counter++;
            payoff += counter;
            if (move.getX() - counter - 1 > 0 && move.getY() - counter - 1 > 0 && move.getX() + 1 < gameBoard.getSize() && move.getY() + 1 < gameBoard.getSize() && (counter == 3 || counter == 4)) {
                if (!gameBoard.fields[move.getX() - counter - 1][move.getY() - counter - 1].isSet() && !gameBoard.fields[move.getX() + 1][move.getY() + 1].isSet()) {
                    payoff += 7;
                } else {
                    payoff += 2;
                }
            }

            if (counter == 4) payoff += 20;
        }

        counter = 0;
        while (gameBoard.hasNorthEastNeighbour(move.getX() + counter, move.getY() - counter)) {
            counter++;
            payoff += counter;
            if (move.getX() - 1 >= 0 && move.getY() - counter - 1 >= 0 && move.getX() + counter + 1 < gameBoard.getSize() && move.getY() + 1 < gameBoard.getSize() && (counter == 3 || counter == 4)) {
                if (!gameBoard.fields[move.getX() + counter + 1][move.getY() - counter - 1].isSet() && !gameBoard.fields[move.getX() - 1][move.getY() + 1].isSet()) {
                    payoff += 7;
                } else {
                    payoff += 2;
                }
            }
            if (counter == 4) payoff += 20;
        }
        counter = 0;
        while (gameBoard.hasSouthEastNeighbour(move.getX() + counter, move.getY() + counter)) {
            counter++;
            payoff += counter;
            if (move.getX() > 0 && move.getY() > 0 && move.getX() + counter + 1 < gameBoard.getSize() && move.getY() + counter + 1 < gameBoard.getSize() && (counter == 3 || counter == 4)) {
                if (!gameBoard.fields[move.getX() + counter + 1][move.getY() + counter + 1].isSet() && !gameBoard.fields[move.getX() - 1][move.getY() - 1].isSet()) {
                    payoff += 7;
                } else {
                    payoff += 2;
                }
            }
            if (counter == 4) payoff += 20;
        }

        if (isX) {
            for (Move oMove : gameBoard.oMoves) {
                counter = 0;
                while (gameBoard.hasEastNeighbour(oMove.getX() + counter, oMove.getY())) {
                    counter++;
                }
                if (counter >= 3) payoff += (Math.pow(counter, 2));
                counter = 0;
                while (gameBoard.hasWestNeighbour(oMove.getX() - counter, oMove.getY())) {
                    counter++;
                }
                if (counter >= 3) payoff += (Math.pow(counter, 2));
                counter = 0;
                while (gameBoard.hasNorthNeighbour(oMove.getX(), oMove.getY() - counter)) {
                    counter++;
                }
                if (counter >= 3) payoff += (Math.pow(counter, 2));
                counter = 0;
                while (gameBoard.hasSouthNeighbour(oMove.getX(), oMove.getY() + counter)) {
                    counter++;
                }
                if (counter >= 3) payoff += (Math.pow(counter, 2));
                counter = 0;
                while (gameBoard.hasNorthEastNeighbour(oMove.getX() + counter, oMove.getY() - counter)) {
                    counter++;
                }
                if (counter >= 3) payoff += (Math.pow(counter, 2));
                counter = 0;
                while (gameBoard.hasSouthEastNeighbour(oMove.getX() + counter, oMove.getY() + counter)) {
                    counter++;
                }
                if (counter >= 3) payoff += (Math.pow(counter, 2));
                counter = 0;
                while (gameBoard.hasNorthWestNeighbour(oMove.getX() - counter, oMove.getY() - counter)) {
                    counter++;
                }
                if (counter >= 3) payoff += (Math.pow(counter, 2));
                counter = 0;
                while (gameBoard.hasSouthWestNeighbour(oMove.getX() - counter, oMove.getY() + counter)) {
                    counter++;
                }
                if (counter >= 3) payoff += (Math.pow(counter, 2));
            }
        } else {
            for (Move xMove : gameBoard.xMoves) {
                counter = 0;
                while (gameBoard.hasEastNeighbour(xMove.getX() + counter, xMove.getY())) {
                    counter++;
                }
                if (counter >= 3) payoff += (Math.pow(counter, 2));
                counter = 0;
                while (gameBoard.hasWestNeighbour(xMove.getX() - counter, xMove.getY())) {
                    counter++;
                }
                if (counter >= 3) payoff += (Math.pow(counter, 2));
                counter = 0;
                while (gameBoard.hasNorthNeighbour(xMove.getX(), xMove.getY() - counter)) {
                    counter++;
                }
                if (counter >= 3) payoff += (Math.pow(counter, 2));
                counter = 0;
                while (gameBoard.hasSouthNeighbour(xMove.getX(), xMove.getY() + counter)) {
                    counter++;
                }
                if (counter >= 3) payoff += (Math.pow(counter, 2));
                counter = 0;
                while (gameBoard.hasNorthEastNeighbour(xMove.getX() + counter, xMove.getY() - counter)) {
                    counter++;
                }
                if (counter >= 3) payoff += (Math.pow(counter, 2));
                counter = 0;
                while (gameBoard.hasSouthEastNeighbour(xMove.getX() + counter, xMove.getY() + counter)) {
                    counter++;
                }
                if (counter >= 3) payoff += (Math.pow(counter, 2));
                counter = 0;
                while (gameBoard.hasNorthWestNeighbour(xMove.getX() - counter, xMove.getY() - counter)) {
                    counter++;
                }
                if (counter >= 3) payoff += (Math.pow(counter, 2));
                counter = 0;
                while (gameBoard.hasSouthWestNeighbour(xMove.getX() - counter, xMove.getY() + counter)) {
                    counter++;
                }
                if (counter >= 3) payoff += (Math.pow(counter, 2));
            }
        }
        return payoff;
    }


    List<HypotheticalMove> findMoorNeighborhood(GameBoard gameBoard) {
        List<HypotheticalMove> hypotheticalMoves = new ArrayList<>();
        for (Move move : gameBoard.oMoves) {
            if (!move.hasEastNeighbour(gameBoard) && move.getX() + 1 < gameBoard.getSize() && move.getY() >= 0)
                hypotheticalMoves.add(new HypotheticalMove(move.getX() + 1, move.getY()));
            if (!move.hasWestNeighbour(gameBoard) && move.getX() > 0 && move.getY() >= 0)
                hypotheticalMoves.add(new HypotheticalMove(move.getX() - 1, move.getY()));
            if (!move.hasNorthNeighbour(gameBoard) && move.getY() > 0 && move.getX() >= 0)
                hypotheticalMoves.add(new HypotheticalMove(move.getX(), move.getY() - 1));
            if (!move.hasSouthNeighbour(gameBoard) && move.getY() + 1 < gameBoard.getSize() && move.getY() >= 0)
                hypotheticalMoves.add(new HypotheticalMove(move.getX(), move.getY() + 1));
            if (!move.hasNorthEastNeighbour(gameBoard) && move.getX() + 1 < gameBoard.getSize() && move.getY() > 0)
                hypotheticalMoves.add(new HypotheticalMove(move.getX() + 1, move.getY() - 1));
            if (!move.hasNorthWestNeighbour(gameBoard) && move.getY() > 0 && move.getX() > 0)
                hypotheticalMoves.add(new HypotheticalMove(move.getX() - 1, move.getY() - 1));
            if (!move.hasSouthEastNeighbour(gameBoard) && move.getX() + 1 < gameBoard.getSize() && move.getY() + 1 < gameBoard.getSize())
                hypotheticalMoves.add(new HypotheticalMove(move.getX() + 1, move.getY() + 1));
            if (!move.hasSouthWestNeighbour(gameBoard) && move.getY() + 1 < gameBoard.getSize() && move.getX() > 0)
                hypotheticalMoves.add(new HypotheticalMove(move.getX() - 1, move.getY() + 1));
        }
        for (Move move : gameBoard.xMoves) {
            if (!move.hasEastNeighbour(gameBoard) && move.getX() + 1 < gameBoard.getSize() && move.getY() >= 0)
                hypotheticalMoves.add(new HypotheticalMove(move.getX() + 1, move.getY()));
            if (!move.hasWestNeighbour(gameBoard) && move.getX() > 0 && move.getY() >= 0)
                hypotheticalMoves.add(new HypotheticalMove(move.getX() - 1, move.getY()));
            if (!move.hasNorthNeighbour(gameBoard) && move.getY() > 0 && move.getX() >= 0)
                hypotheticalMoves.add(new HypotheticalMove(move.getX(), move.getY() - 1));
            if (!move.hasSouthNeighbour(gameBoard) && move.getY() + 1 < gameBoard.getSize() && move.getY() >= 0)
                hypotheticalMoves.add(new HypotheticalMove(move.getX(), move.getY() + 1));
            if (!move.hasNorthEastNeighbour(gameBoard) && move.getX() + 1 < gameBoard.getSize() && move.getY() > 0)
                hypotheticalMoves.add(new HypotheticalMove(move.getX() + 1, move.getY() - 1));
            if (!move.hasNorthWestNeighbour(gameBoard) && move.getY() > 0 && move.getX() > 0)
                hypotheticalMoves.add(new HypotheticalMove(move.getX() - 1, move.getY() - 1));
            if (!move.hasSouthEastNeighbour(gameBoard) && move.getX() + 1 < gameBoard.getSize() && move.getY() + 1 < gameBoard.getSize())
                hypotheticalMoves.add(new HypotheticalMove(move.getX() + 1, move.getY() + 1));
            if (!move.hasSouthWestNeighbour(gameBoard) && move.getY() + 1 < gameBoard.getSize() && move.getX() > 0)
                hypotheticalMoves.add(new HypotheticalMove(move.getX() - 1, move.getY() + 1));
        }

        return hypotheticalMoves.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public void setIsX(boolean isX) {
        this.isX = isX;
    }
}
