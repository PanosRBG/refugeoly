package refugeoly;

import java.util.Scanner;

public class Refugeoly {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Game game;
        int selection;
        boolean exit = false;
        
        do {
            System.out.println("======MENU=======");
            System.out.println("1)New game.");
            System.out.println("2)Continue.");
            System.out.println("3)Exit.");
            System.out.print(">> ");
            selection = in.nextInt(); in.nextLine();
                
            switch(selection) {
                case 1 -> {
                    game = new Game();
                    
                    game.boardCreation();
                    game.playerCreation();
                    game.playGame();
                }
                case 2 -> {
                    game = new Game();
                    
                    game.boardCreation();
                    game.loadGame();
                    game.playGame();
                }
                case 3 -> {
                    exit = true;
                }
            }
        } while(!exit);
    }
}
