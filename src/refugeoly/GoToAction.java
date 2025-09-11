package refugeoly;

public class GoToAction implements Action {
    private int square;
    
    public GoToAction(int square) {
        this.square = square;
    }
    @Override public void act(Refugee refugee) {
        refugee.setSquare(square);
    }
    
    @Override public String toString() {
        return "GoToAction";
    }
}
