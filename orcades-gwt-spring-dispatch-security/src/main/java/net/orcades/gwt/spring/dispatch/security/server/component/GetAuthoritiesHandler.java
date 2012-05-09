package net.orcades.gwt.spring.dispatch.security.server.component;

import java.util.ArrayList;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.ActionException;
import net.orcades.gwt.spring.dispatch.security.shared.rpc.GetAuthoritiesAction;
import net.orcades.gwt.spring.dispatch.security.shared.rpc.GetAuthoritiesResult;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class GetAuthoritiesHandler implements
		ActionHandler<GetAuthoritiesAction, GetAuthoritiesResult> {

	/**
	 * Log4j logger.
	 */
	private static final Logger LOGGER = Logger
			.getLogger(GetAuthoritiesHandler.class);

	public GetAuthoritiesResult execute(GetAuthoritiesAction action,
			ExecutionContext context) throws ActionException {

		ArrayList<String> roles = getRoles();

		return new GetAuthoritiesResult(roles);
	}

	public Class<GetAuthoritiesAction> getActionType() {
		return GetAuthoritiesAction.class;
	}

	public void rollback(GetAuthoritiesAction action,
			GetAuthoritiesResult result, ExecutionContext context)
			throws ActionException {
		// TODO Auto-generated method stub

	}

	public ArrayList<String> getRoles() {
		ArrayList<String> roles = new ArrayList<String>();

		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();

		if (auth == null) {
			LOGGER.debug("No authentication found");
		} else {
			for (GrantedAuthority authority : auth.getAuthorities()) {
				if ("ROLE_ANONYMOUS".equals(authority.getAuthority())) {
					// FIXME should not be hard code...
					LOGGER.debug("ROLE_ANONYMOUS ignored");
				} else {
					roles.add(authority.getAuthority());
				}
			}
		}
		return roles;
	}

}
