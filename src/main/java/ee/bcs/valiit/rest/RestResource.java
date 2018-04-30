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

import ee.bcs.valiit.model.Company;
import ee.bcs.valiit.model.MessageDTO;
import ee.bcs.valiit.services.CompanyService;

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
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/valiit", "root", "tere")) {
            try (Statement stmt = conn.createStatement()) {
                try (ResultSet rs = stmt.executeQuery("SELECT simple_column FROM simpledemo")) {
                    rs.first();
                    String result = rs.getString(1);
                    conn.close();
                    return result;
                }
            }
        }
    }


    @GET
    @Path("/get_companies")
    @Produces(MediaType.APPLICATION_JSON) // anname välja formaadis APPLICATION_JSON
    public List<Company> getCompanies() {
        return CompanyService.getCompanies();
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
}
