package core.orders.payment;

public class PaymentException extends Exception{
    public PaymentException() {
        super("Payment Failed");
    }
}
