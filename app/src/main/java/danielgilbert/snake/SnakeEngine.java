package danielgilbert.snake;

import android.content.Context;
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
    private final int NUM_BLOCKS_WIDE = 60;
    private int numBlocksHigh;

    // Control pausing between updates
    private long nextFrameTime;
    // Update the game 10 times per second
    private final long FPS = 10;
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

    Snake snake;
    Apple apple;
    private CountDownTimer mCountDownTimer;
    private boolean isPowerUpTimerRunning = false;
    private int powerUpGenerator;
    private ArrayList<PowerUp> powerUpList = new ArrayList<PowerUp>();

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

        snake = new Snake(numBlocksHigh, NUM_BLOCKS_WIDE);
        apple = new Apple(numBlocksHigh, NUM_BLOCKS_WIDE);

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
        snake.setupSnake();
        apple.setupApple();
        score = 0;
        // Setup nextFrameTime so an update is triggered
        nextFrameTime = System.currentTimeMillis();
        mCountDownTimer = new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) { }

            public void onFinish() {
                //generatePowerUp();
                isPowerUpTimerRunning = false;
            }
        }.start();
    }

    public void update() {
        //check snake location
        //check if apple eaten
        //check if snake still alive
        //move snake
        snake.moveSnake();
        if (snake.snakeAppleCollision(apple.getAppleX(), apple.getAppleY())) {
            score++;
            apple.setupApple();
            snake.increaseSnakeLength(1);
        }
    }

    private void checkTimer() {
        if (!isPowerUpTimerRunning) {
            mCountDownTimer.start();
            isPowerUpTimerRunning = true;
        }
    }

    private void generatePowerUp() {
        Random random = new Random();
        powerUpGenerator = random.nextInt(2);
        if (powerUpGenerator == 0) {
            PowerUp powerUp;
            PowerUpFactory PF = new PowerUpFactory();
            powerUp = PF.getPowerUp("Golden", numBlocksHigh, NUM_BLOCKS_WIDE);
            powerUpList.add(powerUp);
        }
        else if (powerUpGenerator == 1) {

        }
        else if (powerUpGenerator == 2) {

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
            canvas.drawRect(apple.getAppleX() * blockSize,
                    (apple.getAppleY() * blockSize),
                    (apple.getAppleX() * blockSize) + blockSize,
                    (apple.getAppleY() * blockSize) + blockSize,
                    paint);

            //Draw powerups
            drawPowerUps();

            // Unlock the canvas and reveal the graphics for this frame
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    public void drawPowerUps() {
        for (PowerUp p : powerUpList) {
            paint.setColor(p.getPaintColour());
            canvas.drawRect(p.getPowerUpX() * blockSize,
                    (p.getPowerUpY() * blockSize),
                    (p.getPowerUpX() * blockSize) + blockSize,
                    (p.getPowerUpY() * blockSize) + blockSize,
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
