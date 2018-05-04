package ee.bcs.valiit.services;

import ee.bcs.valiit.model.Feedbackform;
import ee.bcs.valiit.model.Meeting;
import ee.bcs.valiit.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class OmniMeterService {

    public static final String SQL_CONNECTION_URL = "jdbc:mysql://localhost:3306/mydb";
    public static final String SQL_USERNAME = "root";
    public static final String SQL_PASSWORD = "tere";


    public static void addUser(User user) {
        //salvestame ettevõtte andmebaasi
        if (userDoesntExsist(user.getEmail())) {
            String sql = "INSERT INTO user (first_name, last_name, password, role, department, email)" +
                    " VALUES ('" + user.getFirstName() + "' , '" + user.getLastName() + "' , '" + user.getPassword() + "' , " + user.getPersimissonsId() + " , '" + user.getDepartment() + "' , '" + user.getEmail() + "')";
            executeSql(sql);

        }
    }

    public static void addFeedBack(Feedbackform feedback) {
        //salvestame ettevõtte andmebaasi
//        if (userDoesntExsist(user.getEmail())) {
        String sql = "INSERT INTO feedback (id, feedback_points, feedback_comments, meeting_id, )" +
                " VALUES ('" + feedback.getFeedBackFormId() + "' , '" + feedback.getFeedBackAsNumber() + "' , '" + feedback.getComment() + "' , " + feedback.getMeetingId() + "')";
        executeSql(sql);

//        }
    }


    public static void modifyUser(User user) {
        String sql = String.format("UPDATE user SET first_name = '%s', last_name = '%s', password = '%s', role = %s, department = '%s', email = '%s' WHERE id = %s",
                user.getFirstName(), user.getLastName(), user.getPassword(), user.getPersimissonsId(), user.getDepartment(), user.getEmail(), user.getId());
        executeSql(sql);
    }


    public static List<User> getUsers() {
        List<User> users = new ArrayList<User>();
        try {
            ResultSet result = executeSql("select * from user");
            if (result != null) {
                while (result.next()) {
                    users.add(new User(result));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public static boolean userDoesntExsist(String email) {
        return getUserByEmail(email) == null;
    }


    public static User getUserByEmail(String email) {
        try {
            ResultSet result = executeSql("select id, first_name, last_name, password, role, department, email from user where email = '" + email + "'");
            if (result != null) {
                if (result.next()) {
                    User user = new User();
                    user.setId(result.getInt("id"));
                    user.setFirstName(result.getString("first_name"));
                    user.setLastName(result.getString("last_name"));
                    user.setPersimissonsId(result.getInt("role"));
                    user.setPassword(result.getString("password"));
                    user.setDepartment(result.getString("department"));
                    user.setEmail(result.getString("email"));

                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static ResultSet executeSql(String sql) {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(SQL_CONNECTION_URL, SQL_USERNAME, SQL_PASSWORD)) {
                try (Statement stmt = conn.createStatement()) {
                    return stmt.executeQuery(sql);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Meeting getMeetingById(int meetingId) {
        try {
            String sql = "Select * from meeting where id =" + meetingId;
            ResultSet result = executeSql(sql);
            if (result != null) {
                if (result.next()) {
                    Meeting meeting = new Meeting();
                    meeting.setMeetingId(result.getInt("id"));
                    meeting.setMeetingOwnerId(result.getInt("meeting_owner_id"));
                    meeting.setMeetingTypeId(result.getInt("meeting_type_id"));
                    meeting.setDateTimeForMeeting(result.getString("datetime"));
                    meeting.setDetailsOfMeeting(result.getString("details_of_meeting"));
                    meeting.setSubject(result.getString("subject"));
                    return meeting;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static User getUser(int userId) {
        try {
            String sql = "Select * from user where id =" + userId;
            ResultSet result = executeSql(sql);
            if (result != null) {
                if (result.next()) {
                    User user = new User();
                    user.setId(result.getInt("id"));
                    user.setFirstName(result.getString("first_name"));
                    user.setLastName(result.getString("last_name"));
                    user.setPersimissonsId(result.getInt("role"));
                    user.setPassword(result.getString("password"));
                    user.setDepartment(result.getString("department"));
                    user.setEmail(result.getString("email"));
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void deleteUser(int userId) {
        String sql = "DELETE FROM user where id = " + userId;
        executeSql(sql);
    }


}
