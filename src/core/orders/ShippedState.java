package core.orders;

import core.orders.payment.PaymentException;
import core.orders.payment.PaymentStrategy;

public class ShippedState implements OrderState{
    OrderContext context;

    public ShippedState(OrderContext context) {
        this.context = context;
    }
    @Override
    public void pay(PaymentStrategy payment_strategy, double amount) throws OrderStateException{
        throw new OrderStateException(this, new PaidState(context));
    }

    @Override
    public void ship() throws OrderStateException{
        throw new OrderStateException(this, this);
    }

    @Override
    public void deliver() {
        this.context.changeState(new DeliveredState(context));
    }

    @Override
    public void cancel() throws OrderStateException{
        throw new OrderStateException(this, new CancelledState(context));
    }
}
