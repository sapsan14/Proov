package ee.bcs.valiit.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Department {

    private int id;
    private String name;

    public Department(ResultSet result) {
        try {
            this.name = result.getString("name");
            this.id = result.getInt("id");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
