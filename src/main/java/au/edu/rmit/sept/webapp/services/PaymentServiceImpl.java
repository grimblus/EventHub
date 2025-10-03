package au.edu.rmit.sept.webapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.edu.rmit.sept.webapp.entity.Payment;
import au.edu.rmit.sept.webapp.repository.PaymentRepository;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository repository;

    @Autowired
    public PaymentServiceImpl(PaymentRepository repository) { this.repository = repository; }

    public List<Payment> GetAllPayments() { return repository.GetAllPayments(); }
    public void AddPayment(Payment payment) { repository.AddPayment(payment); } 
    public List<Payment> GetUserPayments(int user_id) { return repository.GetUserPayments(user_id); }
    public Payment GetPayment(String ref) { return repository.GetPayment(ref); }
}
