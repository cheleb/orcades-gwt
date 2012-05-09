package net.orcades.gwt.spring.dispatch.security.shared.event;

import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.Result;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class AuthorizationRequiredEvent extends
		GwtEvent<AuthorizationRequiredEventEventHandler> {

	public static final Type<AuthorizationRequiredEventEventHandler> TYPE = new Type<AuthorizationRequiredEventEventHandler>();


	private String message;

	private Action<?> action;


	private AsyncCallback<? extends Result> callback;
	
	@SuppressWarnings("unused")
	private AuthorizationRequiredEvent() {
	}

	public <R extends Result> AuthorizationRequiredEvent(Action<?> action,AsyncCallback<R> callback, String message) {
		this.message = message;
		this.action = action;
		this.callback = callback;
	}

	

	public AuthorizationRequiredEvent(String string) {
		// TODO Auto-generated constructor stub
	}

	public Action<?> getAction() {
		return action;
	}
	
	public AsyncCallback<? extends Result> getCallback() {
		return callback;
	}
	
	@Override
	protected void dispatch(AuthorizationRequiredEventEventHandler handler) {
		handler.onAuthorizationRequired(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<AuthorizationRequiredEventEventHandler> getAssociatedType() {
		return TYPE;
	}

	public String getMessage() {
		return message;
	}
}
