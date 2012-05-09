package net.orcades.gwt.spring.demo.client.mvp;

import net.customware.gwt.presenter.client.EventBus;
import net.orcades.gwt.spring.demo.client.ui.MyUI;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.inject.Inject;

public class ListView extends FlexTable {

	@Inject
	MyUI myUI;
	
	
	public ListView() {
		
	}
	
	
	public void init(){
		setWidget(0, 0, myUI);
	}
	
	public EventBus getEventBus() {
		return myUI.getEventBus();
	}
}
