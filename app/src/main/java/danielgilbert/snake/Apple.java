package danielgilbert.snake;

import java.util.ArrayList;
import java.util.HashMap;
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

    /**
     * Spawns a new apple into the game world. Spawns a new apple into the game world on if the apple will not spawn on top of another apple, wall or powerup.
     * @param wallArrayList - ArrayList containing the walls in the game world.
     * @param appleArrayList - ArrayList containing the apples in the game world.
     * @param powerUpHashMap - HashMap containing the powerups in the game world.
     * @param BEZEL_HEIGHT - Bezel height surrounding game screen.
     * @param numBlocksHigh - Number of vertical game screen blocks.
     * @return true when apple is not spawning on another object else return false.
     */
    public boolean setupApple(ArrayList<Wall> wallArrayList, ArrayList<Apple> appleArrayList, HashMap<String, PowerUp> powerUpHashMap, int BEZEL_HEIGHT, int numBlocksHigh) {
            Random random = new Random();
            xLocation = random.nextInt(numHorizontalScreenBlocks);
            yLocation = random.nextInt(numVerticalScreenBlocks);
            for (Wall w : wallArrayList) {
                if (xLocation == w.getWallX() && yLocation == w.getWallY()) {
                    return false;
                }
            }
            for (Apple a : appleArrayList) {
                if (xLocation == a.getAppleX() && yLocation == a.getAppleY()) {
                    return false;
                }
            }
            for (PowerUp p : powerUpHashMap.values()) {
                if (xLocation == p.getPowerUpX() && yLocation == p.getPowerUpY()) {
                    return false;
                }
            }
            if (yLocation <= BEZEL_HEIGHT || yLocation >= numBlocksHigh - BEZEL_HEIGHT) {
                return false;
            }
            return true;
    }

    public int getAppleX() {
        return xLocation;
    }

    public int getAppleY() {
        return yLocation;
    }
}
