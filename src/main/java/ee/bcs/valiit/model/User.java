package ee.bcs.valiit.model;

public class User {
    private int id;
    private String firstName;
    private String lastName;
    private int persimissonsId;
    private int department;
    private String password;

    public User() {

    }

    public User(int id, String firstName, String lastName, int persimissonsId, int department, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.persimissonsId = persimissonsId;
        this.department = department;
        this.password = password;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getPersimissonsId() {
        return persimissonsId;
    }

    public void setPersimissonsId(int persimissonsId) {
        this.persimissonsId = persimissonsId;
    }

    public int getDepartment() {
        return department;
    }

    public void setDepartment(int department) {
        this.department = department;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
