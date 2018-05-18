package ee.bcs.valiit.services;

import ee.bcs.valiit.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OmniMeterService {

   /* private static Properties properties = null;

    public static Properties getProperties() {
        if (properties == null) {
            properties = new Properties();
            InputStream input = null;
            try

            {
                ClassLoader classLoader = OmniMeterService.class.getClassLoader();
                input = new FileInputStream(classLoader.getResource("config.properties").getFile());
                properties.load(input);

            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                if (input != null) {
                    try {
                        input.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return properties;
    }*/

    static ServerConfig serverConfig = new ServerConfig();

/*    public static final String SQL_CONNECTION_URL = serverConfig.getDbAddres() ;// "jdbc:mysql://localhost:3306/mydb";
    public static final String SQL_USERNAME = getProperties().getProperty("dbuser");// "root";
    public static final String SQL_PASSWORD = getProperties().getProperty("dbpassword");// "tere";*/


    public static ResultSet executeSql(String sql) {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(serverConfig.getDbAddres(), serverConfig.getDbUser(), serverConfig.getDbPassword())) {
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
            String sql = "INSERT INTO user (first_name, last_name, password, role_id, department, email, user_uuid)" +
                    " VALUES ('" + user.getFirstName() + "' , '" + user.getLastName() + "' , '" + user.getPassword() + "' , '" + user.getPersimissonsId() + "' , '" + user.getDepartment() + "' , '" + user.getEmail() + "', '" + user.getUserUuid() + "')";
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


    public static boolean userDoesntExist(String email) {
        return getUserByEmail(email) == null;
    }

    public static boolean meetingDoesntExist(String uuid) {
        return getMeetingByUuid(uuid) == null;
    }


    public static String getUserUuidByEmail(String email) {
        String uuid = UUID.randomUUID().toString();
        String sql = String.format("UPDATE user SET user_uuid = '%s' WHERE email = '%s'", uuid, email);
        executeSql(sql);
        SendEmail sm = new SendEmail();
        sm.sendPasswordRecoveryMail(email, uuid);
        return uuid;
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
        String sql = "DELETE FROM user where id = " + userId + " And role_id <> 'admin' ";
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

    public static Stats getStatsByUuid(String meeting_uuid) {
        String sql = "SELECT meeting_uuid, count(id) as count, avg(feedback_points) as avg from feedback_form where meeting_uuid=" + "'" + meeting_uuid + "'";
        ResultSet result = executeSql(sql);
        try {
            if (result != null) {
                if (result.next()) {
                    Stats stats = new Stats(result);
                    stats.setAverage(result.getFloat("avg"));
                    stats.setCount(result.getInt("count"));
                    return stats;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Stats getStatsByUserId(int user_id) {
        String sql = "SELECT avg(feedback_form.feedback_points) as avg, meeting_owner_id from feedback_form \n" +
                " join meeting on meeting.uuid = feedback_form.meeting_uuid \n" +
                "where meeting.meeting_owner_id = " + user_id;
        ResultSet result = executeSql(sql);
        try {
            if (result != null) {
                if (result.next()) {
                    Stats stats = new Stats(result);
                    stats.setAverage(result.getFloat("avg"));
                    return stats;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<Feedback> getFeedbackByUuid(String meeting_uuid) {
        List<Feedback> feedbackWithComments = new ArrayList<>();
        try {
            String sql = "SELECT feedback_comments, feedback_points from feedback_form where meeting_uuid =" + "'" + meeting_uuid + "'";
            ResultSet result = executeSql(sql);
            if (result != null) {
                while (result.next()) {
                    feedbackWithComments.add(new Feedback(result));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return feedbackWithComments;

    }

    public static List<Meeting> getMeetings(int meetingOwnerId) {
        List<Meeting> meetings = new ArrayList<Meeting>();
        try {
            ResultSet result = executeSql("select * from meeting where meeting_owner_id =" + meetingOwnerId + " order by date desc");
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

    public static void updatePassword(String user_uuid, String password) {
        String sql = String.format("UPDATE user SET password = '%s' WHERE user_uuid = '%s'", password, user_uuid);
        executeSql(sql);
        String sql1 = String.format("Select email from user where user_uuid = '%s'", user_uuid);
        ResultSet result = executeSql(sql1);
        String email = null;
        try {
            while (result.next()) {
                email = result.getString("email");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String uuid = UUID.randomUUID().toString();
        String sql2 = String.format("UPDATE user SET user_uuid = '%s' WHERE user_uuid = '%s'", uuid, user_uuid);
        executeSql(sql2);
        SendEmail updateEmail = new SendEmail();
        updateEmail.passwordChangedConfirmationEmail(email);
    }

    public static List<Department> getDepartments() {
            List<Department> departments = new ArrayList<>();
            try {
                ResultSet result = executeSql("select * from department");
                if (result != null) {
                    while (result.next()) {
                        departments.add(new Department(result));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return departments;
        }
}
