package au.edu.rmit.sept.webapp.entity;

public class Organiser extends User {
    private String organisation;

    // Default Constructor
    public Organiser() {}

    // Full Constructor
    public Organiser(int id, String username, String password, String email, String organisation) {
        super(id, username, password, email);
        this.organisation = organisation;
    }

    public void setOrganisationName(String organisation) {
        this.organisation = organisation;
    }

    public String getOrganisationName() {
        return organisation;
    }
}
