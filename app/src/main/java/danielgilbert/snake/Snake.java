package danielgilbert.snake;

public class Snake {
    private int xLocation;
    private int yLocation;
    private int snakeLength;
    private enum Direction {UP, DOWN, LEFT, RIGHT};
    private int snakeBlockSize;         //size of each snake segment
    private int numHorizontalScreenBlocks;
    private int numVerticalScreenBlocks;

    //consider coming back and setting this up as a struct
    private int[] snakeX;
    private int[] snakeY;

    public Snake(int snakeBlockSize, int verticalBlocks, int horizontalBlocks) {
        this.snakeBlockSize = snakeBlockSize;
        this.numVerticalScreenBlocks = verticalBlocks;
        this.numHorizontalScreenBlocks = horizontalBlocks;
        SetupSnake();
    }

    private void SetupSnake() {
        snakeX = new int[200];
        snakeY = new int[200];
        snakeLength = 1;
        snakeX[0] = numHorizontalScreenBlocks / 2;
        snakeY[0] = numVerticalScreenBlocks / 2;
    }

    public int GetSnakeLength() {

        return 0;
    }

    public boolean CheckSnakeCollision() {

        return false;
    }

    public void MoveSnake() {

    }

    public void DrawSnake() {

    }
}