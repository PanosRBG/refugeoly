package refugeoly;

public class DieAction implements Action {
    @Override public void act(Refugee refugee) {
        refugee.loseLife();
        if(refugee.getLives() < 0) {
            System.out.println("You lost. You are out of the game.");
        }
    }
    
    @Override public String toString() {
        return "DieAction";
    }
}
