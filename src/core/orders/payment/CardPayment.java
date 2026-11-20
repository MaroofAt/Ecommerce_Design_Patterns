package core.orders.payment;

public class CardPayment implements PaymentStrategy{
    @Override
    public void pay(double amount) {
        System.out.println("paid by card");
    }
}
