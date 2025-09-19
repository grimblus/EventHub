package au.edu.rmit.sept.webapp.entity;

import java.time.LocalDateTime;

public class Payment {

    private String paymentRef;
    private int userID;
    private String amount;
    private LocalDateTime time;
    // uuid make sure 

    public void setPaymentRef(String paymentRef) { this.paymentRef = paymentRef; }
    public void setUser(int userID) { this.userID = userID; }
    public void setAmount(String amount) { this.amount = amount; }
    public void setPaymentTime(LocalDateTime time) { this.time = time; }

    public String getPaymentRef() { return paymentRef; }
    public int getUser() { return userID; }
    public String getAmount() { return amount; }
    public LocalDateTime getPaymentTime() { return time; }

    public Payment() {
        paymentRef = "";
        userID = 0;
        amount = "";
        time = LocalDateTime.MIN;
    }

    public Payment(String paymentRef, int userID, String amount, LocalDateTime time) {
        this.paymentRef = paymentRef;
        this.userID = userID;
        this.amount = amount;
        this.time = time;

    }
}