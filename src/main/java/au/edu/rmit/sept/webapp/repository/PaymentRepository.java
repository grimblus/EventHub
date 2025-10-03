package au.edu.rmit.sept.webapp.repository;

import au.edu.rmit.sept.webapp.entity.Payment;
import java.util.List;

public interface PaymentRepository {
    public List<Payment> GetAllPayments();
    public void AddPayment(Payment payment);
    public List<Payment> GetUserPayments(int user_id);
    public Payment GetPayment(String ref);
}
