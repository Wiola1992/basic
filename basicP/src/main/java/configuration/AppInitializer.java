package configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

@Configuration
@EnableWebMvc
public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] {DBContext.class, SecurityConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] {WebConfig.class};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}
	
	
}
