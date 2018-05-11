package ee.bcs.valiit.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Stats {

    private float average;
    private int count;

    public Stats(ResultSet result) {
        try {
            this.setAverage(result.getFloat("avg"));
            this.setCount(result.getInt("count"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public float getAverage() {
        return average;
    }

    public void setAverage(float average) {
        this.average = average;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
