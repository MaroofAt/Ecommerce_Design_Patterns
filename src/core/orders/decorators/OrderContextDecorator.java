package core.orders.decorators;

import core.orders.OrderContext;
import core.orders.OrderState;
import core.orders.OrderStateException;
import core.orders.payment.PaymentException;
import core.orders.payment.PaymentStrategy;

public class OrderContextDecorator extends OrderContext {
    OrderContext wrappee;

    public OrderContextDecorator(OrderContext wrappee) {
        super(wrappee.getState(), wrappee.getPayment_strategy(), wrappee.getPrice());
        this.wrappee = wrappee;
    }

    @Override
    public OrderState getState() {
        return wrappee.getState();
    }

    @Override
    public void changeState(OrderState state) {
        wrappee.changeState(state);
    }

    @Override
    public PaymentStrategy getPayment_strategy() {
        return wrappee.getPayment_strategy();
    }

    @Override
    public void setPayment_strategy(PaymentStrategy payment_strategy) {
        wrappee.setPayment_strategy(payment_strategy);
    }

    @Override
    public void pay() throws OrderStateException, PaymentException {
        wrappee.pay();
    }

    @Override
    public void ship() throws OrderStateException {
        wrappee.ship();
    }

    @Override
    public void deliver() throws OrderStateException {
        wrappee.deliver();
    }

    @Override
    public void cancel() throws OrderStateException {
        wrappee.cancel();
    }

    @Override
    public int getId() {
        return wrappee.getId();
    }

    @Override
    public double getPrice() {
        return wrappee.getPrice();
    }

    @Override
    public String toString() {
        return wrappee.toString();
    }
}
