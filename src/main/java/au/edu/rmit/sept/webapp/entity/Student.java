package au.edu.rmit.sept.webapp.entity;

public class Student extends User {
    private String studentId;

    // Default Constructor
    public Student() {}

    // Full Constructor
    public Student(int id, String username, String password, String email, String studentId) {
        super(id, username, password, email);
        this.studentId = studentId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}
