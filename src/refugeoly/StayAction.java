package refugeoly;

public class StayAction implements Action {
    private int turns;
    
    public StayAction(int turns) {
        this.turns = turns;
    }
    
    @Override public void act(Refugee refugee) {
        refugee.setStay(turns);
    }
    
    @Override public String toString() {
        return "StayAction";
    }
}
