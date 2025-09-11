package refugeoly;

public interface Action {
    void act(Refugee refugee); // Abstract method to implement in every Action class.
    String toString(); //Method for testing only(?).
}
