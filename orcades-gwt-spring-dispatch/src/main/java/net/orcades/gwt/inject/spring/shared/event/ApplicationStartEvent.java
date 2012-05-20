package net.orcades.gwt.inject.spring.shared.event;

import com.google.gwt.event.shared.GwtEvent;

public class ApplicationStartEvent extends GwtEvent<ApplicationStartEventHandler> {

	public static final Type<ApplicationStartEventHandler> TYPE = new Type<ApplicationStartEventHandler>();
	
	@Override
	protected void dispatch(ApplicationStartEventHandler handler) {
		handler.onApplicationStart(this);
	}

	@Override
	public Type<ApplicationStartEventHandler> getAssociatedType() {
		return TYPE;
	}

}
