package au.edu.rmit.sept.webapp.repository;

import au.edu.rmit.sept.webapp.entity.User;
import au.edu.rmit.sept.webapp.H2DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

    @Repository
    public interface UserRepo extends JpaRepository<User, Integer> { // User is the entity. Integer is the primary key 

        //add custom queries in here eg findByInterests or something. otherwise the basic crud operations are already
        // implemented by jparepository so we don't have to make them
        User findByEmail(String email);
    }

// public class UserRepo {
//     static List<User> userList = List.of(
//             new User("Aaron", "Tran", "password123", "aaron@example.com", 0),
//             new User("Bingus", "Bongus", "s3cur3pass", "bingus@example.com", 2),
//             new User("Coolguy", "Joe", "coolguy123", "coolguy@example.com", 0),
//             new User("Johndev", "Programmer", "g0d0t0man", "johndev@example.com", 1),
//             new User("Dexter", "Morgan", "b4yharb0r", "morgan@example.com", 1),
//             new User("Deez", "Noots", "g0tt3m", "deez@example.com", 0)
//     );

//     public static void setUsers() {
//         try (Connection connection = H2DatabaseConnection.getConnection()) {
//             String createTableSQL = "CREATE TABLE IF NOT EXISTS users (" +
//                     "id INT AUTO_INCREMENT PRIMARY KEY, " +
//                     "firstName VARCHAR(255), " +
//                     "lastName VARCHAR(255), " +
//                     "password VARCHAR(255), " +
//                     "email VARCHAR(255), " +
//                     "permissions INT)";
//             PreparedStatement createTableStatement = connection.prepareStatement(createTableSQL);
//             createTableStatement.executeUpdate();

//             String insertSQL = "INSERT INTO users (firstName, lastName, password, email, permissions) VALUES (?, ?, ?, ?, ?)";
//             for (User user : userList) {
//                 try (PreparedStatement ps = connection.prepareStatement(insertSQL)) {
//                     ps.setString(1, user.getFirstName());
//                     ps.setString(2, user.getLastName());
//                     ps.setString(3, user.getPassword());
//                     ps.setString(4, user.getEmail());
//                     ps.setInt(5, user.getPermissions());
//                     ps.executeUpdate();
//                 }
//             }
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//     }
// }