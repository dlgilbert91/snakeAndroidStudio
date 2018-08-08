package danielgilbert.snake;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class Snake {
    private int snakeLength;

    private enum Direction {UP, DOWN, LEFT, RIGHT};
    private int numHorizontalScreenBlocks;
    private int numVerticalScreenBlocks;

    private int[] snakeX;
    private int[] snakeY;
    private Direction direction;

    //TODO SETUP SNAKE AS A SINGLETON

    public Snake(int verticalBlocks, int horizontalBlocks) {
        this.numVerticalScreenBlocks = verticalBlocks;
        this.numHorizontalScreenBlocks = horizontalBlocks;
    }

    public void setupSnake() {
        snakeX = new int[200];
        snakeY = new int[200];
        snakeLength = 1;
        snakeX[0] = 2;
        snakeY[0] = 4;
        direction = Direction.RIGHT;
    }

    public int getSnakeLength() {
        return snakeLength;
    }

    public int getSnakeX(int index) {
        return snakeX[index];
    }

    public int getSnakeY(int index) {
        return snakeY[index];
    }

    public void moveSnake(int BEZEL_HEIGHT) {
        for (int i = snakeLength; i > 0; i--) {
            snakeX[i] = snakeX[i - 1];
            snakeY[i] = snakeY[i - 1];
        }
        switch (direction) {
            case UP:
                snakeY[0]--;
                if (snakeY[0] < BEZEL_HEIGHT) {
                    snakeY[0] = numVerticalScreenBlocks - BEZEL_HEIGHT;
                }
                break;
            case DOWN:
                snakeY[0]++;
                if (snakeY[0] > numVerticalScreenBlocks - BEZEL_HEIGHT) {
                    snakeY[0] = BEZEL_HEIGHT;
                }
                break;
            case LEFT:
                snakeX[0]--;
                if (snakeX[0] < 0) {
                    snakeX[0] = numHorizontalScreenBlocks;
                }
                break;
            case RIGHT:
                snakeX[0]++;
                if (snakeX[0] > numHorizontalScreenBlocks) {
                    snakeX[0] = 0;
                }
                break;
        }
    }

    public void setSnakeDirection(float motionEventValueX, float motionEventValueY, int screenX, int screenY) {
        switch(direction) {
            case UP:
                if (motionEventValueX < screenX / 2) {
                    direction = Direction.LEFT;
                }
                else if (motionEventValueX > screenX / 2) {
                    direction = Direction.RIGHT;
                }
                break;
            case DOWN:
                if (motionEventValueX < screenX / 2) {
                    direction = Direction.LEFT;
                }
                else if (motionEventValueX > screenX / 2) {
                    direction = Direction.RIGHT;
                }
                break;
            case LEFT:
                if (motionEventValueY < screenY / 2) {
                    direction = Direction.UP;
                }
                else if (motionEventValueY > screenY / 2) {
                    direction = Direction.DOWN;
                }
                break;
            case RIGHT:
                if (motionEventValueY < screenY / 2) {
                    direction = Direction.UP;
                }
                else if (motionEventValueY > screenY / 2) {
                    direction = Direction.DOWN;
                }
                break;
        }
    }

    public boolean snakeAppleCollision(ArrayList<Apple> appleArrayList) {
        for (Apple a : appleArrayList) {
            if (snakeX[0] == a.getAppleX() && snakeY[0] == a.getAppleY()) {
                appleArrayList.remove(a);
                return true;
            }
        }
        return false;
    }

    public String snakePowerUpCollision(HashMap<String, PowerUp> powerUpHashMap) {
        String powerUpType;
        for (PowerUp p : powerUpHashMap.values()) {
            if (snakeX[0] == p.getPowerUpX() && snakeY[0] == p.getPowerUpY()) {
                    powerUpType = p.getPowerUpType();
                    p.setIsPowerUpSpawned(false);
                    return powerUpType;
            }
        }
        return null;
    }

    public void increaseSnakeLength(int amount) {
        snakeLength += amount;
    }

    public boolean wallCollision(ArrayList<Wall> wallArrayList, int BEZEL_HEIGHT, int numBlocksHigh) {
        for (Wall w : wallArrayList) {
            if (snakeX[0] == w.getWallX() && snakeY[0] == w.getWallY()) {
                return true;
            }
        }
        //check if the snake hits the bottom wall due to the 0.5 float value (screen resolution issue)
        return false;
    }
}