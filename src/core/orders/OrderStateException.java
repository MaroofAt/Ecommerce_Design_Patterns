package core.orders;

public class OrderStateException extends Exception{
    public OrderStateException(OrderState current_state, OrderState target_state) {
        super(
                "can't apply transaction from "
                        + current_state.getClass().getSimpleName() +
                        " to " + target_state.getClass().getSimpleName()
        );
    }
}
