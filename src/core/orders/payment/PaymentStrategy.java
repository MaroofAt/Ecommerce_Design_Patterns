package core.orders.payment;

public interface PaymentStrategy {
    void pay(double amount) throws PaymentException;
}
