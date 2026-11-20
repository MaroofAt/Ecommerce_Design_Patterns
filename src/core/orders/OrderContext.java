package core.orders;

import core.orders.payment.PaymentException;
import core.orders.payment.PaymentStrategy;

public class OrderContext {
    protected OrderState state;
    protected PaymentStrategy payment_strategy;
    protected double price;

    public OrderContext(OrderState state, PaymentStrategy payment_strategy, double price) {
        this.state = state;
        this.payment_strategy = payment_strategy;
        this.price = price;
    }
    public OrderContext(PaymentStrategy payment_strategy, double price) {
        this.state = new PendingState(this);
        this.payment_strategy = payment_strategy;
        this.price = price;
    }

    public double getPrice() {return price;}
    public OrderState getState() {return state;}
    public void changeState(OrderState state) {this.state = state;}

    public PaymentStrategy getPayment_strategy() {return payment_strategy;}
    public void setPayment_strategy(PaymentStrategy payment_strategy) {this.payment_strategy = payment_strategy;}

    public void pay() throws OrderStateException, PaymentException{
        this.state.pay(this.payment_strategy, this.price);
    }
    public void ship() throws OrderStateException{
        this.state.ship();
    }
    public void deliver() throws OrderStateException{
        this.state.deliver();
    }
    public void cancel() throws OrderStateException{
        this.state.cancel();
    }

}
