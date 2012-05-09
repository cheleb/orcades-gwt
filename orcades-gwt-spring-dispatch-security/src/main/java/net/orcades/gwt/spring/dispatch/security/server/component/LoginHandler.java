package net.orcades.gwt.spring.dispatch.security.server.component;

import java.util.ArrayList;
import java.util.Collection;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.ActionException;
import net.orcades.gwt.spring.dispatch.security.shared.login.LoginAction;
import net.orcades.gwt.spring.dispatch.security.shared.login.LoginResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.NullRememberMeServices;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

@Component
//@Scope("request")
public class LoginHandler implements ActionHandler<LoginAction, LoginResult> {

	@Autowired
	@Qualifier("authenticationManager")
	private AuthenticationManager authenticationManager;

	@Autowired
	private ApplicationEventPublisher eventPublisher;
	
	@Autowired(required=false)
	private RememberMeServices rememberMeServices = new NullRememberMeServices();
	
	@Autowired
	private RequestScoped requestScoped;
	
	protected AuthenticationDetailsSource authenticationDetailsSource = new WebAuthenticationDetailsSource();
	
//	@Autowired
//	private HttpServletRequest request;
	
	//private HttpServletResponse response;
	
	public LoginResult execute(LoginAction action, ExecutionContext context)
			throws ActionException {
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				action.getLogin(), action.getPassword());

			
		Authentication authResult = authenticationManager
				.authenticate(usernamePasswordAuthenticationToken);
		LoginResult loginResult = null;
		if (authResult.isAuthenticated()) {
			Collection<? extends GrantedAuthority> grantedAuthorities = authResult.getAuthorities();
			ArrayList<String> list = new ArrayList<String>();
			for (GrantedAuthority grantedAuthority : grantedAuthorities) {
				list.add(grantedAuthority.getAuthority());
			}
			loginResult = new LoginResult(true, list);
			SecurityContextHolder.getContext().setAuthentication(authResult);
			eventPublisher
			.publishEvent(new InteractiveAuthenticationSuccessEvent(
					authResult, this.getClass()));
			//rememberMeServices.loginSuccess(request, response, authResult);
			//usernamePasswordAuthenticationToken.setDetails(authenticationDetailsSource);
		} else {
			loginResult = new LoginResult(false, null);
		}

		return loginResult;
	}

	public Class<LoginAction> getActionType() {
		return LoginAction.class;
	}

	public void rollback(LoginAction action, LoginResult result,
			ExecutionContext context) throws ActionException {

	}

}
