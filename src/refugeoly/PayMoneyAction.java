package refugeoly;

public class PayMoneyAction implements Action {
    private double money;
    private MoneyReceiver mafia = null;
    
    public PayMoneyAction(double money) {
        this.money = money;
    }
    
    public PayMoneyAction(double money, MoneyReceiver mafia) {
        this.money = money;
        this.mafia = mafia;
    }
    
    @Override public void act(Refugee refugee) {
        if(mafia != null) {
            mafia.receiveMoney(refugee.giveMoney(money));
        }
        else {
            refugee.giveMoney(money);
        }
    }
    
    @Override public String toString() {
        return "PayMoneyAction";
    }
}
