package au.edu.rmit.sept.webapp.repository;

import au.edu.rmit.sept.webapp.H2DatabaseConnection;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import au.edu.rmit.sept.webapp.entity.Payment;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PaymentRepositoryImpl implements PaymentRepository {
    static List<Payment> paymentList = List.of(
        new Payment("REF-152045CC", 1, "54", LocalDateTime.now()),
        new Payment("REF-152045CA", 1, "43", LocalDateTime.now()),
        new Payment("REF-11111111", 2, "999", LocalDateTime.now())
    );


    public void setPayments() {
        try(Connection connection = H2DatabaseConnection.getConnection()) {
            String createTableSQL = 
                    "CREATE TABLE IF NOT EXISTS payments (" +
                    "id VARCHAR(255) PRIMARY KEY, " +               // payment reference (primary key)
                    "user_id INT, " +                               // linking ot user id table
                    "amount DECIMAL(10, 2), " +                     // so we get 2 decimal place epic
                    "time TIMESTAMP, " +                
                    "FOREIGN KEY(user_id) REFERENCES users(id))";
            PreparedStatement createTableStatement = connection.prepareStatement(createTableSQL);
            createTableStatement.executeUpdate();

            String insertPayment = "INSERT INTO payments (id, user_id, amount, time) VALUES (?, ?, ?, ?)";
            for(Payment p : paymentList) {
                try(PreparedStatement ps = connection.prepareStatement(insertPayment)) {
                    ps.setString(1, p.getPaymentRef());
                    ps.setInt(2, p.getUser());
                    ps.setBigDecimal(3, new BigDecimal(p.getAmount()));
                    ps.setTimestamp(4, Timestamp.valueOf(p.getPaymentTime()));
                    ps.executeUpdate();
                }
            }
            

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public List<Payment> GetAllPayments() {
        List<Payment> payments = new ArrayList<>();
        try(Connection connection = H2DatabaseConnection.getConnection()) {
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
        try(Connection connection = H2DatabaseConnection.getConnection()) {
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
        try(Connection connection = H2DatabaseConnection.getConnection()) {
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
        try(Connection connection = H2DatabaseConnection.getConnection()) {
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
