package danielgilbert.snake;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.SoundPool;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.io.IOException;
import java.util.Random;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

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
        NewGame();
    }

    @Override
    public void run() {

        while (isPlaying) {

            // Update 10 times a second
            if(updateRequired()) {
                update();
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

    public void NewGame() {
        snake.SetupSnake();
        apple.SetupApple();
        score = 0;
        // Setup nextFrameTime so an update is triggered
        nextFrameTime = System.currentTimeMillis();
    }

    public void update() {
        //check snake location
        //check if apple eaten
        //check if snake still alive
        //move snake
        snake.MoveSnake();
        if (snake.SnakeAppleCollision(apple.GetAppleX(), apple.GetAppleY())) {
            score++;
            apple.SetupApple();
            snake.IncreaseSnakeLength(1);
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
            for (int i = 0; i < snake.GetSnakeLength(); i++) {
                canvas.drawRect(snake.GetSnakeX(i) * blockSize, snake.GetSnakeY(i) * blockSize,
                        snake.GetSnakeX(i) * blockSize + blockSize,
                        snake.GetSnakeY(i) * blockSize + blockSize, paint);
            }

            // Set the color of the paint to draw apple red
            paint.setColor(Color.argb(255, 255, 0, 0));

            // Draw apple
            canvas.drawRect(apple.GetAppleX() * blockSize,
                    (apple.GetAppleY() * blockSize),
                    (apple.GetAppleX() * blockSize) + blockSize,
                    (apple.GetAppleY() * blockSize) + blockSize,
                    paint);

            // Unlock the canvas and reveal the graphics for this frame
            surfaceHolder.unlockCanvasAndPost(canvas);
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
                snake.SetSnakeDirection(motionEvent.getX(), motionEvent.getY(), screenX, screenY);
        }
        return true;
    }
}
