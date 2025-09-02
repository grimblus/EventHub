package au.edu.rmit.sept.webapp.entity;

public class User {
    private Integer id;
    private String username;
    private String password;
    private String email;
    private int permissions;

    // Default Constructor
    public User() {}

    // No ID Constructor
    public User(String username, String password, String email, int permissions) {
        this.id = null;
        this.username = username;
        this.password = password;
        this.email = email;
        this.permissions = permissions;
    }

    // Full Constructor
    public User(Integer id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.permissions = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPermissions() {
        return permissions;
    }

    public void setPermissions(int permissions) {
        this.permissions = permissions;
    }
}