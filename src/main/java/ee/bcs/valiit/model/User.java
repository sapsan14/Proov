package ee.bcs.valiit.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String persimissonsId;
    private String department;
    private String password;
    private String email;
    private String role;


    public User() {

    }

    public User(ResultSet result) {
        try {
            this.setId(result.getInt("id"));
            this.setFirstName(result.getString("first_name"));
            this.setLastName(result.getString("last_name"));
            this.setPassword(result.getString("password"));
            this.setPersimissonsId(result.getString("role_id"));
            this.setDepartment(result.getString("department"));
            this.setEmail(result.getString("email"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User(int id, String firstName, String lastName, String password, String persimissonsId, String department, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.persimissonsId = persimissonsId;
        this.department = department;
        this.email = email;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPersimissonsId() {
        return persimissonsId;
    }

    public void setPersimissonsId(String persimissonsId) {
        this.persimissonsId = persimissonsId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }



}
