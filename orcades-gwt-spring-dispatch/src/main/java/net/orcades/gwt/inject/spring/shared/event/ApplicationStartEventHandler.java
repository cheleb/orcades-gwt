package net.orcades.gwt.inject.spring.shared.event;

import com.google.gwt.event.shared.EventHandler;

public interface ApplicationStartEventHandler extends EventHandler {
	public void onApplicationStart(ApplicationStartEvent event);
}
