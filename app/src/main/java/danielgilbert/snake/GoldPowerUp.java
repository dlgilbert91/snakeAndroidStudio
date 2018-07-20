package danielgilbert.snake;

import android.graphics.Color;

public class GoldPowerUp extends PowerUp {

    public GoldPowerUp(int xLocation, int yLocation) {
        super(xLocation, yLocation);
    }

    @Override
    public int getPaintColour() {
        return Color.argb(255, 255, 215, 0);
    }

    @Override
    public String getPowerUpType() {
        return "Gold";
    }
}
