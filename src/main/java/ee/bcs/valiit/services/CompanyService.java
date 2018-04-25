package ee.bcs.valiit.services;

import ee.bcs.valiit.model.Company;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompanyService {

    public static final String SQL_CONNECTION_URL = "jdbc:mysql://localhost:3306/companies";
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
    public static List<Company> getCompanies()  {
        List<Company> companies = new ArrayList<Company>();
        try {
            ResultSet result = executeSql("select * from company");
            if (result != null) {
                while (result.next()) {
                    Company company = new Company();
                    company.setId(result.getInt("id"));
                    company.setName(result.getString("name"));
                    company.setEmployeeCount(result.getInt("employee_count"));
                    company.setEstablished(result.getString("established"));
                    company.setLogo(result.getString("logo"));
                    companies.add(company);
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return companies;
    }




}




