package refugeoly;

import java.util.ArrayList;

public class Board {
    private ArrayList<Square> squares = new ArrayList<>();
    
    public void addSquare(Square square) {
        squares.add(square);
    }
    
    public Square getSquare(int index) {
        return squares.get(index);
    }
    
    //Testing purposes
    public void printSquares() {
        for(Square sq: squares) {
            System.out.println(sq);
        }
    }
}
