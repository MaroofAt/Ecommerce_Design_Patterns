package core.orders;

import core.orders.payment.PaymentException;
import core.orders.payment.PaymentStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderContext {
    private static Map<Integer, OrderContext> OrderMap = new HashMap<>();
//    private static ArrayList<OrderContext> OrderList;
    private static int id_count = 0;
    protected int id;
    protected OrderState state;
    protected PaymentStrategy payment_strategy;
    protected double price;

    public OrderContext(OrderState state, PaymentStrategy payment_strategy, double price) {
        id_count++;
        this.id = id_count;

        this.state = state;
        this.payment_strategy = payment_strategy;
        this.price = price;

        OrderMap.put(this.id, this);
    }
    public OrderContext(PaymentStrategy payment_strategy, double price) {
        id_count++;
        this.id = id_count;

        this.state = new PendingState(this);
        this.payment_strategy = payment_strategy;
        this.price = price;

        OrderMap.put(this.id, this);
    }

    public int getId() {return this.id;}
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


    public static Map<Integer, OrderContext> getOrderMap(){
        return OrderMap;
    }
    public static OrderContext getOrderById(int id){
        if(!OrderMap.containsKey(id)) return null;
        return OrderMap.get(id);
    }
    public static ArrayList<OrderContext> getAllOrdersInDB(){
        return (ArrayList<OrderContext>) OrderMap.values();
    }


    @Override
    public String toString() {
        return "OrderContext{" +
                "id=" + id +
                ", state=" + state.getClass().getSimpleName() +
                ", payment_strategy=" + payment_strategy.getClass().getSimpleName() +
                ", price=" + price +
                '}';
    }
}
