package au.edu.rmit.sept.webapp.repository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import java.util.Locale;
import java.util.UUID;

import au.edu.rmit.sept.webapp.entity.Payment;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Repository
public class PaymentRepositoryImpl implements PaymentRepository {
    private final DataSource dataSource;
    static String randUUID() {
        return "REF-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase(Locale.ROOT);
    }

    public PaymentRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // get every payment from the payments table and add them to a list of Payments that is returned 
    public List<Payment> GetAllPayments() {
        List<Payment> payments = new ArrayList<>();
        try(Connection connection = this.dataSource.getConnection()) {
            String searchPayment = "SELECT * FROM payments";
            try(PreparedStatement ps = connection.prepareStatement(searchPayment)) {
                try(ResultSet rs = ps.executeQuery()) {
                    while(rs.next()) {
                        Payment payment = new Payment(
                            rs.getString("id"),
                            rs.getInt("user_id"),
                            rs.getBigDecimal("amount").toString(),
                            rs.getTimestamp("time").toLocalDateTime()
                        );

                        payments.add(payment);
                    }
                }
            };
        } catch(Exception e) {
            e.printStackTrace();
        }
        return payments;
    }

    public void AddPayment(Payment payment) {
        try(Connection connection = this.dataSource.getConnection()) {
            String insertPayment = "INSERT INTO payments (id, user_id, amount, time) VALUES (?, ?, ?, ?)";
            try(PreparedStatement ps = connection.prepareStatement(insertPayment)) {
                ps.setString(1, payment.getPaymentRef());
                ps.setInt(2, payment.getUser());
                ps.setBigDecimal(3, new BigDecimal(payment.getAmount()));
                ps.setTimestamp(4, Timestamp.valueOf(payment.getPaymentTime()));
                ps.executeUpdate();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public List<Payment> GetUserPayments(int user_id) {
        List<Payment> payments = new ArrayList<>();
        String searchSQL = "SELECT * FROM payments WHERE user_id = ?";
        try(Connection connection = this.dataSource.getConnection()) {
            try(PreparedStatement ps = connection.prepareStatement(searchSQL)) {
                ps.setInt(1, user_id);
                try(ResultSet rs = ps.executeQuery()) {
                    while(rs.next()) {
                        Payment payment = new Payment(
                            rs.getString("id"),
                            rs.getInt("user_id"),
                            rs.getBigDecimal("amount").toString(),
                            rs.getTimestamp("time").toLocalDateTime()
                        );

                        payments.add(payment);
                    }
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        return payments;
    }

    public Payment GetPayment(String ref) {
        Payment payment = null;
        String searchSQL = "SELECT * FROM payments WHERE id = ?";
        try(Connection connection = this.dataSource.getConnection()) {
            try(PreparedStatement ps = connection.prepareStatement(searchSQL)) {
                ps.setString(1, ref);
                try(ResultSet rs = ps.executeQuery()) {
                    if(rs.next()) {
                        payment = new Payment(
                            rs.getString("id"),
                            rs.getInt("user_id"),
                            rs.getBigDecimal("amount").toString(),
                            rs.getTimestamp("time").toLocalDateTime()
                        );
                    }
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        return payment;
    }
}
