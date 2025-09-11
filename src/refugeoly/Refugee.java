package refugeoly;

public class Refugee extends ReceiverEntity{
    private Board board;
    private int square;
    private int lives;
    private int stay;
    private boolean winner;
    private double expenses;
    
    public Refugee(String name, Board board) {
        super(name, 10000);
        this.board = board;
        this.square = 0;
        this.lives = 0;
        this.stay = 0;
        this.winner = false;
        this.expenses = 0;
    }
    
    //Setters
    public void setSquare(int square) {
        this.square = square;
    }
    
    public void setStay(int stay) {
        this.stay = stay;
    }
    
    public void setWinner(boolean winner) {
        this.winner = winner;
    }
    
    public void setExpenses(double expenses) {
        this.expenses = expenses;
    }
    
    //Getters
    public int getSquare() {
        return square;
    }
    
    public int getLives() {
        return lives;
    }
    
    public int getStay() {
        return stay;
    }
    
    public boolean getWinner() {
        return winner;
    }
    
    public double getExpenses() {
        return expenses;
    }
    
    //Gameplay methods
    public void extraLife() {
        lives += 1;
    }
    
    public void loseLife() {
        if(lives < 0) {
            System.out.println("You lost");
        }
        lives -= 1;
    }
    
    public double giveMoney(double money) {
        if(getMoney() < money) {
            double temp = getMoney();
            setMoney(0);
            loseLife();
            return temp;
        }
        
        setMoney(getMoney() - money);
        this.expenses += money;
        
        return money;
    }
    
    public void moveTo(int roll) {
        square += roll;
    }
    
    @Override public String toString() {
        return "[Name]: " + getName() + '\n' + "[Money]: " + getMoney() + '\n' +  "[Expenses]: " + expenses + '\n' + "[Square]: " + square;
    }
}
