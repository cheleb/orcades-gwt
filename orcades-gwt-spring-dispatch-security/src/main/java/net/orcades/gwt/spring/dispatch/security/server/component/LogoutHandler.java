package net.orcades.gwt.spring.dispatch.security.server.component;

import java.util.List;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.ActionException;
import net.orcades.gwt.spring.dispatch.security.shared.logout.LogoutAction;
import net.orcades.gwt.spring.dispatch.security.shared.logout.LogoutResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class LogoutHandler implements ActionHandler<LogoutAction, LogoutResult> {

	@Autowired
	private RequestScoped requestScoped;
	
	@Autowired
	private List<org.springframework.security.web.authentication.logout.LogoutHandler> handlers;
	
	public LogoutResult execute(LogoutAction action, ExecutionContext context)
			throws ActionException {

		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();

		for (org.springframework.security.web.authentication.logout.LogoutHandler handler : handlers) {
			
			handler.logout(requestScoped.getHttpServletRequest(), null, auth);
		}
		
		

		return new LogoutResult();
	}

	public Class<LogoutAction> getActionType() {

		return LogoutAction.class;
	}

	public void rollback(LogoutAction action, LogoutResult result,
			ExecutionContext context) throws ActionException {
		// TODO Auto-generated method stub

	}

}
