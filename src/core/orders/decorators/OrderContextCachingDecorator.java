package core.orders.decorators;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import core.orders.OrderContext;
import core.orders.OrderState;
import core.orders.OrderStateException;
import core.orders.payment.PaymentException;
import core.orders.payment.PaymentStrategy;

import java.util.HashMap;
import java.util.Map;

public class OrderContextCachingDecorator extends OrderContextDecorator{
    private Map<String, Object> cacheMap;
    public OrderContextCachingDecorator(OrderContext wrappee) {
        super(wrappee);
        cacheMap = new HashMap<>();
    }

    @Override
    public OrderState getState() {
        String cacheKey = "getState";
        if(cacheMap.containsKey(cacheKey)){
            System.out.println("\t<From Cache: "+cacheKey+">\t");
            return (OrderState) cacheMap.get(cacheKey);
        }
        OrderState response = super.getState();
        cacheMap.put(cacheKey, response);
        return response;
    }

    @Override
    public void changeState(OrderState state) {
        String cacheKey = "getState"; // to remove it
        cacheMap.remove(cacheKey);
        super.changeState(state);
    }

    @Override
    public PaymentStrategy getPayment_strategy() {
        String cacheKey = "getPayment_strategy";
        if(cacheMap.containsKey(cacheKey)){
            System.out.println("\t<From Cache: "+cacheKey+">\t");
            return (PaymentStrategy) cacheMap.get(cacheKey);
        }
        PaymentStrategy response = super.getPayment_strategy();
        cacheMap.put(cacheKey, response);
        return response;
    }

    @Override
    public void setPayment_strategy(PaymentStrategy payment_strategy) {
        String cacheKey = "getPayment_strategy"; // to remove it
        cacheMap.remove(cacheKey);
        super.setPayment_strategy(payment_strategy);
    }

    @Override
    public void pay() throws OrderStateException, PaymentException {
        String cacheKey = "getState"; // to remove it
        cacheMap.remove(cacheKey);
        super.pay();
    }

    @Override
    public void ship() throws OrderStateException {
        String cacheKey = "getState"; // to remove it
        cacheMap.remove(cacheKey);
        super.ship();
    }

    @Override
    public void deliver() throws OrderStateException {
        String cacheKey = "getState"; // to remove it
        cacheMap.remove(cacheKey);
        super.deliver();
    }

    @Override
    public void cancel() throws OrderStateException {
        String cacheKey = "getState"; // to remove it
        cacheMap.remove(cacheKey);
        super.cancel();
    }

    @Override
    public int getId() {
        String cacheKey = "getId";
        if(cacheMap.containsKey(cacheKey)){
            System.out.println("\t<From Cache: "+cacheKey+">\t");
            return (int) cacheMap.get(cacheKey);
        }
        int response = super.getId();
        cacheMap.put(cacheKey, response);
        return response;
    }

    @Override
    public double getPrice() {
        String cacheKey = "getPrice";
        if(cacheMap.containsKey(cacheKey)){
            System.out.println("\t<From Cache: "+cacheKey+">\t");
            return (double) cacheMap.get(cacheKey);
        }
        double response = super.getPrice();
        cacheMap.put(cacheKey, response);
        return response;
    }
}
