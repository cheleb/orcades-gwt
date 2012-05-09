package net.orcades.gwt.spring.dispatch.security.shared.event;

import com.google.gwt.event.shared.GwtEvent;

public class AccessDeniedEvent extends GwtEvent<AccessDeniedEventEventHandler> {

	public static final Type<AccessDeniedEventEventHandler> TYPE = new Type<AccessDeniedEventEventHandler>();
	
	private String message;
	
	@SuppressWarnings("unused")
	private AccessDeniedEvent() {
	}
	
	public AccessDeniedEvent(String message) {
		
		this.message = message;
	}

	@Override
	protected void dispatch(AccessDeniedEventEventHandler accessDeniedEventEventHandler) {
		accessDeniedEventEventHandler.onAccessDenied(this);
	}

	@Override
	public Type<AccessDeniedEventEventHandler> getAssociatedType() {
		return TYPE;
	}

	


	

	public String getMessage() {
		return message;
	}

}
