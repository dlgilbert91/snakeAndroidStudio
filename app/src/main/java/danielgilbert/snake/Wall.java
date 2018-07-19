package danielgilbert.snake;
import java.util.ArrayList;

public class Wall {
    private float xLocation;
    private float yLocation;
    private int numHorizontalScreenBlocks;
    private int numVerticalScreenBlocks;
    private ArrayList<Wall> wallArrayList;

        public Wall(float xLocation, float yLocation) {
            this.xLocation = xLocation;
            this.yLocation = yLocation;
        }
/*    public Wall(int verticalBlocks, int horizontalBlocks) {
        wallArrayList = new ArrayList<>();
        numHorizontalScreenBlocks = horizontalBlocks;
        numVerticalScreenBlocks = verticalBlocks;
    }*/
/*
    public void setupWall() {
    }*/

    public float getWallX() {
        return xLocation;
    }

    public float getWallY() {
        return yLocation;
    }
}
