package refugeoly;

public class GiverEntity implements MoneyGiver{
    private final String name;
    private double money;
    
    public GiverEntity(String name, double money) {
        this.name = name;
        this.money = money;
    }
    
    public void setMoney(double money) {
        this.money = money;
    }
    
    public String getName() {
        return name;
    }
    
    public double getMoney() {
        return money;
    }
    
    @Override public void giveMoney(double money) throws NoMoneyException{
        if(this.money <= 0) {
            throw new NoMoneyException("No money to give.");
        }
        
        this.money -= money;
    }
    
    public String toString() {
        return "[Name]: " + name + " [Money]: " + money;
    }
}
