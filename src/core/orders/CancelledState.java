package core.orders;

import core.orders.payment.PaymentException;
import core.orders.payment.PaymentStrategy;

public class CancelledState implements OrderState{
    OrderContext context;

    public CancelledState(OrderContext context) {
        this.context = context;
    }

    @Override
    public void pay(PaymentStrategy payment_strategy, double amount) throws OrderStateException{
        throw new OrderStateException(this, new PaidState(context));
    }

    @Override
    public void ship() throws OrderStateException{
        throw new OrderStateException(this, new ShippedState(context));
    }

    @Override
    public void deliver() throws OrderStateException{
        throw new OrderStateException(this, new DeliveredState(context));
    }

    @Override
    public void cancel() throws OrderStateException{
        throw new OrderStateException(this, this);
    }
}
