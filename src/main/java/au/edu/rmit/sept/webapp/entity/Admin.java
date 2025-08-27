package au.edu.rmit.sept.webapp.entity;

public class Admin extends User {
    // Default Constructor
    public Admin() {}

    // Full Constructor
    public Admin(int id, String username, String password, String email) {
        super(id, username, password, email);
    }
}
