package refugeoly;

import java.util.Scanner;

public class ChoiceAction implements Action {
    ReceiverEntity mafia;
    
    public ChoiceAction(ReceiverEntity mafia) {
        this.mafia = mafia;
    }
    
    @Override public void act(Refugee refugee) {
        Scanner in = new Scanner(System.in);
        
        char option;
        do {
            System.out.print("Select the following action[A/B]: ");
            option = in.nextLine().charAt(0);
        } while(option != 'A' && option != 'B');
        
        if(option == 'A') {
            PayMoneyAction action1 = new PayMoneyAction(1500, mafia);
            RollDiceAction action2 = new RollDiceAction();
            
            action1.act(refugee);
            action2.act(refugee);
        }
        else {
            StayAction action1 = new StayAction(2);
            
            action1.act(refugee);
        }
    }
}
