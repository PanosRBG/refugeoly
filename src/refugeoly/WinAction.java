package refugeoly;

public class WinAction implements Action {
    @Override public void act(Refugee refugee) {
        refugee.setWinner(true);
    }
    
    @Override public String toString() {
        return "WinAction";
    }
}
