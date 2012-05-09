package net.orcades.gwt.spring.dispatch.security.server.component;

import java.util.List;

import net.customware.gwt.dispatch.server.Dispatch;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.ActionException;
import net.customware.gwt.dispatch.shared.Result;
import net.orcades.gwt.spring.dispatch.security.client.rpc.GWTAccessDeniedException;
import net.orcades.gwt.spring.dispatch.security.client.rpc.GWTAuthorizationRequiredException;
import net.orcades.gwt.spring.dispatch.security.client.rpc.GWTSecurityException;
import net.orcades.gwt.spring.dispatch.security.client.service.ISpringSecuredDispatchService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class SpringSecuredDispatchService implements
		ISpringSecuredDispatchService {

	/**
	 * Dispatch <b>Server</b> implementation, injected at construction.
	 */
	private final Dispatch dispatch;

	@Autowired
	private GetAuthoritiesHandler getAuthoritiesHandler;
	
	@Autowired
	public SpringSecuredDispatchService(Dispatch dispatch) {
		this.dispatch = dispatch;
	}

	/**
	 * 
	 */
	public Result execute(Action<?> action) throws GWTSecurityException,
			ActionException {

		try {
			return dispatch.execute(action);
		} catch (AccessDeniedException accessDeniedException) {
			List<String> roles = getAuthoritiesHandler.getRoles();

			if(roles.size() > 0 ) {
				throw new GWTAccessDeniedException(accessDeniedException
						.getMessage());	
			}else {
				throw new GWTAuthorizationRequiredException("Auth required");
			}
			
		} catch (AuthenticationCredentialsNotFoundException authenticationCredentialsNotFoundException) {
			
			throw new GWTAuthorizationRequiredException("Auth required");
		}

	}

}
