package net.orcades.gwt.spring.demo.client.mvp;

import net.customware.gwt.presenter.client.EventBus;
import net.orcades.gwt.inject.spring.shared.event.ApplicationStartEvent;

import com.google.inject.Inject;

public class ApplicationPresenter {

	
	
	
	@Inject
	private EventBus eventBus;
	
	public void start() {
		eventBus.fireEvent(new ApplicationStartEvent());
	}
	
}
