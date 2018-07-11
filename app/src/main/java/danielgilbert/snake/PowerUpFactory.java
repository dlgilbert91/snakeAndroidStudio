package danielgilbert.snake;

public class PowerUpFactory {

    public PowerUp getPowerUp(String powerUpCode, int numBlocksHigh, int NUM_BLOCKS_WIDE) {
        if (powerUpCode == "Gold") {
            PowerUp power;
            power = new GoldPowerUp(numBlocksHigh, NUM_BLOCKS_WIDE);
            return power;
        }
        else if (powerUpCode == "Green") {
            PowerUp power;
            power = new GreenPowerUp(numBlocksHigh, NUM_BLOCKS_WIDE);
            return power;
        }
        return null;
    }
}
