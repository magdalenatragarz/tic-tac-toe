package tictactoe;

import player.Move;

import java.util.ArrayList;
import java.util.List;

public class GameBoard {

    private final int size;
    private final int toWin;
    public int level;
    public BoardField [] [] fields;
    public List<Move> xMoves;
    public List<Move> oMoves;

    public GameBoard(int size, int toWin) {
        this.size = size;
        this.toWin = toWin;
        this.level = 0;

        xMoves = new ArrayList<>();
        oMoves = new ArrayList<>();
        fields = new BoardField[size][size];

        for (int x=0 ; x<size ; x++){
            for (int y=0 ; y<size ; y++){
                fields [x] [y] = new BoardField(0);
            }
        }
    }

    public int getSize() {
        return size;
    }

    public boolean checkNeighbourhood(int x, int y){

        int counter = 0;
        //boolean hasNeighbour = true;

        while(hasWestNeighbour(x-counter,y)){
            System.out.println("checking if ("+(x-counter)+","+y+") is neighbor of ("+(x-counter-1)+","+y+")");
            //
            counter ++;
            //System.out.println(" "+counter);
            if (counter+1 == toWin) return true;
        }

        counter = 0;
        //hasNeighbour = true;

        while( hasEastNeighbour(x+counter,y)){
            System.out.println("checking if ("+(x+counter)+","+y+") is neighbor of ("+(x+counter+1)+","+y+")");
            counter ++;
            if (counter+1 == toWin) return true;
        }

        counter = 0;

        while(hasNorthNeighbour(x,y-counter)){
            System.out.println("checking if ("+x+","+(y-counter)+") is neighbor of ("+x+","+(y-counter-1)+")");
            counter ++;
            if (counter+1 == toWin) return true;
        }

        counter = 0;

        while(hasSouthNeighbour(x,y+counter)){
            System.out.println("checking if ("+x+","+(y+counter)+") is neighbor of ("+x+","+(y+counter+1)+")");

            counter ++;
            if (counter+1 == toWin) return true;
        }

        counter = 0;
        //hasNeighbour = true;

        while(hasSouthEastNeighbour(x+counter,y+counter)){
            System.out.println("checking if ("+(x+counter)+","+(y+counter)+") is neighbor of ("+(x+counter+1)+","+(y+counter+1)+")");
            counter ++;
            if (counter+1 == toWin) return true;
        }

        counter = 0;

        while(hasSouthWestNeighbour(x-counter,y+counter)){
            System.out.println("checking if ("+(x-counter)+","+(y+counter)+") is neighbor of ("+(x-counter-1)+","+(y+counter+1)+")");
            counter ++;
            if (counter+1 == toWin) return true;
        }

        counter = 0;

        while(hasNorthWestNeighbour(x-counter,y-counter)){
            System.out.println("checking if ("+(x-counter)+","+(y-counter)+") is neighbor of ("+(x+counter+1)+","+(y-counter-1)+")");
            counter ++;
            if (counter+1 == toWin) return true;
        }

        counter = 0;

        while(hasNorthEastNeighbour(x+counter,y-counter)){
            System.out.println("checking if ("+(x+counter)+","+(y-counter)+") is neighbor of ("+(x+counter+1)+","+(y-counter-1)+")");
            counter ++;
            if (counter+1 == toWin) return true;
        }

        return false;

    }


    public boolean hasWestNeighbour(int x,int y) {
        return (x > 0 && y < size && y < size && fields[x][y].isSet() && fields[x-1][y].isSet() && (fields[x][y].isX()==fields[x-1][y].isX()));
    }

    public boolean hasEastNeighbour(int x,int y) {
        return x < size && y < size && fields[x+1][y].isSet() && fields[x][y].isSet() && (fields[x+1][y].isX() == fields[x][y].isX());
    }

    public  boolean hasNorthNeighbour(int x,int y) {
        return y > 0 && x < size && y < size && fields[x][y-1].isSet() && fields[x][y].isSet() && (fields[x][y-1].isX() == fields[x][y].isX());
    }

    public boolean hasSouthNeighbour(int x,int y) {
        return x < size && y < size && fields[x][y+1].isSet() && fields[x][y].isSet() && (fields[x][y+1].isX() == fields[x][y].isX());
    }

    public boolean hasSouthEastNeighbour(int x,int y) {
        //System.out.println("south east:"+x+","+y+","+isX+"=?="+fields[x+1][y+1].isX()+", "+ fields[x][y].getLevel() + "eh" + fields[x+1][y+1].getLevel());
        return x < size && y < size && fields[x+1][y+1].isSet() && fields[x][y].isSet() && (fields[x+1][y+1].isX() == fields[x][y].isX());
    }

    public boolean hasSouthWestNeighbour(int x,int y) {
        return x > 0 && x < size && y < size && fields[x-1][y+1].isSet() && fields[x][y].isSet() && (fields[x-1][y+1].isX() == fields[x][y].isX());
    }

    public boolean hasNorthEastNeighbour(int x,int y) {
        return y > 0 && x < size && y < size && fields[x+1][y-1].isSet() && fields[x][y].isSet() && (fields[x+1][y-1].isX() == fields[x][y].isX());
    }

    public boolean hasNorthWestNeighbour(int x,int y) {
        return x > 0 && y > 0 && x < size && y < size && fields[x-1][y-1].isSet() && fields[x][y].isSet() && (fields[x-1][y-1].isX() == fields[x][y].isX());
    }



    public boolean checkIfWins(){
        for (Move move : xMoves) {
            if (checkNeighbourhood(move.getX(),move.getY())) return true;
        }
        for (Move move : oMoves) {
            if (checkNeighbourhood(move.getX(),move.getY())) return true;
        }
        return false;
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("   ");
        for (int i = 0 ; i<size ; i++){
            builder.append(" ").append(i).append(" ");
        }
        builder.append("\n");
        for (int y=0; y<size ; y++){
            builder.append(" ").append(y).append(" ");
            for (int x=0 ; x<size ; x++){
                if(fields[x][y].isSet()){
                    if(fields[x][y].isX()){
                        builder.append(" X ");
                    }else{
                        builder.append(" O ");
                    }
                }else{
                    builder.append("   ");
                }
            }
            builder.append("\n");
        }
        return builder.toString();
    }

}
