package PayMethodd;

import java.util.Scanner;

public class PayCard implements PaymentMethod {
    private int amount;

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
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите номер карточки (16 цифр):");
        String numbersCardStr = sc.nextLine();

        if (numbersCardStr.length() == 16) {
            System.out.println("Введите 3-значный одноразовый код для оплаты:");

            String numbersCardCode = sc.nextLine();

            if (numbersCardCode.length() == 3) {
                System.out.println("Оплата прошла успешно.");

                if (this.amount >= amountToPay) {
                    this.amount -= amountToPay;
                    return true;
                } else {
                    System.out.println("Ошибка: недостаточно средств на карте.");
                    return false;
                }
            } else {
                System.out.println("Ошибка: введите 3-значный одноразовый код.");
            }
        } else {
            System.out.println("Ошибка: введите корректный номер карты (16 цифр).");
        }

        return false;
    }
}
