package net.orcades.gwt.spring.demo.shared.event;

import com.google.gwt.event.shared.GwtEvent;

public class ZozoEvent extends GwtEvent<ZozoEventHandler>{

	
	public static final Type<ZozoEventHandler> TYPE = new Type<ZozoEventHandler>();

	private String message;
	
	
	@SuppressWarnings("unused")
	private ZozoEvent() {
	}
	
	public ZozoEvent(String message) {
		this.message = message;
	}

	@Override
	protected void dispatch(ZozoEventHandler handler) {
		handler.onZozoEvent(this);
		
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ZozoEventHandler> getAssociatedType() {
		return TYPE;
	}

	

	public String getMessage() {
		return message;
	}

}
