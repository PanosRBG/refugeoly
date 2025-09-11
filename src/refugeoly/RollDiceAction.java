package refugeoly;

import java.util.Random;
import java.util.Scanner;

public class RollDiceAction implements Action {
    @Override public void act(Refugee refugee) {
        Scanner in = new Scanner(System.in);
        Random random = new Random();
        
        System.out.print("Press enter to roll. "); in.nextLine();
        int diceRoll = 1 + random.nextInt(6);
        System.out.println("You rolled a " + diceRoll + ".");
        
        if((diceRoll + refugee.getSquare()) > 39) {
            int rollNeeded = 39 - refugee.getSquare();
            int extra = diceRoll - rollNeeded;
            int square = 39 - extra;
            refugee.setSquare(square);
        }
        else {
            refugee.moveTo(diceRoll);
        }
    }
    
    @Override public String toString() {
        return "RollDiceAction";
    }
}
