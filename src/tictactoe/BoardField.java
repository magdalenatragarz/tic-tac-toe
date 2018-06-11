package tictactoe;

public class BoardField {
    private boolean isSet;
    private int level;
    public boolean isX;

    public BoardField(int level) {
        this.isSet = false;
        this.level = level;
    }

    public void set(int level) {
        this.isSet = true;
        this.level = level;
    }


    public int getLevel() {
        return level;
    }

    public boolean isSet() {
        return isSet;
    }

   public boolean isX() {
        return (level % 2) == 1;
    }


    public void unset() {
        this.isSet = false;
        this.level = 0;
    }
}
