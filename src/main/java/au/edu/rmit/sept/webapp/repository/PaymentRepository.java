package au.edu.rmit.sept.webapp.repository;

import au.edu.rmit.sept.webapp.entity.Payment;
import au.edu.rmit.sept.webapp.entity.User;

import java.util.List;

public interface PaymentRepository {
    public List<Payment> GetAllPayments();
    public List<Payment> GetUserPayments(String user);
    public Payment GetPayment(String user, String ref);
    public void AddPayment(Payment payment);
}
