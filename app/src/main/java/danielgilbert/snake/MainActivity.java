package danielgilbert.snake;

import android.app.Activity;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Point;
import android.view.Display;

public class MainActivity extends Activity {
    SnakeEngine snakeEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //get pixel dimensions of the screen
        Display display = getWindowManager().getDefaultDisplay();

        //initialize the result into a Point object
        Point size = new Point();
        display.getSize(size);

        //create instance of SnakeEngine class
        snakeEngine = new SnakeEngine(this, size);

        //make snakeEngine view of the activity
        setContentView(snakeEngine);
    }

    // Start the thread in snakeEngine
    @Override
    protected void onResume() {
        super.onResume();
        snakeEngine.resume();
    }

    // Stop the thread in snakeEngine
    @Override
    protected void onPause() {
        super.onPause();
        snakeEngine.pause();
    }
}
