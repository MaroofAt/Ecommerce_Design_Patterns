package core;


import core.inventory.InventoryService;
import core.notification.NotificationService;
import core.orders.OrderContext;
import core.orders.OrderStateException;
import core.orders.decorators.OrderContextCachingDecorator;
import core.orders.decorators.OrderContextLoggingDecorator;
import core.orders.payment.*;

import java.util.ArrayList;

public class FullFacade {
    protected OrderContext order = null;
    protected PaymentStrategy paymentStrategy = null;
    public void placeOrder(int paymentMethod, double orderPrice){
        setPaymentMethod(paymentMethod);
        this.order = new OrderContextLoggingDecorator(
                        new OrderContextCachingDecorator(
                                new OrderContext(this.paymentStrategy, orderPrice)
                        )
                    );
    }
    public void checkInventory(){
        new InventoryService().call();
    }
    public void doNotify(){
        new NotificationService().call();
    }
    public void setPaymentMethod(int paymentMethod){
        if(!(paymentMethod > 0 && paymentMethod < getPaymentMethods().length))
            paymentMethod = 0;
        this.paymentStrategy = mapPaymentStrategy(paymentMethod);
        if(this.order != null) this.order.setPayment_strategy(this.paymentStrategy);
    }

    public void payOrder()throws OrderStateException, PaymentException, FacadeException {
        if(this.order == null) throw new FacadeException("Order still not initialized! : Create Order Before payOrder()");
        this.order.pay();
    }
    public void shipOrder()throws OrderStateException, FacadeException {
        if(this.order == null) throw new FacadeException("Order still not initialized! : Create Order Before shipOrder()");
        this.order.ship();
    }
    public void deliverOrder()throws OrderStateException, FacadeException {
        if(this.order == null) throw new FacadeException("Order still not initialized! : Create Order Before deliverOrder()");
        this.order.deliver();
    }
    public void cancelOrder()throws OrderStateException, FacadeException {
        if(this.order == null) throw new FacadeException("Order still not initialized! : Create Order Before cancelOrder()");
        this.order.cancel();
    }


    public String getOrderState() throws FacadeException {
        if(this.order == null) throw new FacadeException("Order still not initialized! : Create Order Before getting its State!");
        return this.order.getState().getClass().getSimpleName();
    }

    public int getOrderId(){
        if(this.order == null) return -1;
        return this.order.getId();
    }
    public void ReplaceOrder(int id){
        OrderContext temp = OrderContext.getOrderById(id);
        if(temp == null) return;
        this.order = new OrderContextLoggingDecorator(
                new OrderContextCachingDecorator(temp)
        );
    }
    public String getOrderAsString(){
        if(this.order == null) return null;
        return this.order.toString();
    }
    public static String getOrderAsStringById(int id){
        OrderContext temp = OrderContext.getOrderById(id);
        if(temp == null) return null;
        return temp.toString();
    }
    public static ArrayList<OrderContext> getAllOrdersInDB(){
        return OrderContext.getAllOrdersInDB();
    }


    // tools
    public String[] getPaymentMethods(){
        String[] s = new String[3];
        s[0] = "by card";
        s[1] = "cash on delivery";
        s[2] = "pay pal";
        return s;
    }
    protected PaymentStrategy mapPaymentStrategy(int i){
        switch(i){
            case 0:
                return new CardPayment();
            case 1:
                return new CashOnDelivery();
            case 2:
                return new PayPalAdapter();
        }
        return new CardPayment();
    }
}