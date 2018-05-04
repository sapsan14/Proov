package ee.bcs.valiit.rest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ee.bcs.valiit.model.*;
import ee.bcs.valiit.services.CompanyService;
import ee.bcs.valiit.services.OmniMeterService;

@Path("/")
public class RestResource {

    @GET
    @Path("/hi/text")
    @Produces(MediaType.TEXT_PLAIN)
    public String getHi(@DefaultValue("World") @QueryParam("name") String name) {
        return String.format("Hello, %s!", name);
    }

    @GET
    @Path("/hi/json/map/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHi2(@PathParam(value = "name") String name) {
        Map<String, String> response = new HashMap<>();
        response.put("greeting", String.format("Hello, %s!", name));
        return Response.ok(response).build();
    }

    @GET
    @Path("/hi/json/dto")
    @Produces(MediaType.APPLICATION_JSON)
    public MessageDTO getHi3(@DefaultValue("World") @QueryParam("name") String name) {
        MessageDTO message = new MessageDTO();
        message.setText("Hello, " + name + "!!!");
        return message;
    }

    @POST
    @Path("/message")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String message(MessageDTO message) {
        return "Sõnum oli järgmine: " + message.getText();
    }

    @POST
    @Path("/addnumbers")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addNumbers(@FormParam("number1") String number1, @FormParam("number2") String number2) {
        int value1 = Integer.parseInt(number1);
        int value2 = Integer.parseInt(number2);
        int result = value1 + value2;
        Map<String, String> response = new HashMap<>();
        response.put("result", String.valueOf(result));
        response.put("comment", "See summa arvutati kokku RestResource klassis serveri pool.");
        return Response.ok(response).build();
    }

    @GET
    @Path("/message/db")
    @Produces(MediaType.TEXT_PLAIN)
    public String getTextFromDb() throws SQLException, ClassNotFoundException {
        // Class.forName("com.mysql.cj.jdbc.Driver");
        Class.forName("org.mariadb.jdbc.Driver");
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "tere")) {
            try (Statement stmt = conn.createStatement()) {
                try (ResultSet rs = stmt.executeQuery("SELECT id FROM meeting_owner")) {
                    rs.next();
                    String result = rs.getString(1);
                    conn.close();
                    return result;
                }
            }
        }
    }


    @GET
    @Path("/get_users")
    @Produces(MediaType.APPLICATION_JSON) // anname välja formaadis APPLICATION_JSON
    public List<User> getCompanies() {
        return OmniMeterService.getUsers();
    }

    @POST
    @Path("/add_user")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String addUser(User user) {
        OmniMeterService.addUser(user);
        return "OK";
    }

    @POST
    @Path("/add_feedback")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String addFeedback(Feedbackform feedback) {
        OmniMeterService.addFeedBack(feedback);
        return "OK";
    }

    @GET
    @Path("/get_meeting_by_id")
    @Produces(MediaType.APPLICATION_JSON) // anname välja formaadis APPLICATION_JSON
    public Meeting getMeeting(@QueryParam("meeting_id") int meeting_id){
        return OmniMeterService.getMeetingById(meeting_id);
    }


    @POST
    @Path("/modify_user")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String modifyUser(User user) {
        OmniMeterService.modifyUser(user);
        return "OK";
    }
    @GET
    @Path("/get_user")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@QueryParam("user_id") int userId){
        return OmniMeterService.getUser(userId);
    }

    @POST
    @Path("/delete_user")
    @Produces(MediaType.TEXT_PLAIN)
    public String deleteUser(@FormParam("user_id") int userId){
        OmniMeterService.deleteUser(userId);
        return "OK";
    }


    @GET
    @Path("/get_company")
    @Produces(MediaType.APPLICATION_JSON)
    public Company getCompany(@QueryParam("company_id") int companyID){
        return CompanyService.getCompany(companyID);
    }


    @POST
    @Path("/add_company")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String addCompany(Company company) {

        CompanyService.addCompany(company);
        return "OK";
    }

    @POST
    @Path("/modify_company")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String modifyCompany(Company company) {
        CompanyService.modifyCompany(company);
        return "OK";
    }

    @POST
    @Path("/delete_company")
    @Produces(MediaType.TEXT_PLAIN)
    public String deleteCompany(@FormParam("company_id") int companyId){
        CompanyService.deleteCompany(companyId);
        return "OK";
    }

    @GET
    @Path("/give_feedback")
    @Produces(MediaType.TEXT_PLAIN)
    public int feedBackByNumber(@DefaultValue("") @QueryParam("feedBack") String feedBack) {
        int a = Integer.parseInt(feedBack);
        return a;
    }
}
