package danielgilbert.snake;

public class PowerUpFactory {

    public PowerUp getPowerUp(String powerUpCode, int numBlocksHigh, int NUM_BLOCKS_WIDE) {
        if (powerUpCode == "Gold") {
            PowerUp power;
            power = new GoldPowerUp(NUM_BLOCKS_WIDE / 2, numBlocksHigh - 5);
            return power;
        }
        else if (powerUpCode == "Green") {
            PowerUp power;
            power = new GreenPowerUp(NUM_BLOCKS_WIDE / 2, 5);
            return power;
        }
        return null;
    }
}
