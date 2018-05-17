package ee.bcs.valiit.rest;

import ee.bcs.valiit.model.*;
import ee.bcs.valiit.services.AuthenticationService;
import ee.bcs.valiit.services.CompanyService;
import ee.bcs.valiit.services.FileService;
import ee.bcs.valiit.services.OmniMeterService;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                try (ResultSet rs = stmt.executeQuery("SELECT id FROM meeting")) {
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
    public List<User> getUsers(@Context HttpServletRequest req) {
        if (isUserAuthorized(req, "admin")) {
            return OmniMeterService.getUsers();
        } else {
            return new ArrayList<>();
        }
    }

    @GET
    @Path("/get_meeting_types")
    @Produces(MediaType.APPLICATION_JSON) // anname välja formaadis APPLICATION_JSON
    public List<MeetingType> getMeetingTypes() {
        return OmniMeterService.getMeetingTypes();
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
    @Path("/add_meeting")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String addMeeting(Meeting meeting) {
        OmniMeterService.addMeeting(meeting);
        return "OK";
    }


//    @GET
//    @Path("/get_meeting_by_id")
//    @Produces(MediaType.APPLICATION_JSON) // anname välja formaadis APPLICATION_JSON
//    public Meeting getMeeting(@QueryParam("meeting_id") int meeting_id){
//        return OmniMeterService.getMeetingById(meeting_id);
    // }

    @GET
    @Path("/get_meeting_by_uuid")
    @Produces(MediaType.APPLICATION_JSON) // anname välja formaadis APPLICATION_JSON
    public Meeting getMeeting(@QueryParam("meeting_uuid") String meeting_uuid) {
        return OmniMeterService.getMeetingByUuid(meeting_uuid);
    }

    @GET
    @Path("/get_meeting_stats_by_uuid")
    @Produces(MediaType.APPLICATION_JSON) // anname välja formaadis APPLICATION_JSON
    public Stats getStats(@QueryParam("meeting_uuid") String meeting_uuid) {
        return OmniMeterService.getStatsByUuid(meeting_uuid);
    }

    @GET
    @Path("/get_meeting_feedback_by_uuid")
    @Produces(MediaType.APPLICATION_JSON) // anname välja formaadis APPLICATION_JSON
    public List<Feedback> getFeedback(@QueryParam("meeting_uuid") String meeting_uuid) {
        return OmniMeterService.getFeedbackByUuid(meeting_uuid);
    }

    @GET
    @Path("/get_meetings")
    @Produces(MediaType.APPLICATION_JSON) // anname välja formaadis APPLICATION_JSON
    public List<Meeting> getMeetings(@Context HttpServletRequest req, @QueryParam("meeting_owner_id") int meetingOwnerId) {
        if (isUserAuthorized(req, "admin") || isUserAuthorized(req, "user")) {
            return OmniMeterService.getMeetings(meetingOwnerId);
        } else {
            return new ArrayList<>();
        }
    }


    @POST
    @Path("/modify_user")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String modifyUser(User user) {
        OmniMeterService.modifyUser(user);
        return "OK";
    }

    @POST
    @Path("/modify_meeting")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String modifyMeeting(Meeting meeting) {
        OmniMeterService.modifyMeeting(meeting);
        return "OK";
    }

    @GET
    @Path("/get_user")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@QueryParam("user_id") int userId) {
        return OmniMeterService.getUser(userId);
    }

    @POST
    @Path("/delete_user")
    @Produces(MediaType.TEXT_PLAIN)
    public String deleteUser(@FormParam("user_id") int userId) {
        OmniMeterService.deleteUser(userId);
        return "OK user deleted";
    }


    @POST
    @Path("/delete_meeting")
    @Produces(MediaType.TEXT_PLAIN)
    public String deleteMeeting(@FormParam("uuid") String uuid) {
        OmniMeterService.deleteMeeting(uuid);
        return "OK";
    }


    @GET
    @Path("/get_company")
    @Produces(MediaType.APPLICATION_JSON)
    public Company getCompany(@QueryParam("company_id") int companyID) {
        return CompanyService.getCompany(companyID);
    }


//    @POST
//    @Path("/add_company")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.TEXT_PLAIN)
//    public String addCompany(Company company) {
//
//        CompanyService.addCompany(company);
//        return "OK";
//    }

    @POST
    @Path("/add_company")
    @Consumes({ MediaType.MULTIPART_FORM_DATA })
//	@Produces(MediaType.TEXT_PLAIN)
    public String addCompany(
            @FormDataParam("id") int id,
            @FormDataParam("name") String name,
            @FormDataParam("employeeCount") int employeeCount,
            @FormDataParam("established") String established,
            @FormDataParam("logo") InputStream logo,
            @FormDataParam("logo") FormDataContentDisposition logoMetadata) {

        String fileName = FileService.writeFileBytes(logo, logoMetadata);
        Company company = new Company();
        company.setName(name);
        company.setEmployeeCount(employeeCount);
        company.setEstablished(established);
        company.setLogo(fileName);

        CompanyService.addCompany(company);
        return "OK";
    }


    @GET
    @Path("/get_companies")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Company> getCompanies(@Context HttpServletRequest req) {
        if (isUserAuthorized(req, "admin") || isUserAuthorized(req, "user")) {
            return CompanyService.getCompanies();
        } else {
            return new ArrayList<>();
        }
    }




    @POST
    @Path("/modify_company")
    @Consumes({ MediaType.MULTIPART_FORM_DATA })
//	@Produces(MediaType.TEXT_PLAIN)
    public String modifyCompany(@FormDataParam("id") int id,
                                @FormDataParam("name") String name,
                                @FormDataParam("employeeCount") int employeeCount,
                                @FormDataParam("established") String established,
                                @FormDataParam("logo") InputStream logo,
                                @FormDataParam("logo") FormDataContentDisposition logoMetadata) {
        String fileName = FileService.writeFileBytes(logo, logoMetadata);
        Company company = new Company();
        company.setId(id);
        company.setName(name);
        company.setEmployeeCount(employeeCount);
        company.setEstablished(established);
        if (fileName != null) {
            company.setLogo(fileName);
        }
        CompanyService.modifyCompany(company);
        return "OK";
    }


    @POST
    @Path("/delete_company")
    @Produces(MediaType.TEXT_PLAIN)
    public String deleteCompany(@FormParam("company_id") int companyId) {
        CompanyService.deleteCompany(companyId);
        return "OK";
    }

    @POST
    @Path("/add_feedback")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response addFeedback(Feedbackform feedback) {
        OmniMeterService.addFeedBack(feedback);
        String cookieName = "feedback" + feedback.getMeetingUuid();
        NewCookie myCookie = new NewCookie(cookieName, "true", "/", null, null, -1, false, false);
        return Response.ok("OK").cookie(myCookie).build();
    }


    @GET
    @Path("/get_all_meetings")
    @Produces(MediaType.APPLICATION_JSON) // anname välja formaadis APPLICATION_JSON
    public List<Meeting> getAllMeetings(@Context HttpServletRequest req) {
        if (isUserAuthorized(req, "admin")) {
            return OmniMeterService.getAllMeetings();
        } else {
            return new ArrayList<>();
        }
    }


/*    @GET
    @Path("/set_cookie")
    @Produces(MediaType.TEXT_PLAIN)
    public Response setCookie(@FormParam("meeting_uuid") String meetingUuid) {
        NewCookie myCookie = new NewCookie("feedbackcookie", meetingUuid, "/", null, null, -1, false, false);
        return Response.ok("Cookie set successfully!").cookie(myCookie).build();
    }*/

    @GET
    @Path("/read_cookie")
    @Produces(MediaType.TEXT_PLAIN)
    public String readCookie(@CookieParam("MY_TEST_COOKIE") String myCookie) {
        return "The cookie is " + myCookie;
    }


    @GET
    @Path("/set_session_info")
    @Produces(MediaType.TEXT_PLAIN)
    public String setSessionInfo(@Context HttpServletRequest req) {
        HttpSession session = req.getSession(true);
        String myTestAttr = (String) session.getAttribute("TEST");
        if (myTestAttr != null) {
            return "Session attribute found: " + myTestAttr;
        } else {
            // Session attribute was not found
            session.setAttribute("TEST", String.valueOf(Math.random()));
            return "Session attribute generated: " + (String) session.getAttribute("TEST");
        }
    }

    @POST
    @Path("/authenticate_user")
    @Produces(MediaType.TEXT_PLAIN)
    public String authenticateUser(@Context HttpServletRequest req, @FormParam("email") String email, @FormParam("password") String password) {
        User user = AuthenticationService.getUser(email, password);
        if (user == null) {
            // Autentimine ebaõnnestus
            return "FAIL";
        } else {
            // Autentimine õnnestus
            HttpSession session = req.getSession(true);
            session.setAttribute("AUTH_USER", user);
            return "SUCCESS";
        }
    }

    @GET
    @Path("/get_authenticated_user")
    @Produces(MediaType.APPLICATION_JSON)
    public User getAuthenticatedUser(@Context HttpServletRequest req) {
        HttpSession session = req.getSession(true);
        if (session.getAttribute("AUTH_USER") != null) {
            //Kasutaja on sisse loginud
            return (User) session.getAttribute("AUTH_USER");
        } else {
            // Kasutaja ei ole sisse loginud
            return new User(); //
        }
    }

    @GET
    @Path("/logout")
    @Produces(MediaType.TEXT_PLAIN)
    public String logout(@Context HttpServletRequest req) {
        HttpSession session = req.getSession(true);
        session.removeAttribute("AUTH_USER");
        return "SUCCESS";
    }

    private boolean isUserAuthorized(@Context HttpServletRequest req, String expectedRole) {
        HttpSession session = req.getSession(true);
        User user = null;
        if (session.getAttribute("AUTH_USER") != null) {
            // Kasutaja on sisse loginud
            user = (User) session.getAttribute("AUTH_USER");
            return user.getPersimissonsId().equals(expectedRole);
        }
        return false;
    }

    @GET
    @Path("/register_visit")
    @Produces(MediaType.TEXT_PLAIN)
    public String registerVisit() {
        return VisitCounter.addVisit();
    }

    @GET
    @Path("/get_image")
    @Produces("image/*")
    public Response getImage(@QueryParam("file_name") String fileName) throws IOException {
        byte[] imageBytes = FileService.readFileBytes(fileName);
        return Response.ok(imageBytes).build();
    }

    @GET
    @Path("/get_user_uuid_by_email")
    @Produces(MediaType.APPLICATION_JSON)
    public String getUserUuidByEmail(@QueryParam("email") String email) {
        return OmniMeterService.getUserUuidByEmail(email);
    }

    @POST
    @Path("/update_password")
    @Produces(MediaType.TEXT_PLAIN)
    public String updatePassowrd(@FormParam("user_uuid")String user_uuid, @FormParam("password")String password) {
        OmniMeterService.updatePassword(user_uuid, password);
        return "OK update password";
    }

}


