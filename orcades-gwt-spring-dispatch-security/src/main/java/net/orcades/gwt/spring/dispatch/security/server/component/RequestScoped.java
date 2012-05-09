package net.orcades.gwt.spring.dispatch.security.server.component;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class RequestScoped {

	@Autowired
	private HttpServletRequest httpServletRequest;
	
	
	
	public HttpServletRequest getHttpServletRequest() {
		return httpServletRequest;
	}
	
	
}
