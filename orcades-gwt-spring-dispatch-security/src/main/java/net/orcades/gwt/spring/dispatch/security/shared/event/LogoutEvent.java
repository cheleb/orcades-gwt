package net.orcades.gwt.spring.dispatch.security.shared.event;

import com.google.gwt.event.shared.GwtEvent;

public class LogoutEvent extends GwtEvent<LogoutEventHandler> {

	public static final Type<LogoutEventHandler> TYPE = new Type<LogoutEventHandler>();
	
	private String message;
	
	@SuppressWarnings("unused")
	private LogoutEvent() {
	}
	
	public LogoutEvent(String message) {
		this.message = message;
	}

	@Override
	protected void dispatch(LogoutEventHandler accessDeniedEventEventHandler) {
		accessDeniedEventEventHandler.onLogout(this);
	}

	@Override
	public Type<LogoutEventHandler> getAssociatedType() {
		return TYPE;
	}

	

	public String getMessage() {
		return message;
	}

}
