package refugeoly;

public class GiveLifeAction implements Action {
    @Override public void act(Refugee refugee) {
        refugee.extraLife();
    }
    
    @Override public String toString() {
        return "GiveLifeAction";
    }
}
