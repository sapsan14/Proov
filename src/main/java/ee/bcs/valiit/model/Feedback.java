package ee.bcs.valiit.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Feedback {

    private String comment;
    private int grade;

    public Feedback(ResultSet result) {
        try {
            this.setComment(result.getString("feedback_comments"));
            this.setGrade(result.getInt("feedback_points"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }




}
