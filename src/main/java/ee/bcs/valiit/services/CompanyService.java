package ee.bcs.valiit.services;

import ee.bcs.valiit.model.Company;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompanyService {

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




    public static void addCompany(Company company) {
        //salvestame ettevõtte andmebaasi
        if (companyNotExists(company.getName())) {
            String sql = "INSERT INTO company (name, employee_count, established, logo)" +
                    " VALUES ('" + company.getName() + "' , " + company.getEmployeeCount() + " , '"
                    + company.getEstablished() + "', '" + company.getLogo() + "')";
            executeSql(sql);

        }
    }

    public static void modifyCompany(Company company){
        String sql = "";
        if (company.getLogo() != null && company.getLogo().length() > 0) {
            sql = String.format(
                    "UPDATE company SET name = '%s', employee_count = %s, " +
                            "established = '%s', logo='%s' WHERE id = %s",
                    company.getName(), company.getEmployeeCount(), company.getEstablished(), company.getLogo(),
                    company.getId());
        } else {
            sql = String.format(
                    "UPDATE company SET name = '%s', employee_count = %s, " +
                            "established = '%s' WHERE id = %s",
                    company.getName(), company.getEmployeeCount(), company.getEstablished(),
                    company.getId());
        }
        executeSql(sql);
    }



    public static boolean companyNotExists(String name) {
        return getCompanyByName(name) == null;
    }

    public static Company getCompanyByName(String name) {
        try {
            ResultSet result = executeSql("select id, name, employee_count, established, logo from company where name = '" + name + "'");
            if (result != null) {
                if (result.next()) {
                    Company company = new Company();
                    company.setId(result.getInt("id"));
                    company.setName(result.getString("name"));
                    company.setEmployeeCount(result.getInt("employee_count"));
                    company.setEstablished(result.getString("established"));
                    company.setLogo(result.getString("logo"));
                    return company;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Company getCompany(int companyID) {
        try {
            String sql = "Select * from company where id =" + companyID;
            ResultSet result = executeSql(sql);
            if (result != null) {
                if (result.next()) {
                    Company company = new Company();
                    company.setId(result.getInt("id"));
                    company.setName(result.getString("name"));
                    company.setEmployeeCount(result.getInt("employee_count"));
                    company.setEstablished(result.getString("established"));
                    company.setLogo(result.getString("logo"));
                    return company;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void deleteCompany(int companyId) {
        String sql = "DELETE FROM company where id = " + companyId;
        executeSql(sql);
    }

    public static List<Company> getCompanies() {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return companies;
    }



}




