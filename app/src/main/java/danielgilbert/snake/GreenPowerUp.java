package danielgilbert.snake;

import android.graphics.Color;

public class GreenPowerUp extends PowerUp {

    public GreenPowerUp(int xLocation, int yLocation) {
        super(xLocation, yLocation);
    }

    @Override
    public int getPaintColour() {
        return Color.argb(255, 124, 252, 0);
    }

    @Override
    public String getPowerUpType() {
        return "Green";
    }
}
