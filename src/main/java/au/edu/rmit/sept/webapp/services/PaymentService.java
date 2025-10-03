package au.edu.rmit.sept.webapp.services;

import java.util.List;

import au.edu.rmit.sept.webapp.entity.Payment;

// what is the point of these????
public interface PaymentService {
    public List<Payment> GetAllPayments();
    public void AddPayment(Payment payment);
    public List<Payment> GetUserPayments(int user_id);
    public Payment GetPayment(String ref);
} 
