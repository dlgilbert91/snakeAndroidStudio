package danielgilbert.snake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class SnakeEngine extends SurfaceView implements Runnable {
    // Our game thread for the main game loop
    private Thread thread = null;

    // To hold a reference to the Activity
    private Context context;

    // To hold the screen size in pixels
    private int screenX;
    private int screenY;

    // The size in pixels of a snake segment
    private int blockSize;

    // The size in segments of the playable area
    private final int NUM_BLOCKS_WIDE = 40;
    private int numBlocksHigh;

    // Control pausing between updates
    private long nextFrameTime;
    // Update the game 10 times per second
    private long FPS = 10;
    // There are 1000 milliseconds in a second
    private final long MILLIS_PER_SECOND = 1000;
// We will draw the frame much more often

    // How many points does the player have
    private int score;

    // Everything we need for drawing
// Is the game currently playing?
    private volatile boolean isPlaying;

    // A canvas for our paint
    private Canvas canvas;

    // Required to use canvas
    private SurfaceHolder surfaceHolder;

    // Some paint for our canvas
    private Paint paint;

    private Snake snake;
    private CountDownTimer mCountDownTimer;
    private boolean isPowerUpTimerRunning = true;
    private int powerUpGenerator;
    private ArrayList<Apple> appleArrayList = new ArrayList<>();
    private ArrayList<Wall> wallArrayList = new ArrayList<>();
    private HashMap<String, PowerUp> powerUpHashMap = new HashMap<>();
    private int snakeGreenPowerUpCounter = 0;
    private int appleSpawnCounter = 0;
    Bitmap appleBitmap;
    Bitmap scaledAppleBitmap;
    Bitmap goldAppleBitmap;
    Bitmap scaledGoldAppleBitmap;
    Bitmap arrowBitmap;
    Bitmap scaledArrowBitmap;

    public SnakeEngine(Context context, Point size) {
        super(context);

        context = context;

        screenX = size.x;
        screenY = size.y;

        // Work out how many pixels each block is
        blockSize = screenX / NUM_BLOCKS_WIDE;
        // How many blocks of the same size will fit into the height
        numBlocksHigh = screenY / blockSize;

        // Initialize the drawing objects
        surfaceHolder = getHolder();
        paint = new Paint();

        // Start the game
        newGame();
    }

    @Override
    public void run() {

        while (isPlaying) {

            // Update 10 times a second
            if(updateRequired()) {
                update();
                checkTimer();
                draw();
            }
        }
    }

    public void pause() {
        isPlaying = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            // Error
        }
    }

    public void resume() {
        isPlaying = true;
        thread = new Thread(this);
        thread.start();
    }

    public void newGame() {
        Apple apple = new Apple(numBlocksHigh, NUM_BLOCKS_WIDE);
        snake = new Snake(numBlocksHigh, NUM_BLOCKS_WIDE);
        setupBitmaps();
        snake.setupSnake();
        apple.setupApple();
        appleArrayList.add(apple);
        setupWalls();
        score = 0;
        // Setup nextFrameTime so an update is triggered
        nextFrameTime = System.currentTimeMillis();
        mCountDownTimer = new CountDownTimer(3000, MILLIS_PER_SECOND) {

            public void onTick(long millisUntilFinished) {
                snakeGreenPowerUpCounter++;
                appleSpawnCounter++;
            }

            public void onFinish() {
                isPowerUpTimerRunning = false;
                generatePowerUp();
            }
        }.start();
    }

    public void update() {
        //check snake location
        //check if apple eaten
        //check if snake still alive
        //move snake
        if (snakeGreenPowerUpCounter >= 5) {
            FPS = 10;
        }
        if (appleSpawnCounter >= 5) {
            Apple apple = new Apple(numBlocksHigh, NUM_BLOCKS_WIDE);
            apple.setupApple();
            appleArrayList.add(apple);
            appleSpawnCounter = 0;
        }

        snake.moveSnake();
        if (snake.snakeAppleCollision(appleArrayList)) {
            score++;
            snake.increaseSnakeLength(1);
        }
        //TODO remove powerup after collision
        if (snake.snakePowerUpCollision(powerUpHashMap) == "Gold") {
            score += 5;
        }
        else if (snake.snakePowerUpCollision(powerUpHashMap) == "Green") {
            FPS = 20;
            snakeGreenPowerUpCounter = 0;
        }
    }

    private void checkTimer() {
        if (!isPowerUpTimerRunning) {
            mCountDownTimer.start();
            isPowerUpTimerRunning = true;
        }
    }

    private void setupBitmaps() {
        int sizeY;
        int sizeX;
        appleBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.apple);
        sizeY = screenY * 4 / 100;
        sizeX= appleBitmap.getWidth() * sizeY / appleBitmap.getHeight();
        scaledAppleBitmap = Bitmap.createScaledBitmap(appleBitmap, sizeX, sizeY, false);

        goldAppleBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.goldapple);
        sizeY = screenY * 4 / 100;
        sizeX= goldAppleBitmap.getWidth() * sizeY / goldAppleBitmap.getHeight();
        scaledGoldAppleBitmap = Bitmap.createScaledBitmap(goldAppleBitmap, sizeX, sizeY, false);

        arrowBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.arrow);
        sizeY = screenY * 4 / 100;
        sizeX= arrowBitmap.getWidth() * sizeY / arrowBitmap.getHeight();
        scaledArrowBitmap = Bitmap.createScaledBitmap(arrowBitmap, sizeX, sizeY, false);
    }

    private void generatePowerUp() {
        Random random = new Random();
        powerUpGenerator = random.nextInt(2);
        if (powerUpGenerator == 0) {
            if (powerUpHashMap.containsKey("Gold")) {
                if (!powerUpHashMap.get("Gold").isPowerUpSpawned()) {
                    PowerUp powerUp;
                    PowerUpFactory PF = new PowerUpFactory();
                    powerUp = PF.getPowerUp("Gold", numBlocksHigh, NUM_BLOCKS_WIDE);
                    powerUpHashMap.put("Gold", powerUp);
                }
            } else {
                PowerUp powerUp;
                PowerUpFactory PF = new PowerUpFactory();
                powerUp = PF.getPowerUp("Gold", numBlocksHigh, NUM_BLOCKS_WIDE);
                powerUpHashMap.put("Gold", powerUp);
            }
        }
        else if (powerUpGenerator == 1) {
            if (powerUpHashMap.containsKey("Green")) {
                if (!powerUpHashMap.get("Green").isPowerUpSpawned()) {
                    PowerUp powerUp;
                    PowerUpFactory PF = new PowerUpFactory();
                    powerUp = PF.getPowerUp("Green", numBlocksHigh, NUM_BLOCKS_WIDE);
                    powerUpHashMap.put("Green", powerUp);
                }
            } else {
                PowerUp powerUp;
                PowerUpFactory PF = new PowerUpFactory();
                powerUp = PF.getPowerUp("Green", numBlocksHigh, NUM_BLOCKS_WIDE);
                powerUpHashMap.put("Green", powerUp);
            }
        } else {
            Log.e("POWERUP", "Generated powerup number not in bounds");
        }
    }

    public void draw() {
        // Get a lock on the canvas
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();

            // Fill the screen with Game Code School blue
            canvas.drawColor(Color.argb(255, 26, 128, 182));

            // Set the color of the paint to draw the snake white
            paint.setColor(Color.argb(255, 255, 255, 255));

            // Scale the HUD text
            paint.setTextSize(90);
            canvas.drawText("Score:" + score, 10, 70, paint);

            // Draw the snake one block at a time
            for (int i = 0; i < snake.getSnakeLength(); i++) {
                canvas.drawRect(snake.getSnakeX(i) * blockSize, snake.getSnakeY(i) * blockSize,
                        snake.getSnakeX(i) * blockSize + blockSize,
                        snake.getSnakeY(i) * blockSize + blockSize, paint);
            }

            // Set the color of the paint to draw apple red
            paint.setColor(Color.argb(255, 255, 0, 0));

            // Draw apple
            drawApples();

            //Draw powerups
            drawPowerUps();

            //Draw Walls
            drawWalls();

            // Unlock the canvas and reveal the graphics for this frame
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    public void drawApples() {
        for (Apple a : appleArrayList) {
            /*canvas.drawRect(a.getAppleX() * blockSize,
                    (a.getAppleY() * blockSize),
                    (a.getAppleX() * blockSize) + blockSize,
                    (a.getAppleY() * blockSize) + blockSize,
                    paint);*/
            //canvas.drawBitmap(scaledAppleBitmap, a.getAppleX() - (appleBitmap.getWidth() / 2), a.getAppleY() - (appleBitmap.getHeight() / 2), null);
            canvas.drawBitmap(scaledAppleBitmap, a.getAppleX() * blockSize, a.getAppleY() * blockSize, null);
        }
    }

    public void drawPowerUps() {
        for (PowerUp p : powerUpHashMap.values()) {
            paint.setColor(p.getPaintColour());
            if (p.isPowerUpSpawned()) {
                /*canvas.drawRect(p.getPowerUpX() * blockSize,
                        (p.getPowerUpY() * blockSize),
                        (p.getPowerUpX() * blockSize) + blockSize,
                        (p.getPowerUpY() * blockSize) + blockSize,
                        paint);*/
                if (p.getPowerUpType() == "Gold") {
                    canvas.drawBitmap(scaledGoldAppleBitmap, p.getPowerUpX() * blockSize, p.getPowerUpY() * blockSize, paint);
                }
                else if (p.getPowerUpType() == "Green") {
                    canvas.drawBitmap(scaledArrowBitmap, p.getPowerUpX() * blockSize, p.getPowerUpY() * blockSize, paint);
                }
            }
        }
    }

    private void setupWalls() {
        //Left Walls
        for (int i = 0; i <= numBlocksHigh / 3; i++) {
            Wall wall = new Wall(0, i);
            wallArrayList.add(wall);
        }
        for (int i = numBlocksHigh - numBlocksHigh / 3; i <= numBlocksHigh; i++) {
            Wall wall = new Wall(0, i);
            wallArrayList.add(wall);
        }

        //Top Walls
        for (int i = 0; i <= NUM_BLOCKS_WIDE / 3; i++) {
            Wall wall = new Wall(i, 0);
            wallArrayList.add(wall);
        }

        for (int i = NUM_BLOCKS_WIDE - NUM_BLOCKS_WIDE / 3; i <= NUM_BLOCKS_WIDE; i++) {
            Wall wall = new Wall(i, 0);
            wallArrayList.add(wall);
        }
//TODO might have to implement more walls depending on collision detection depending on getWall location methods - screen ratio causes the bottom and right walls to be out by 0.5 units
        //Bottom Walls
        for (int i = 0; i <= NUM_BLOCKS_WIDE / 3; i++) {
            Wall wall = new Wall(i, (float)(numBlocksHigh - 0.5));
            wallArrayList.add(wall);
        }

        for (int i = NUM_BLOCKS_WIDE - NUM_BLOCKS_WIDE / 3; i <= NUM_BLOCKS_WIDE; i++) {
            Wall wall = new Wall(i, (float)(numBlocksHigh - 0.5));
            wallArrayList.add(wall);
        }

        //Right Walls
/*        for (int i = 0; i <= numBlocksHigh / 3; i++) {
            Wall wall = new Wall((float)(NUM_BLOCKS_WIDE - 0.5), i);
            wallArrayList.add(wall);
        }
        for (int i = numBlocksHigh - numBlocksHigh / 3; i <= numBlocksHigh; i++) {
            Wall wall = new Wall((float)(NUM_BLOCKS_WIDE - 0.5), i);
            wallArrayList.add(wall);
        }*/
        for (int i = 0; i <= numBlocksHigh / 3; i++) {
            Wall wall = new Wall((float)(NUM_BLOCKS_WIDE - 1), i);
            wallArrayList.add(wall);
        }
        for (int i = numBlocksHigh - numBlocksHigh / 3; i <= numBlocksHigh; i++) {
            Wall wall = new Wall((float)(NUM_BLOCKS_WIDE - 1), i);
            wallArrayList.add(wall);
        }

        //Center Vertical Walls
        for (int i = numBlocksHigh / 2 - 5; i <= numBlocksHigh / 2 + 5; i++) {
            Wall wall = new Wall(NUM_BLOCKS_WIDE / 2 - 1, i);
            wallArrayList.add(wall);
            wall = new Wall(NUM_BLOCKS_WIDE / 2, i);
            wallArrayList.add(wall);
            wall = new Wall(NUM_BLOCKS_WIDE / 2 + 1, i);
            wallArrayList.add(wall);
        }

        //Center Horizontal Walls
        for (int i = NUM_BLOCKS_WIDE / 2 - 6; i <= NUM_BLOCKS_WIDE / 2 + 6; i++) {
            Wall wall = new Wall(i, numBlocksHigh / 2 - 1);
            wallArrayList.add(wall);
            wall = new Wall(i, numBlocksHigh / 2);
            wallArrayList.add(wall);
            wall = new Wall(i, numBlocksHigh / 2 + 1);
            wallArrayList.add(wall);
        }
    }

    private void drawWalls() {
        for (Wall w : wallArrayList) {
            paint.setARGB(255, 57, 255, 20);
            canvas.drawRect(w.getWallX() * blockSize,
                        (w.getWallY() * blockSize),
                        (w.getWallX() * blockSize) + blockSize - 1,
                        (w.getWallY() * blockSize) + blockSize - 1,
                        paint);
        }
    }

    public boolean updateRequired() {

        // Are we due to update the frame
        if(nextFrameTime <= System.currentTimeMillis()){
            // Tenth of a second has passed

            // Setup when the next update will be triggered
            nextFrameTime =System.currentTimeMillis() + MILLIS_PER_SECOND / FPS;

            // Return true so that the update and draw
            // functions are executed
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                snake.setSnakeDirection(motionEvent.getX(), motionEvent.getY(), screenX, screenY);
        }
        return true;
    }
}
