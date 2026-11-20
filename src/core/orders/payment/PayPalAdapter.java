package core.orders.payment;

import third_parties.ExternalPayPalLib;

public class PayPalAdapter implements PaymentStrategy{
    protected ExternalPayPalLib payPalLib;

    public PayPalAdapter(ExternalPayPalLib payPalLib) {
        this.payPalLib = payPalLib;
    }
    public PayPalAdapter() {
        this.payPalLib = new ExternalPayPalLib();
    }
    public ExternalPayPalLib getPayPalLib() {return payPalLib;}


    @Override
    public void pay(double amount) throws PaymentException {
        this.payPalLib.makeTransaction(amount, "USD");
    }
}
