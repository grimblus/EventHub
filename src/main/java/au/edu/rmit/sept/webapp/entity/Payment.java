package au.edu.rmit.sept.webapp.entity;

import java.time.LocalDateTime;

public class Payment {

    private String userEmail;
    private String amount;
    private LocalDateTime time;
    private String paymentRef;

    String getUser() { return userEmail; }
    String getAmount() { return amount; }
    LocalDateTime getPaymentTime() { return time; }
    String getPaymentRef() { return paymentRef; }

    public Payment() {}

    public Payment(String userEmail, String amount, LocalDateTime time, String paymentRef) {
        this.userEmail = userEmail;
        this.amount = amount;
        this.time = time;
        this.paymentRef = paymentRef;
    }
}