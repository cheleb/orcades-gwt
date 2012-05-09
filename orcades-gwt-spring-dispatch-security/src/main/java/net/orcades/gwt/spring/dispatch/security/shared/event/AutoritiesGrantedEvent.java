package net.orcades.gwt.spring.dispatch.security.shared.event;

import java.util.ArrayList;

import com.google.gwt.event.shared.GwtEvent;

public class AutoritiesGrantedEvent extends GwtEvent<AutoritiesGrantedEventEventHandler>{

	private ArrayList<String> authorities;

	public static Type<AutoritiesGrantedEventEventHandler> TYPE = new Type<AutoritiesGrantedEventEventHandler>();
	
	@SuppressWarnings("unused")
	private AutoritiesGrantedEvent() {
	}
	
	public AutoritiesGrantedEvent(ArrayList<String> auths) {
		this.authorities = auths;
	}

	@Override
	protected void dispatch(AutoritiesGrantedEventEventHandler handler) {
		handler.onAuthoritiesGranted(this);
		
	}
	
	public ArrayList<String> getAuthorities() {
		return authorities;
	}

	@Override
	public Type<AutoritiesGrantedEventEventHandler> getAssociatedType() {
		return TYPE;
	}

}
