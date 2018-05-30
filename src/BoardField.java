public class BoardField {
    private boolean isSet;
    private int level;

    public BoardField(int level) {
        this.isSet = false;
        this.level = level;
    }

    public void set(int level){
        this.isSet = true;
        this.level = level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isSet(){
        return isSet;
    }

    boolean isX(){
        return (level % 2) == 1;
    }
}
