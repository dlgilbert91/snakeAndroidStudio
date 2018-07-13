package danielgilbert.snake;

import java.util.Random;

public abstract class PowerUp {
    private int xLocation;
    private int yLocation;
    private int numHorizontalScreenBlocks;
    private int numVerticalScreenBlocks;
    private boolean isActive;

    public PowerUp(int verticalBlocks, int horizontalBlocks) {
        isActive = true;
        numHorizontalScreenBlocks = horizontalBlocks;
        numVerticalScreenBlocks = verticalBlocks;
        setPowerUpLocation();
    }

    public void setPowerUpLocation() {
        Random random = new Random();
        xLocation = random.nextInt(numHorizontalScreenBlocks);
        yLocation = random.nextInt(numVerticalScreenBlocks);
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

    //todo public abstract void getSpawnSound();
    public abstract int getPaintColour();
    public abstract String getPowerUpType();
}
