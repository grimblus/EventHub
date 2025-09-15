package au.edu.rmit.sept.webapp.entity;

import java.time.LocalDateTime;

public class Payment {

    private String paymentRef;
    private int userID;
    private String amount;
    private LocalDateTime time;
    // uuid make sure 

    public String getPaymentRef() { return paymentRef; }
    public int getUser() { return userID; }
    public String getAmount() { return amount; }
    public LocalDateTime getPaymentTime() { return time; }

    public Payment() {}

    public Payment(String paymentRef, int userID, String amount, LocalDateTime time) {
        this.paymentRef = paymentRef;
        this.userID = userID;
        this.amount = amount;
        this.time = time;

    }
}