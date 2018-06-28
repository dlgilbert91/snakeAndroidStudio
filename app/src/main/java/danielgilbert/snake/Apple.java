package danielgilbert.snake;

import java.util.Random;

public class Apple {
    private int xLocation;
    private int yLocation;
    private int numHorizontalScreenBlocks;
    private int numVerticalScreenBlocks;

    public Apple(int verticalBlocks, int horizontalBlocks) {
        numHorizontalScreenBlocks = horizontalBlocks;
        numVerticalScreenBlocks = verticalBlocks;
    }

    public void setupApple() {
        Random random = new Random();
        xLocation = random.nextInt(numHorizontalScreenBlocks);
        yLocation = random.nextInt(numVerticalScreenBlocks);
    }

    public int getAppleX() {
        return xLocation;
    }

    public int getAppleY() {
        return yLocation;
    }
}
