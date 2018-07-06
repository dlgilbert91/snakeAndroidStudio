package danielgilbert.snake;

import java.util.Random;

public abstract class PowerUp {
    private int xLocation;
    private int yLocation;
    private int numHorizontalScreenBlocks;
    private int numVerticalScreenBlocks;
    private boolean isSpawned;

    public PowerUp(int verticalBlocks, int horizontalBlocks) {
        isSpawned = false;
        setPowerUpLocation();
        numHorizontalScreenBlocks = horizontalBlocks;
        numVerticalScreenBlocks = verticalBlocks;
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
        return isSpawned;
    }

    //todo public abstract void getSpawnSound();
    public abstract int getPaintColour();
}
