package net.orcades.gwt.spring.dispatch.security.client.rpc;

import net.orcades.gwt.spring.shared.ApplicationException;

public class GWTAuthorizationRequiredException extends GWTSecurityException implements ApplicationException {

	
	@SuppressWarnings("unused")
	private GWTAuthorizationRequiredException() {
	}
	
	public GWTAuthorizationRequiredException(String message) {
		super(message);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
