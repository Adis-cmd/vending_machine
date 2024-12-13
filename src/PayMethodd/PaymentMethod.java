package PayMethodd;

public class PaymentMethod {
    private int amount;

    public  PaymentMethod() {
        this.amount = 0;
    }

    public void addMoney(int money) {
        if (money > 0) {
            this.amount += money;
        }

    }
    public int getAmount() {
        return this.amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
}