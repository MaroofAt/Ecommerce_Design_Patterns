package core.orders;

import core.orders.payment.PaymentException;
import core.orders.payment.PaymentStrategy;

public interface OrderState {
    void pay(PaymentStrategy payment_strategy, double amount) throws OrderStateException, PaymentException;
    void ship() throws OrderStateException;
    void deliver() throws OrderStateException;
    void cancel() throws OrderStateException;
}
