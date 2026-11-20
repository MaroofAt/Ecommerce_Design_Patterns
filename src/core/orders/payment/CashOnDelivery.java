package core.orders.payment;

public class CashOnDelivery implements PaymentStrategy{
    @Override
    public void pay(double amount) {
        System.out.println("will be paid on delivery");
    }
}
