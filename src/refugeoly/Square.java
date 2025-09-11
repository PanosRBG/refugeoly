package refugeoly;

import java.util.ArrayList;

public class Square {
    private final int number;
    private final String text;
    private final String description;
    private Board board;
    private ArrayList<Action> actions = new ArrayList<>();
    
    public Square(int number, String text, String description, Board board) {
        this.number = number;
        this.text = text;
        this.description = description;
        this.board = board;
    }
    
    public void addAction(Action action) {
        actions.add(action);
    }
    
    public void act(Refugee refugee) {
        for(int i = 0; i < actions.size(); i++) {
            actions.get(i).act(refugee);
        }
    }
    
    public String toString() {
        return "\n[" + number + "]\n" + text + '\n' + description;
    }
}
