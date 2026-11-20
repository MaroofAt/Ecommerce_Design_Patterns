package third_parties;

public class ExternalPayPalLib implements ExternalPayPalLibInterface{
    @Override
    public void makeTransaction(double value, String currency) {
        System.out.println("Paying "+ value +" "+ currency +" Using PayPal...");
    }
}
