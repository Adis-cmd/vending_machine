package PayMethodd;

public class PayCard implements PaymentMethod {
    private int amount;

    public PayCard(int amount) {
        this.amount = amount;
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

    public boolean payWithCard(int amountToPay) {
        if (amountToPay > 0) {
            this.amount += amountToPay;
            return true;
        }
        return false;
    }
}
