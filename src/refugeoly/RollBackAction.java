package refugeoly;

import java.util.Random;

public class RollBackAction implements Action{
    @Override public void act(Refugee refugee) {
        Random random = new Random();
        int rollDice = 1 + random.nextInt(6);
        refugee.setSquare(refugee.getSquare() - rollDice);
    }
}