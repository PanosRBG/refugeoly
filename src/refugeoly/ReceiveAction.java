package refugeoly;


public class ReceiveAction implements Action {
    private double money;
    private GiverEntity ngo;
    
    public ReceiveAction(double money, GiverEntity ngo) {
        this.money = money;
        this.ngo = ngo;
    }
    
    @Override public void act(Refugee refugee) {
        try {
            if(ngo.getMoney() < money) {
                ngo.giveMoney(ngo.getMoney());
                refugee.receiveMoney(ngo.getMoney());
            }
            else {
                ngo.giveMoney(money);
                refugee.receiveMoney(money);
            }
        } catch (NoMoneyException e) {
            System.out.println(e);
        }
    }
}
