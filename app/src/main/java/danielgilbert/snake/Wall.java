package danielgilbert.snake;
import java.util.ArrayList;

public class Wall {
    private float xLocation;
    private float yLocation;

    public Wall(float xLocation, float yLocation) {
        this.xLocation = xLocation;
        this.yLocation = yLocation;
    }

    public float getWallX() {
        return xLocation;
    }

    public float getWallY() {
        return yLocation;
    }
}
