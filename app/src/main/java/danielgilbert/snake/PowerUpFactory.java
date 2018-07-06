package danielgilbert.snake;

public class PowerUpFactory {

    public PowerUp getPowerUp(String powerUpCode, int numBlocksHigh, int NUM_BLOCKS_WIDE) {
        PowerUp power = null;
        if (powerUpCode == "Golden") {
            power = new GoldPowerUp(numBlocksHigh, NUM_BLOCKS_WIDE);
        }
        return power;
    }
}
