package ee.bcs.valiit.web;

import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.freemarker.FreemarkerMvcFeature;

public class WebApplication extends ResourceConfig {
	public WebApplication() {
		super(WebResource.class);
		register(LoggingFeature.class);
		register(FreemarkerMvcFeature.class);
	}
}