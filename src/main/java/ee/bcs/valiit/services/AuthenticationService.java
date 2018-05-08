package ee.bcs.valiit.services;

import ee.bcs.valiit.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthenticationService {

    public static User getUser (String email, String password) {

        String sql = String.format("SELECT * FROM user WHERE email = '%s' AND password = '%s'", email, password);
        ResultSet userRecord = OmniMeterService.executeSql(sql);

        try {
            if (userRecord != null && userRecord.next()) {
                return instantiateUser(userRecord);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

        protected static User instantiateUser (ResultSet userRecord) throws SQLException {
            User user = new User();
            user.setId(userRecord.getInt("id"));
            user.setFirstName(userRecord.getString("first_name"));
            user.setLastName(userRecord.getString("last_name"));
            user.setEmail(userRecord.getString("email"));
            user.setPersimissonsId(userRecord.getString("role_id"));
            return user;
        }
}
