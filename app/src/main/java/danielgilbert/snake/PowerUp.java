package danielgilbert.snake;

import java.util.Random;

public abstract class PowerUp {
    private int xLocation;
    private int yLocation;
    private boolean isActive;

    public PowerUp(int xLocation, int yLocation) {
        isActive = true;
        this.xLocation = xLocation;
        this.yLocation = yLocation;
    }

    public int getPowerUpX() {
        return xLocation;
    }

    public int getPowerUpY() {
        return yLocation;
    }

    public boolean isPowerUpSpawned() {
        return isActive;
    }

    public void setIsPowerUpSpawned(boolean isSpawned) {
        isActive = isSpawned;
    }

    public abstract int getPaintColour();
    public abstract String getPowerUpType();
}
