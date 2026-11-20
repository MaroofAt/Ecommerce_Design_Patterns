package core.orders;

import core.orders.payment.PaymentException;
import core.orders.payment.PaymentStrategy;
import core.shipping.ShippingService;

public class PaidState implements OrderState{
    OrderContext context;

    public PaidState(OrderContext context) {
        this.context = context;
    }
    @Override
    public void pay(PaymentStrategy payment_strategy, double amount) throws OrderStateException{
        throw new OrderStateException(this, this);
    }

    @Override
    public void ship() {
        new ShippingService().call();
        this.context.changeState(new ShippedState(context));
    }

    @Override
    public void deliver() throws OrderStateException{
        throw new OrderStateException(this, new DeliveredState(context));
    }

    @Override
    public void cancel() { // it is okay to cancel after payment but before shipping
        this.context.changeState(new CancelledState(context));
    }
}
