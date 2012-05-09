package net.orcades.gwt.spring.dispatch.security.client.rpc;

import net.orcades.gwt.spring.shared.ApplicationException;

public class GWTAccessDeniedException extends GWTSecurityException implements ApplicationException {

	
	@SuppressWarnings("unused")
	private GWTAccessDeniedException() {
	}
	
	public GWTAccessDeniedException(String message) {
		super(message);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
