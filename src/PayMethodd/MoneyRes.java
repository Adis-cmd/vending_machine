package PayMethodd;

public class MoneyRes implements PaymentMethod {
    private int amount;

    public MoneyRes() {
        this.amount = 0;
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