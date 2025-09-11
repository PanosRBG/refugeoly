package refugeoly;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class Game {
    private ArrayList<Refugee> players = new ArrayList<>();
    private ReceiverEntity mafia; 
    private GiverEntity ngo;
    private Board board;
    private int turn = 0;
    private int playerTurn = 0;
    
    //Method for the creation of the board and individual squares.
    public void boardCreation() {
        board = new Board();
        mafia = new ReceiverEntity("Mafia Bank", 0);
        ngo = new GiverEntity("NGO Bank", 10000);
        BufferedReader reader = null;
        
        //Error handling for FileIO.
        try {
            reader = new BufferedReader(new FileReader("refugeoly-squares.txt"));
            
            //Read file while end of the file is reached.
            do {
                //Passing file content to variables.
                String number = reader.readLine();
                String text = reader.readLine();
                String description = reader.readLine();
                
                //Passing text file variables to Square attributes.
                Square square = new Square(Integer.parseInt(number), text, description, board);
                
                //Add actions automatically based on content of text.
                if(text.contains("Pay") || text.contains("lose")) {
                    String numbers = text.substring(firstNumberIndex(text), text.indexOf('$'));
                    
                    if(containsIgnoreCase(text, "Mafia")) {
                        square.addAction(new PayMoneyAction(Double.parseDouble(numbers), mafia));
                    }
                    else {
                        square.addAction(new PayMoneyAction(Double.parseDouble(numbers)));
                    }
                }
                
                if(text.contains("receive")) {
                    square.addAction(new ReceiveAction(1000, ngo));
                }
                
                if(text.contains("Option")) {
                    square.addAction(new ChoiceAction(mafia));
                    board.addSquare(square);
                    continue;
                }
                
                if(containsIgnoreCase(text, "Back") || text.contains("Jump")) {
                    int leftBound = text.lastIndexOf('('), rightBound = text.lastIndexOf(')');

                    if(leftBound == -1 || rightBound == -1) {
                        square.addAction(new RollBackAction());
                        board.addSquare(square);
                        continue;
                    }
                    else {
                        String nums = ""; 
                        for(int i = leftBound+1; i < rightBound; i++) {
                            nums += text.charAt(i);
                        }

                        square.addAction(new GoToAction(Integer.parseInt(nums)));
                    }
                }
                                
                if(containsIgnoreCase(text, "Roll")) {
                    square.addAction(new RollDiceAction());
                }
                
                if(text.contains("extra life")) {
                    square.addAction(new GiveLifeAction());
                }
                
                if(containsIgnoreCase(text, "Stay")) {
                    if(containsIgnoreCase(text, "turn")) {
                        square.addAction(new StayAction(1));
                    }
                    else {
                        square.addAction(new StayAction(2));
                    }
                }
                
                if(text.contains("win")) {
                    square.addAction(new WinAction());
                }
                
                if(text.contains("dead")) {
                    square.addAction(new DieAction());
                }
                
                //Add square to the Board's squares ArrayList.
                board.addSquare(square);
            } while(reader.readLine() != null);
        } 
        catch (FileNotFoundException ex) {
            System.out.println("File not found.");
        } 
        catch (IOException ex) {
            System.out.println("Could not read file.");
        } 
        finally {
            try {
                reader.close();
            } 
            catch (IOException ex) {
                System.out.println("Could not close file.");
            }
        }
    }
    
    // Method for the creation of players.
    public void playerCreation() {
        Scanner in = new Scanner(System.in);
        
        //User input for the amount of players.
        int n;
        do {
            System.out.print("Enter the amount of players[1-4]: ");
            n = in.nextInt(); in.nextLine();
        } while(n < 1 || n > 4);
        
        //Create players and pass them to the ArrayList.
        for(int i = 0; i < n; i++) {
            System.out.print("Enter name for refugee[" + (i+1) + "]: ");
            String name = in.nextLine();
            
            players.add(new Refugee(name, board));
        }
    }

    public void playGame() {
        boolean exit = false;
        do {
            Scanner in = new Scanner(System.in);
            Random random = new Random();
            boolean flag = false;
            int option;
            
            turn++;
            for(int i = playerTurn; i < players.size(); i++) {        
                
                if(players.get(i).getStay() != 0) {
                    players.get(i).setStay((players.get(i).getStay()) - 1);
                    continue;
                }

                do {
                    System.out.println("\nTurn: " + turn);
                    System.out.println("============[Banks]===========");
                    System.out.println(ngo); 
                    System.out.println(mafia);
                    System.out.println("==========[Player " + (i+1) + "]==========");
                    if (players.get(i).getLives() > 0) 
                        System.out.println("You have " + players.get(i).getLives() + " extra life.");
                    System.out.println(players.get(i));
                    System.out.println("==============================");
                    System.out.println("1)Roll");
                    System.out.println("2)Square info");
                    System.out.println("3)Save");
                    System.out.println("4)Return to menu");
                    System.out.print(">> ");
                    option = in.nextInt(); in.nextLine();
                } while(option < 1 || option > 4);
                
                switch (option) {
                    case 1 -> { 
                        RollDiceAction roll = new RollDiceAction();
                        roll.act(players.get(i));
                        
                        System.out.print(board.getSquare(players.get(i).getSquare()));
                        in.nextLine();
                        
                        board.getSquare(players.get(i).getSquare()).act(players.get(i));
                        
                        if(players.get(i).getWinner())
                        {
                            System.out.println(players.get(i).getName() + " have won. Congratulations.");
                            exit = true;
                        }
                    }
                    case 2 -> {
                        System.out.println("\n" + board.getSquare(players.get(i).getSquare()));
                        in.nextLine();
                        flag = true;
                    }
                    case 3 -> {
                        saveGame();
                        playerTurn = i;
                        flag = true; 
                    }
                    case 4 -> {
                        exit = true;
                    }
                }
                
                if(exit == true) break;
                
                if(players.get(i).getLives() < 0) players.remove(i);
                
                if(players.isEmpty()) {
                    System.out.println("Game over!\nAll players are out of the game.");
                    exit = true;
                    break;
                }
                
                if(flag) {
                    i--;
                    flag = false;
                }
            }
        } while(!exit);
    }
    
    public void saveGame() {
        File saveFile = new File("save.txt");
        Scanner in = new Scanner(System.in);
        
        if(saveFile.exists()) {
            char option;
            do {
                System.out.print("Do you want to overwrite the current save file?(y/n): ");
                option = in.nextLine().charAt(0);
            } while(option != 'y' && option != 'n');
            
            saveFile.delete();
            try {
                saveFile.createNewFile();
            } 
            catch (IOException e) {
                System.out.print("An exception occured.");
            }
        }
        else {
            try {
                saveFile.createNewFile();
            } 
            catch (IOException ex) {
                System.out.print("An exception occured.");
            }
        }
        
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(saveFile));
            
            writer.write("Turn: " + turn); writer.newLine();
            writer.write("Player: " + playerTurn); writer.newLine();
            
            writer.write(ngo.toString()); writer.newLine();
            writer.write(mafia.toString()); writer.newLine();
            
            for(Refugee player: players) {
                writer.write(player.toString());
                writer.newLine();
                writer.newLine();
            }
            
            writer.close();
        } catch (IOException e) {
            System.out.print("An exception occured.");
        }
        
        System.out.println("Game saved!");
    }
    
    public void loadGame() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("save.txt"));
            
            String savedTurns = reader.readLine();
            this.turn = Integer.parseInt(savedTurns.substring(savedTurns.lastIndexOf(':')+2));
            
            String playerTurn = reader.readLine();
            this.playerTurn = Integer.parseInt(playerTurn.substring(playerTurn.lastIndexOf(':')+2));
            
            String savedNGO = reader.readLine();
            ngo.setMoney(Double.parseDouble(savedNGO.substring(savedNGO.lastIndexOf(':')+2)));
            
            String savedMafia = reader.readLine();
            mafia.setMoney(Double.parseDouble(savedMafia.substring(savedMafia.lastIndexOf(':')+2)));
            
            do {
                String name = reader.readLine();
                Refugee savedPlayer = new Refugee(name.substring(name.lastIndexOf(':')+2), board);
                
                String money = reader.readLine();
                savedPlayer.setMoney(Double.parseDouble(money.substring(money.lastIndexOf(':')+2)));
                
                String expenses = reader.readLine();
                savedPlayer.setExpenses(Double.parseDouble(expenses.substring(expenses.lastIndexOf(':')+2)));
                
                String square = reader.readLine();
                System.out.println(square);
                savedPlayer.setSquare(Integer.parseInt(square.substring(square.lastIndexOf(':')+2)));
                
                players.add(savedPlayer);
            } while(reader.readLine() != null);
        } 
        catch (FileNotFoundException e) {
            System.out.print("File not found.");
        } 
        catch (IOException ex) {
            System.out.print("An exception occured.");
        }
    }
    
    public boolean containsIgnoreCase(String source, String search) {
        source = source.toLowerCase();
        return source.contains(search.toLowerCase());
    }
    
    public int firstNumberIndex(String source) {
        int start = -1;

        for(int i = 0; i < source.length(); i++) {
            if(Character.isDigit(source.charAt(i))) {
                start = i;
                break;
            }
        }
        
        return start;
    }
}
