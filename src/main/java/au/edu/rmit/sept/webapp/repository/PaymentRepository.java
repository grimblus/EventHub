package au.edu.rmit.sept.webapp.repository;

import au.edu.rmit.sept.webapp.entity.Payment;
import au.edu.rmit.sept.webapp.entity.User;

import java.util.List;
import org.springframework.stereotype.Repository;
import java.sql.Connection;

public interface PaymentRepository {
    public void setPayments();
    public List<Payment> GetAllPayments();
    public void AddPayment(Payment payment);
    public List<Payment> GetUserPayments(int user_id);
    public Payment GetPayment(String ref);
}
