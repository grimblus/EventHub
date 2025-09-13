package au.edu.rmit.sept.webapp.repository;

import au.edu.rmit.sept.webapp.entity.Payment;
import au.edu.rmit.sept.webapp.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PaymentRepositoryImpl implements PaymentRepository {
    public List<Payment> GetAllPayments() {
        List<Payment> payments = null;
        return payments;
    }
    public List<Payment> GetUserPayments(String user) {
        List<Payment> payments = null;
        return payments;
    }
    public Payment GetPayment(String user, String ref) {
        Payment payment = null;
        return payment;
    }
    public void AddPayment(Payment payment) {
        // insert crap here
    }
}
