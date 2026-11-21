package core;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import core.inventory.InventoryService;
import core.notification.NotificationService;
import core.orders.OrderContext;
import core.orders.OrderStateException;
import core.orders.decorators.OrderContextCachingDecorator;
import core.orders.decorators.OrderContextLoggingDecorator;
import core.orders.payment.*;

public class CheckoutFacade{

    public void placeOrder(int paymentMethod, double orderPrice){
        try {
            System.out.println("Starting Placing Order...");
            OrderContext order;
            PaymentStrategy paymentStrategy = mapPaymentStrategy(paymentMethod);
            System.out.println("----");
            System.out.println("Initializing Payment Method...");
            System.out.println("----");
            System.out.println("Payment Method = " + paymentStrategy.getClass().getSimpleName());
            System.out.println("Order Price = " + orderPrice);
            System.out.println("----");
            order = new OrderContextLoggingDecorator(
                        new OrderContextCachingDecorator(
                                new OrderContext(paymentStrategy, orderPrice)
                        )
                    );
            System.out.println("- Order Initialized -");
            System.out.println("Order State: " + order.getState().getClass().getSimpleName());
            System.out.println("----");
            System.out.println("Checking Inventory...");
            new InventoryService().call();
            System.out.println("- Inventory Checked -");
            System.out.println("----");
            System.out.println("Paying Order...");
            order.pay();
            System.out.println("- Payment Succeed -");
            System.out.println("Order State: " + order.getState().getClass().getSimpleName());
            System.out.println("----");
            System.out.println("Shipping...");
            order.ship();
            System.out.println("- Shipping Succeed -");
            System.out.println("Order State: " + order.getState().getClass().getSimpleName());
            System.out.println("----");
            System.out.println("Delivering...");
            order.deliver();
            System.out.println("- Delivering Succeed -");
            System.out.println("Order State: " + order.getState().getClass().getSimpleName());
            System.out.println("----");
            System.out.println("Notifying...");
            new NotificationService().call();
            System.out.println("----");
            System.out.println("---------------------------------------------");
            System.out.println("- Placing Order Process Ended Successfully -");
            System.out.println("---------------------------------------------");
        }catch (OrderStateException e){
            System.out.println("---------------------------------------------");
            System.out.println("- Unavailable Transition Between Order States: " + e.getMessage() + " -");
            System.out.println("---------------------------------------------");
        }catch (PaymentException pe){
            System.out.println("---------------------------------------------");
            System.out.println("- Payment Method didn't Succeed!: " + pe.getMessage() + " -");
            System.out.println("---------------------------------------------");
        }
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
