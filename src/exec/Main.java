package exec;

import core.CheckoutFacade;
import core.FacadeException;
import core.FullFacade;
import core.orders.OrderContext;
import core.orders.OrderStateException;
import core.orders.PaidState;
import core.orders.payment.CardPayment;
import core.orders.payment.CashOnDelivery;
import core.orders.payment.PaymentException;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        main_task();
//        state_strategy_test();
//        facade_test();
    }


    public static void main_task(){
        FullFacade facade = new FullFacade();
        Scanner cin = new Scanner(System.in);
        int choice = 999999999;
        do {
            System.out.println("-------------------------------");
            do {
                System.out.println("1) Create Order");
                System.out.println("2) Choose Payment Method");
                System.out.println("3) Pay");
                System.out.println("4) Ship");
                System.out.println("5) Deliver");
                System.out.println("6) Cancel");
                System.out.println("7) Show Order State");
                System.out.println("8) Exit");
                System.out.print("Choose: ");
                choice = cin.nextInt();
            } while (choice == 999999999);
            System.out.println();

            switch (choice) {
                case 1:
                    Scanner scan_double = new Scanner(System.in);
                    int payment_method_choice = 9999999;
                    String[] paymentMethods = facade.getPaymentMethods();
                    do {
                        for (int i = 0; i < paymentMethods.length; i++) {
                            System.out.println("[" + i + "] " + paymentMethods[i]);
                        }
                        System.out.print("Choose: ");
                        payment_method_choice = cin.nextInt();
                    } while (payment_method_choice >= paymentMethods.length);
                    System.out.print("Enter Order Price: ");
                    double orderPrice = scan_double.nextDouble();
                    facade.placeOrder(payment_method_choice, orderPrice);
                    System.out.println("Done::Order Created::method::"+paymentMethods[payment_method_choice]+"::price::"+orderPrice);
                    break;
                case 2:
                    int payment_method_choice2 = 9999999;
                    String[] paymentMethods2 = facade.getPaymentMethods();
                    do {
                        for (int i = 0; i < paymentMethods2.length; i++) {
                            System.out.println("[" + i + "] " + paymentMethods2[i]);
                        }
                        System.out.print("Choose: ");
                        payment_method_choice2 = cin.nextInt();
                    } while (payment_method_choice2 >= paymentMethods2.length);
                    facade.setPaymentMethod(payment_method_choice2);
                    System.out.println("Done::Payment Method Changed::"+paymentMethods2[payment_method_choice2]);
                    break;
                case 3:
                    try{
                        facade.payOrder();
                        System.out.println("Done::Order set as Paid");
                    }catch (OrderStateException e){
                        System.out.println("Invalid Order State Transition: " + e.getMessage());
                    }catch (PaymentException pe){
                        System.out.println("Invalid Payment: " + pe.getMessage());
                    }catch (FacadeException fe){
                        System.out.println(fe.getMessage());
                    }
                    break;
                case 4:
                    try{
                        facade.shipOrder();
                        System.out.println("Done::Order Shipped");
                    }catch(OrderStateException e){
                        System.out.println("Invalid Order State Transition: " + e.getMessage());
                    }catch(FacadeException fe){
                        System.out.println(fe.getMessage());
                    }
                    break;
                case 5:
                    try{
                        facade.deliverOrder();
                        System.out.println("Done::Order Delivered");
                    }catch(OrderStateException e){
                        System.out.println("Invalid Order State Transition: " + e.getMessage());
                    }catch(FacadeException fe){
                        System.out.println(fe.getMessage());
                    }
                    break;
                case 6:
                    try{
                        facade.cancelOrder();
                        System.out.println("Done::Order Canceled");
                    }catch(OrderStateException e){
                        System.out.println("Invalid Order State Transition: " + e.getMessage());
                    }catch(FacadeException fe){
                        System.out.println(fe.getMessage());
                    }
                    break;
                case 7:
                    try{
                        String state = facade.getOrderState();
                        System.out.println("order state: "+ state);
                    }catch(FacadeException fe){
                        System.out.println(fe.getMessage());
                    }
                    break;
                case 8:
                    System.out.println("Exiting...");
                    break;
            }
        }while(choice != 8);
    }
    public static void facade_test(){
        CheckoutFacade facade = new CheckoutFacade();
        facade.placeOrder(0, 40000);
    }
    public static void state_strategy_test(){
        OrderContext order = new OrderContext(new CardPayment(), 20000);
        try {
            print(order.getState().getClass().getName());
            order.pay();
            print(order.getState().getClass().getName());
            order.ship();
            print(order.getState().getClass().getName());
            order.deliver();
            print(order.getState().getClass().getName());
        }catch (Exception e){
            e.printStackTrace();
        }
        print("------------------------------------------");
        try{
            order = new OrderContext(new CashOnDelivery(), 50000);
            print(order.getState().getClass().getName());
            order.pay();
            print(order.getState().getClass().getName());
            order.cancel();
            order.pay();
        }catch (OrderStateException|PaymentException e){
            try{
                order.ship();
            }catch(OrderStateException e2){
                try{
                    order.deliver();
                }catch(OrderStateException e3){
                    try{
                        order.cancel();
                    }catch(OrderStateException e4){
                        print(
                                e.getMessage() +"\n"
                                        + e2.getMessage() +"\n"
                                        + e3.getMessage() +"\n"
                                        + e4.getMessage() +"\n"
                        );
                    }
                }
            }
        }
    }

    public static void print(Object message){
        System.out.println(message);
    }
}
