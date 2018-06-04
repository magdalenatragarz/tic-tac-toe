package player;

import tictactoe.GameBoard;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ComputerPlayer implements Player {
    private boolean isX;

    @Override
    public Move move(GameBoard gameBoard) {
        List<HypotheticalMove> hypotheticalMoves = findMoorNeighborhood(gameBoard);
        if(hypotheticalMoves.size()==0) return new Move(gameBoard.getSize()/2,gameBoard.getSize()/2,isX);
        Random random = new Random();
        int x = random.nextInt(hypotheticalMoves.size());
        return new Move(hypotheticalMoves.get(x).getX(),hypotheticalMoves.get(x).getY(),isX);
    }





    List<HypotheticalMove> findMoorNeighborhood(GameBoard gameBoard){
        List<HypotheticalMove> hypotheticalMoves = new ArrayList<>();
        for (Move move : gameBoard.oMoves) {
            if (!move.hasEastNeighbour(gameBoard)) hypotheticalMoves.add(new HypotheticalMove(move.getX(),move.getY()+1));
            if (!move.hasWestNeighbour(gameBoard)) hypotheticalMoves.add(new HypotheticalMove(move.getX(),move.getY()-1));
            if (!move.hasNorthNeighbour(gameBoard)) hypotheticalMoves.add(new HypotheticalMove(move.getX()-1,move.getY()));
            if (!move.hasSouthNeighbour(gameBoard)) hypotheticalMoves.add(new HypotheticalMove(move.getX()+1,move.getY()));
            if (!move.hasNorthEastNeighbour(gameBoard)) hypotheticalMoves.add(new HypotheticalMove(move.getX()-1,move.getY()+1));
            if (!move.hasNorthWestNeighbour(gameBoard)) hypotheticalMoves.add(new HypotheticalMove(move.getX()-1,move.getY()-1));
            if (!move.hasSouthEastNeighbour(gameBoard)) hypotheticalMoves.add(new HypotheticalMove(move.getX()+1,move.getY()+1));
            if (!move.hasSouthWestNeighbour(gameBoard)) hypotheticalMoves.add(new HypotheticalMove(move.getX()+1,move.getY()-1));
        }
        for (Move move : gameBoard.xMoves) {
            if (!move.hasEastNeighbour(gameBoard)) hypotheticalMoves.add(new HypotheticalMove(move.getX(),move.getY()+1));
            if (!move.hasWestNeighbour(gameBoard)) hypotheticalMoves.add(new HypotheticalMove(move.getX(),move.getY()-1));
            if (!move.hasNorthNeighbour(gameBoard)) hypotheticalMoves.add(new HypotheticalMove(move.getX()-1,move.getY()));
            if (!move.hasSouthNeighbour(gameBoard)) hypotheticalMoves.add(new HypotheticalMove(move.getX()+1,move.getY()));
            if (!move.hasNorthEastNeighbour(gameBoard)) hypotheticalMoves.add(new HypotheticalMove(move.getX()-1,move.getY()+1));
            if (!move.hasNorthWestNeighbour(gameBoard)) hypotheticalMoves.add(new HypotheticalMove(move.getX()-1,move.getY()-1));
            if (!move.hasSouthEastNeighbour(gameBoard)) hypotheticalMoves.add(new HypotheticalMove(move.getX()+1,move.getY()+1));
            if (!move.hasSouthWestNeighbour(gameBoard)) hypotheticalMoves.add(new HypotheticalMove(move.getX()+1,move.getY()-1));
        }

        return hypotheticalMoves.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public void setIsX(boolean isX) {
        this.isX = isX;
    }
}
