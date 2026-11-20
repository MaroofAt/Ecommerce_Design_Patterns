package core.orders.decorators;

import core.orders.OrderContext;
import core.orders.OrderState;
import core.orders.OrderStateException;
import core.orders.payment.PaymentException;
import core.orders.payment.PaymentStrategy;
import logs.LogsIO;


import java.time.LocalTime;

public class OrderContextLoggingDecorator extends OrderContextDecorator{
    private final String logsFileName = "OrderContextLogs.txt";
    public OrderContextLoggingDecorator(OrderContext wrappee) {
        super(wrappee);
        LogsIO.write_to(this.logsFileName, msgln("constructor(): Order Context Created"));
    }

    @Override
    public OrderState getState() {
//        System.out.println("Logging Decorator: " + msg("getState(): Getting Order State"));
        LogsIO.write_to(this.logsFileName, msgln("getState(): Getting Order State"));
        return super.getState();
    }

    @Override
    public void changeState(OrderState state) {
//        System.out.println("Logging Decorator: " + msg("changeState(): Changing Order State"));
        LogsIO.write_to(this.logsFileName, msgln("changeState(): Changing Order State"));
        super.changeState(state);
    }

    @Override
    public PaymentStrategy getPayment_strategy() {
//        System.out.println("Logging Decorator: " + msg("getPayment_strategy(): Getting Payment Strategy"));
        LogsIO.write_to(this.logsFileName, msgln("getPayment_strategy(): Getting Payment Strategy"));
        return super.getPayment_strategy();
    }

    @Override
    public void setPayment_strategy(PaymentStrategy payment_strategy) {
//        System.out.println("Logging Decorator: " + msg("setPayment_strategy(): Setting Payment Strategy"));
        LogsIO.write_to(this.logsFileName, msgln("setPayment_strategy(): Setting Payment Strategy"));
        super.setPayment_strategy(payment_strategy);
    }

    @Override
    public void pay() throws OrderStateException, PaymentException {
//        System.out.println("Logging Decorator: " + msg("pay(): Paying Order"));
        LogsIO.write_to(this.logsFileName, msg("pay(): Paying Order"));
        String result = "UNKNOWN";
        try{
            super.pay();
            result = "Succeed";
        }catch (OrderStateException e){
            result = "Failed";
        }finally {
            LogsIO.write_to(this.logsFileName, msgln(" :"+result));
        }
    }

    @Override
    public void ship() throws OrderStateException {
//        System.out.println("Logging Decorator: " + msg("ship(): Shipping Order"));
        LogsIO.write_to(this.logsFileName, msg("ship(): Shipping Order"));
        String result = "UNKNOWN";
        try{
            super.ship();
            result = "Succeed";
        }catch (OrderStateException e){
            result = "Failed";
        }finally {
            LogsIO.write_to(this.logsFileName, msgln(" :"+result));
        }
    }

    @Override
    public void deliver() throws OrderStateException {
//        System.out.println("Logging Decorator: " + msg("deliver(): Delivering Order"));
        LogsIO.write_to(this.logsFileName, msg("deliver(): Delivering Order"));
        String result = "UNKNOWN";
        try{
            super.deliver();
            result = "Succeed";
        }catch (OrderStateException e){
            result = "Failed";
        }finally {
            LogsIO.write_to(this.logsFileName, msgln(" :"+result));
        }
    }

    @Override
    public void cancel() throws OrderStateException {
//        System.out.println("Logging Decorator: " + msg("cancel(): Canceling Order"));
        LogsIO.write_to(this.logsFileName, msg("cancel(): Canceling Order"));
        String result = "UNKNOWN";
        try{
            super.cancel();
            result = "Succeed";
        }catch (OrderStateException e){
            result = "Failed";
        }finally {
            LogsIO.write_to(this.logsFileName, msgln(" :"+result));
        }
    }


    // tools
    private String msg(String message){
        return "[" + getNow() + "]:\t\t\t\t" + message;
    }
    private String msgln(String message){
        return msg(message) + "\n";
    }
    private String getNow(){
        LocalTime currentTime = LocalTime.now();
        return  currentTime.toString();
    }
}
