package core.orders;

import core.orders.payment.PaymentException;
import core.orders.payment.PaymentStrategy;

public class PendingState implements OrderState{
    OrderContext context;

    public PendingState(OrderContext context) {
        this.context = context;
    }

    @Override
    public void pay(PaymentStrategy payment_strategy, double amount) throws PaymentException{
        payment_strategy.pay(amount);
        this.context.changeState(new PaidState(context));
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
    public void cancel() {
        this.context.changeState(new CancelledState(context));
    }
}
