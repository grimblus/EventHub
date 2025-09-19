package au.edu.rmit.sept.webapp.models;

import jakarta.validation.constraints.*;

public class PaymentRequest {

    @NotBlank @Size(max = 80)
    private String cardholderName;

    @Email @NotBlank @Size(max = 120)
    private String email;

    @NotNull @DecimalMin(value = "1.00", inclusive = true)
    private Double amount;

    @NotBlank @Pattern(regexp = "^[0-9 ]{12,19}$", message = "Enter 12-19 digits")
    private String cardNumber;

    @Min(1) @Max(12)
    private int expMonth;

    @Min(2024)
    private int expYear;

    @NotBlank @Pattern(regexp = "^[0-9]{3,4}$", message = "3-4 digits")
    private String cvc;

    public PaymentRequest() {
        this.cardholderName = "";
        this.email = "";
        this.amount = 0.0;
        this.cardNumber = "";
        this.expMonth = 0;
        this.expYear = 0;
        this.cvc = "";
    }

    public PaymentRequest(String cardholderName, String email, Double amount, String cardNumber, int expMonth, int expYear, String cvc) {
        this.cardholderName = cardholderName;
        this.email = email;
        this.amount = amount;
        this.cardNumber = cardNumber;
        this.expMonth = expMonth;
        this.expYear = expYear;
        this.cvc = cvc;
    }

    // getters + setters
    public String getCardholderName() { return cardholderName; }
    public void setCardholderName(String cardholderName) { this.cardholderName = cardholderName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public String getCardNumber() { return cardNumber; }
    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }

    public int getExpMonth() { return expMonth; }
    public void setExpMonth(int expMonth) { this.expMonth = expMonth; }

    public int getExpYear() { return expYear; }
    public void setExpYear(int expYear) { this.expYear = expYear; }

    public String getCvc() { return cvc; }
    public void setCvc(String cvc) { this.cvc = cvc; }
}
