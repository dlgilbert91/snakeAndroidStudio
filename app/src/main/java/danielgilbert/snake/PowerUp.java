package danielgilbert.snake;

public abstract class PowerUp {
    private int locationX;
    private int locationY;
    private boolean isSpawned;

    public PowerUp(int locationX, int locationY) {
        this.locationX = locationX;
        this.locationY = locationY;
        isSpawned = false;
    }

    public int getPowerUpX() {
        return locationX;
    }

    public int getPowerUpY() {
        return locationY;
    }

    public boolean isPowerUpSpawned() {
        return isSpawned;
    }

    //todo public abstract void getSpawnSound();
    //todo public abstract void getPaintColour();
}
