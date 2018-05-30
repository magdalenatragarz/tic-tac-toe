import java.util.ArrayList;
import java.util.List;

public class GameBoard {

    private final int size;
    private final int toWin;
    public int level;
    public BoardField[] [] fields;
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


    boolean checkNeighbourhood(int x,int y, boolean isX){

        int counter = 0;
        boolean hasNeighbour = true;

        while(hasNeighbour){
            if (hasNeighbour = hasWestNeighbour(x,y-counter,isX)) counter ++;
            if (counter+1 == toWin) return true;
        }

        counter = 0;
        hasNeighbour = true;

        while(hasNeighbour){
            if (hasNeighbour = hasEastNeighbour(x,y+counter,isX)) counter ++;
            if (counter+1 == toWin) return true;
        }

        counter = 0;
        hasNeighbour = true;

        while(hasNeighbour){
            if (hasNeighbour = hasNorthNeighbour(x-counter,y,isX)) counter ++;
            if (counter+1 == toWin) return true;
        }

        counter = 0;
        hasNeighbour = true;

        while(hasNeighbour){
            if (hasNeighbour = hasSouthNeighbour(x+counter,y,isX)) counter ++;
            if (counter+1 == toWin) return true;
        }

        counter = 0;
        hasNeighbour = true;

        while(hasNeighbour){
            if (hasNeighbour = hasSouthEastNeighbour(x+counter,y+counter,isX)) counter ++;
            if (counter+1 == toWin) return true;
        }

        counter = 0;
        hasNeighbour = true;

        while(hasNeighbour){
            if (hasNeighbour = hasSouthWestNeighbour(x+counter,y-counter,isX)) counter ++;
            if (counter+1 == toWin) return true;
        }

        counter = 0;
        hasNeighbour = true;

        while(hasNeighbour){
            if (hasNeighbour = hasNorthWestNeighbour(x-counter,y-counter,isX)) counter ++;
            if (counter+1 == toWin) return true;
        }

        counter = 0;
        hasNeighbour = true;

        while(hasNeighbour){
            if (hasNeighbour = hasNorthEastNeighbour(x-counter,y+counter,isX)) counter ++;
            if (counter+1 == toWin) return true;
        }

        return false;

    }

    private boolean hasWestNeighbour(int x,int y, boolean isX) {
        return y > 0 && x < size && y < size && fields[x][y-1].isSet() && (isX == fields[x][y].isX());
    }

    private boolean hasEastNeighbour(int x,int y, boolean isX) {
        return x < size && y < size && fields[x][y+1].isSet() && (isX == fields[x][y].isX());
    }

    private boolean hasNorthNeighbour(int x,int y, boolean isX) {
        return x > 0 && x < size && y < size && fields[x-1][y].isSet() && (isX == fields[x][y].isX());
    }

    private boolean hasSouthNeighbour(int x,int y, boolean isX) {
        return x < size && y < size && fields[x+1][y].isSet() && (isX == fields[x][y].isX());
    }

    private boolean hasSouthEastNeighbour(int x,int y, boolean isX) {
        return x < size && y < size && fields[x+1][y+1].isSet() && (isX == fields[x][y].isX());
    }

    private boolean hasSouthWestNeighbour(int x,int y, boolean isX) {
        return y > 0 && x < size && y < size && fields[x+1][y-1].isSet() && (isX == fields[x][y].isX());
    }

    private boolean hasNorthEastNeighbour(int x,int y, boolean isX) {
        return x > 0 && x < size && y < size && fields[x-1][y+1].isSet() && (isX == fields[x][y].isX());
    }

    private boolean hasNorthWestNeighbour(int x,int y, boolean isX) {
        return x > 0 && y > 0 && x < size && y < size && fields[x-1][y-1].isSet() && (isX == fields[x][y].isX());
    }



    boolean checkIfWins(){
        for (Move move : xMoves) {
            if (checkNeighbourhood(move.getX(),move.getY(),move.isX)) return true;
        }
        for (Move move : oMoves) {
            if (checkNeighbourhood(move.getX(),move.getY(),move.isX)) return true;
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
        for (int x=0; x<size ; x++){
            builder.append(" ").append(x).append(" ");
            for (int y=0 ; y<size ; y++){
                if(fields[x][y].isSet()){
                    if(fields[x][y].isX()){
                        builder.append(" X ");
                    }else{
                        builder.append(" O ");
                    }
                }else{
                    builder.append(" ~ ");
                }
            }
            builder.append("\n");
        }
        return builder.toString();
    }

}
