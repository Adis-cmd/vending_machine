package PayMethodd;

public class CoinAcceptor implements PaymentMethod{
    private int amount;

    public CoinAcceptor(int index) {
        this.amount = index;
    }

    @Override
    public void addMoney(int money) {
        this.amount += money;
    }

    @Override
    public int getAmount() {
        return this.amount;
    }

    @Override
    public void setAmount(int amount) {
        this.amount = amount;
    }
}
