package ee.bcs.valiit.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MeetingType {

    private int id;
    private String type;

    public MeetingType(ResultSet result) {
        try {
            this.type = result.getString("type");
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
