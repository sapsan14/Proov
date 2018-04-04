package ee.bcs.valiit.web;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.server.mvc.Viewable;

@Path("/")
public class WebResource {

	@GET
	@Path("/contact/form")
	@Produces(MediaType.TEXT_HTML)
	public Viewable getHello() {
		return new Viewable("/contact_form.ftl");
	}

	@POST
	@Path("/contact/view")
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Viewable setPurchase(@FormParam("first_name") String firstName, @FormParam("last_name") String lastName,
			@FormParam("phone") String phone, @FormParam("email") String email) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("first_name", firstName);
		map.put("last_name", lastName);
		map.put("phone", phone);
		map.put("email", email);
		return new Viewable("/contact_view.ftl", map);
	}
	
	@GET
	@Path("/numbers/add")
	@Produces(MediaType.TEXT_HTML)
	public Viewable getNumbersAdd() {
		return new Viewable("/rest_demo.ftl");
	}
}
