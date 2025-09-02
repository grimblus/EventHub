package au.edu.rmit.sept.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@SpringBootApplication
public class WebappApplication {
	public static void main(String[] args) {
		try {
			Connection connection = H2DatabaseConnection.getConnection();
			// Create a table and insert data
			String createTableSQL = "CREATE TABLE IF NOT EXISTS users (id INT AUTO_INCREMENT PRIMARY KEY" +
			"username VARCHAR(255), password VARCHAR(255), email VARCHAR(255), permissions INT)";
			PreparedStatement createTableStatement = connection.prepareStatement(createTableSQL);
			createTableStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		/*try {
			// The database exists, so insert some data into it
			Connection connection = H2DatabaseConnection.getConnection();
			String insertDataSQL = "INSERT INTO users (name) VALUES (?)";
			try (PreparedStatement insertDataStatement = connection.prepareStatement(insertDataSQL)) {
				insertDataStatement.setString(1, "John Doe");

				insertDataStatement.executeUpdate();
				// Now get the data back out again
				String selectDataSQL = "SELECT * FROM users";
				try (PreparedStatement selectDataStatement = connection.prepareStatement(selectDataSQL)) {
					ResultSet resultSet = selectDataStatement.executeQuery();
					while (resultSet.next()) {
						// There may be multiple selected rows from the table; this loop iterates over each of them
						int id = resultSet.getInt("id");
						String name = resultSet.getString("name");
						System.out.println("ID: " + id + ", Name: " + name);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		*/
				SpringApplication.run(WebappApplication.class, args);
	}
}
