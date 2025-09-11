package refugeoly;

public class ReceiverEntity implements MoneyReceiver{
    private final String name;
    private double money;
    
    public ReceiverEntity(String name, double money) {
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
    
    @Override public void receiveMoney(double money) {
        this.money += money;
    }
    
    public String toString() {
        return "[Name]: " + name + " [Money]: " + money;
    }
}
