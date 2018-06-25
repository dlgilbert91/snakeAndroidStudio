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
    private Context context;
    private Thread thread = null;
    private Snake snake;
    private Apple apple;
    private int score;
    // The size in segments of the playable area
    private final int NUM_BLOCKS_WIDE = 40;
    private int numBlocksHigh;

    private int screenX;
    private int screenY;
    private int blockSize;

    // Control pausing between updates
    private long nextFrameTime;
    // Update the game 10 times per second
    private final long FPS = 10;
    // There are 1000 milliseconds in a second
    private final long MILLIS_PER_SECOND = 1000;
    // Everything we need for drawing
    // Is the game currently playing?
    private volatile boolean isPlaying;

    // A canvas for our paint
    private Canvas canvas;

    // Required to use canvas
    private SurfaceHolder surfaceHolder;

    // Some paint for our canvas
    private Paint paint;

    public SnakeEngine(Context context, Point size) {
        super(context);
        this.context = context;
        screenX = size.x;
        screenY = size.y;
        snake = new Snake();
        apple = new Apple();
        isPlaying = true;

        // Work out how many pixels each block is
        blockSize = screenX / NUM_BLOCKS_WIDE;
        // How many blocks of the same size will fit into the height
        numBlocksHigh = screenY / blockSize;

        // Initialize the drawing objects
        surfaceHolder = getHolder();
        paint = new Paint();

        // Start the game
        //newGame();
    }

    public void NewGame() {
/*        snake.SetupSnake();
        apple.SpawnApple();*/
        score = 0;
        // Setup nextFrameTime so an update is triggered
        nextFrameTime = System.currentTimeMillis();
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

/*            // Draw the snake one block at a time
            for (int i = 0; i < snakeLength; i++) {
                canvas.drawRect(snakeXs[i] * blockSize,
                        (snakeYs[i] * blockSize),
                        (snakeXs[i] * blockSize) + blockSize,
                        (snakeYs[i] * blockSize) + blockSize,
                        paint);
            }*/

            // Set the color of the paint to draw Bob red
            paint.setColor(Color.argb(255, 255, 0, 0));

            // Draw Bob
/*            canvas.drawRect(bobX * blockSize,
                    (bobY * blockSize),
                    (bobX * blockSize) + blockSize,
                    (bobY * blockSize) + blockSize,
                    paint);*/

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

    public void update() {

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

}
