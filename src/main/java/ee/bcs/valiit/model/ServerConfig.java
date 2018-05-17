package ee.bcs.valiit.model;

import ee.bcs.valiit.services.OmniMeterService;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ServerConfig {

    private String dbAddres = "jdbc:mysql://" + getProperties().getProperty("database");;
    private String dbPassword = getProperties().getProperty("dbpassword");
    private String dbUser = getProperties().getProperty("dbuser");
    private String webSiteAddress = getProperties().getProperty("webAddress");
    private String email = getProperties().getProperty("email");
    private String emailPassword = getProperties().getProperty("emailPassword");

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailPassword() {
        return emailPassword;
    }

    public void setEmailPassword(String emailPassword) {
        this.emailPassword = emailPassword;
    }

    public ServerConfig(){

    }

    private static Properties properties = null;

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
    }


    public String getWebSiteAddress() {
        return webSiteAddress;
    }

    public void setWebSiteAddress(String webSiteAddress) {
        this.webSiteAddress = webSiteAddress;
    }

    public String getDbAddres() {
        return dbAddres;
    }

    public void setDbAddres(String dbAddres) {
        this.dbAddres = dbAddres;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public String getDbUser() {
        return dbUser;
    }

    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }


}
