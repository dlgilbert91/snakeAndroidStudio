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

    public void SetupApple() {
        Random random = new Random();
        xLocation = random.nextInt(numHorizontalScreenBlocks);
        yLocation = random.nextInt(numVerticalScreenBlocks);
    }

    public int GetAppleX() {
        return xLocation;
    }

    public int GetAppleY() {
        return yLocation;
    }
}
