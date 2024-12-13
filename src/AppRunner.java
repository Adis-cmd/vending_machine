import PayMethodd.CoinAcceptor;
import PayMethodd.MoneyRes;
import PayMethodd.PayCard;
import PayMethodd.PaymentMethod;
import enums.ActionLetter;
import model.*;
import util.UniversalArray;
import util.UniversalArrayImpl;

import java.util.Scanner;

public class AppRunner {

    private final UniversalArray<Product> products = new UniversalArrayImpl<>();
    private PaymentMethod pay;


    private static boolean isExit = false;

    private AppRunner() {
        products.addAll(new Product[]{
                new Water(ActionLetter.B, 20),
                new CocaCola(ActionLetter.C, 50),
                new Soda(ActionLetter.D, 30),
                new Snickers(ActionLetter.E, 80),
                new Mars(ActionLetter.F, 80),
                new Pistachios(ActionLetter.G, 130)
        });
        this.pay = new CoinAcceptor(100);
    }

    public static void run() {
        AppRunner app = new AppRunner();
        while (!isExit) {
            app.startSimulation();
        }
    }

    private void startSimulation() {
        print("В автомате доступны:");
        showProducts(products);

        print("Баланс " +  pay.getAmount());

        UniversalArray<Product> allowProducts = new UniversalArrayImpl<>();
        allowProducts.addAll(getAllowedProducts().toArray());
        choosePaymentAcceptor();
        insertMoney();
        chooseAction(allowProducts);

    }


    private void choosePaymentAcceptor() {
        Scanner sc = new Scanner(System.in);
        print("Выберите тип приемника: ");
        print("1 - Монетоприемник");
        print("2 - Денежный приемник");
        print("3 - Оплата картой");
        String choice = sc.nextLine().trim();
        switch (choice) {
            case "1":
            pay = new CoinAcceptor(0);
            break;
            case "2":
            pay = new MoneyRes();
                break;
            case "3":
            pay = new PayCard();
                break;
            default:
                System.out.println("Введите правильно значение от 1 до 3 и не строку");
                choosePaymentAcceptor();
        }
    }

    private void insertMoney() {
        print("Пожалуйста, вставьте деньги (введите сумму).Введите h чтобы переместиться к покупке");
        while (true) {
            String input = fromConsole();
            if (input.equalsIgnoreCase("h")) {
                break;
            }
            try {
                int money = Integer.parseInt(input);
                if (money > 0) {
                    if (pay.getAmount() + money >= 0) {
                        pay.addMoney(money);
                        print("Вы вставили " + money + " рублей. Текущий баланс: " + pay.getAmount() + " чтобы выйти введи h");
                    } else {
                        print("Невозможно выполнить операцию: недостаточно средств.");
                    }
                } else {
                    print("Введите положительное число денег.");
                }
            } catch (NumberFormatException e) {
                print("Недопустимый ввод. Пожалуйста, введите число или 'h' для завершения.");
            }
        }
    }



    private void payWithCard(int amountToPay) {
        if (pay instanceof PayCard) {
            boolean paymentSuccess = ((PayCard) pay).payWithCard(amountToPay);
            if (paymentSuccess) {
                print("Оплата прошла успешно с карты.");
                pay.setAmount(pay.getAmount() - amountToPay);
            } else {
                print("Ошибка при оплате картой.");
            }
        }
    }


    private UniversalArray<Product> getAllowedProducts() {
        UniversalArray<Product> allowProducts = new UniversalArrayImpl<>();
        for (int i = 0; i < products.size(); i++) {
            if (pay.getAmount() >= products.get(i).getPrice()) {
                allowProducts.add(products.get(i));
            }
        }
        return allowProducts;
    }

    private void chooseAction(UniversalArray<Product> products) {
        showActions(products);
        print(" h - Выйти");
        String action = fromConsole().substring(0, 1);
        try {
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getActionLetter().equals(ActionLetter.valueOf(action.toUpperCase()))) {
                    Product selectedProduct = products.get(i);
                    int productPrice = selectedProduct.getPrice();

                    if (pay.getAmount() >= productPrice) {
                        pay.setAmount(pay.getAmount() - productPrice);
                        print("Вы купили " + selectedProduct.getName());
                        print("Оставшийся баланс: " + pay.getAmount());
                    } else if (pay instanceof PayCard) {
                        payWithCard(productPrice);  // Пытаемся оплатить картой
                    } else {
                        print("Недостаточно средств для покупки " + selectedProduct.getName());
                    }
                    break;
                }
            }
        } catch (IllegalArgumentException e) {
            print("Недопустимая буква. Попрбуйте еще раз.");
            chooseAction(products);
        }


    }

    private void showActions(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(String.format(" %s - %s", products.get(i).getActionLetter().getValue(), products.get(i).getName()));
        }
    }

    private String fromConsole() {
        return new Scanner(System.in).nextLine();
    }

    private void showProducts(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(products.get(i).toString());
        }
    }

    private void print(String msg) {
        System.out.println(msg);
    }
}
