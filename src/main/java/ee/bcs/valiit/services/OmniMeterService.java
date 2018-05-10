package ee.bcs.valiit.services;

import ee.bcs.valiit.model.Feedbackform;
import ee.bcs.valiit.model.Meeting;
import ee.bcs.valiit.model.MeetingType;
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


    public static void addUser(User user) {
        //salvestame ettevõtte andmebaasi
        if (userDoesntExist(user.getEmail())) {
            String sql = "INSERT INTO user (first_name, last_name, password, role_id, department, email)" +
                    " VALUES ('" + user.getFirstName() + "' , '" + user.getLastName() + "' , '" + user.getPassword() + "' , '" + user.getPersimissonsId() + "' , '" + user.getDepartment() + "' , '" + user.getEmail() + "')";
            executeSql(sql);

        }
    }

    public static void addMeeting(Meeting meeting) {
        //salvestame koosoleku andmebaasi
        if (meetingDoesntExist(meeting.getUniqueHash())) {
            String sql = "INSERT INTO meeting (uuid, subject, details, date, time, type, meeting_owner_id)" +
                    " VALUES ('" + meeting.getUniqueHash() + "' , '" + meeting.getSubject() + "' , '" + meeting.getDetails() + "' , '" + meeting.getDate() + "'    , '" + meeting.getTime() + "' , '" + meeting.getType() + "' , " + meeting.getOwnerId() + ")";
            executeSql(sql);

        }
    }

    public static void addFeedBack(Feedbackform feedback) {
        //salvestame vormi andmebaasi
//        if (userDoesntExist(user.getEmail())) {
        String sql = "INSERT INTO feedback_form (feedback_points, feedback_comments, meeting_uuid )" +
                " VALUES (" + feedback.getFeedBackAsNumber() + " , '" + feedback.getComment() + "' , '" + feedback.getMeetingUuid() + "')";
        executeSql(sql);

//        }
    }


    public static void modifyUser(User user) {
        String sql = String.format("UPDATE user SET first_name = '%s', last_name = '%s', password = '%s', role_id = '%s', department = '%s', email = '%s' WHERE id = %s",
                user.getFirstName(), user.getLastName(), user.getPassword(), user.getPersimissonsId(), user.getDepartment(), user.getEmail(), user.getId());
        executeSql(sql);
    }

    public static void modifyMeeting(Meeting meeting) {
        String sql = String.format("UPDATE meeting SET uuid = '%s', subject = '%s', details = '%s', date = '%s', time = '%s', type = '%s', meeting_owner_id = '%s' WHERE uuid = '%s'",
                meeting.getUniqueHash(), meeting.getSubject(), meeting.getDetails(), meeting.getDate(), meeting.getTime(), meeting.getType(), meeting.getOwnerId(), meeting.getUniqueHash());
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

    public static List<Meeting> getMeetings(int meetingOwnerId) {
        List<Meeting> meetings = new ArrayList<Meeting>();
        try {
            ResultSet result = executeSql("select * from meeting where meeting_owner_id =" + meetingOwnerId);
            if (result != null) {
                while (result.next()) {
                    meetings.add(new Meeting(result));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return meetings;
    }


    public static boolean userDoesntExist(String email) {
        return getUserByEmail(email) == null;
    }

    public static boolean meetingDoesntExist(String uuid) {
        return getMeetingByUuid(uuid) == null;
    }


    public static User getUserByEmail(String email) {
        try {
            ResultSet result = executeSql("select id, first_name, last_name, password, role_id, department, email from user where email = '" + email + "'");
            if (result != null) {
                if (result.next()) {
                    User user = new User();
                    user.setId(result.getInt("id"));
                    user.setFirstName(result.getString("first_name"));
                    user.setLastName(result.getString("last_name"));
                    user.setPersimissonsId(result.getString("role_id"));
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

    public static Meeting getMeetingByUuid(String uuid) {
        try {
            ResultSet result = executeSql("select * from meeting where uuid ='" + uuid + "'");
            if (result != null) {
                if (result.next()) {
                    Meeting meeting = new Meeting();
                    meeting.setUniqueHash(result.getString("uuid"));
                    meeting.setSubject(result.getString("subject"));
                    meeting.setDetails(result.getString("details"));
                    meeting.setDate(result.getString("date"));
                    meeting.setTime(result.getString("time"));
                    meeting.setType(result.getString("type"));
                    meeting.setOwnerId(result.getInt("meeting_owner_id"));
                    meeting.setMeetingHolder(getUser(meeting.getOwnerId()));

                    return meeting;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

//    public static Meeting getMeetingById(int meetingId) {
//        try {
//            String sql = "Select * from meeting where id =" + meetingId;
//            ResultSet result = executeSql(sql);
//            if (result != null) {
//                if (result.next()) {
//                    Meeting meeting = new Meeting();
//                    meeting.setMeetingId(result.getInt("id"));
//                    meeting.setOwnerId(result.getInt("meeting_owner_id"));
//                    meeting.setType(result.getString("type"));
//                    meeting.setUniqueHash(result.getString("uuid"));
//                    meeting.setDate(result.getString("date"));
//                    meeting.setTime(result.getString("time"));
//                    meeting.setDetails(result.getString("details"));
//                    meeting.setSubject(result.getString("subject"));
//                    meeting.setMeetingHolder(getUser(meeting.getOwnerId()));
//
//                    return meeting;
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }

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
                    user.setPersimissonsId(result.getString("role_id"));
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


    public static void deleteMeeting(String uuid) {
        String sql = "DELETE FROM meeting where uuid = " + "'" + uuid + "'";
        executeSql(sql);
    }

    public static List<MeetingType> getMeetingTypes() {
        List<MeetingType> meetingTypes = new ArrayList<>();
        try {
            ResultSet result = executeSql("select * from meeting_type ");
            if (result != null) {
                while (result.next()) {
                    meetingTypes.add(new MeetingType(result));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return meetingTypes;
    }

    public static List<Meeting> getAllMeetings() {
        List<Meeting> allmeetings = new ArrayList<>();
        try {
            ResultSet result = executeSql("select * from meeting");
            if (result != null) {
                while (result.next()) {
                    allmeetings.add(new Meeting(result));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allmeetings;
    }
}

