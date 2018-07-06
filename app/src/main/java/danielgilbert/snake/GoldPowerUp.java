package danielgilbert.snake;

import android.graphics.Color;

public class GoldPowerUp extends PowerUp {

    public GoldPowerUp(int verticalBlocks, int horizontalBlocks) {
        super(verticalBlocks, horizontalBlocks);
    }

    @Override
    public int getPaintColour() {
        return Color.argb(255, 255, 215, 0);
    }
}
