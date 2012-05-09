package net.orcades.gwt.spring.demo.client.ui;

import net.customware.gwt.presenter.client.EventBus;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.datepicker.client.DatePicker;
import com.google.inject.Inject;


public class MyUI extends Composite {

	private static MyUIUiBinder uiBinder = GWT.create(MyUIUiBinder.class);

	interface MyUIUiBinder extends UiBinder<Widget, MyUI> {
	}

	@UiField
	Button button;

	@UiField
	DatePicker datePicker;
	
	@UiField
	DateBox dateBox;
	
	@Inject
	EventBus eventBus;
	
	
	
	
	public MyUI() {
		initWidget(uiBinder.createAndBindUi(this));
		button.setText("event");
		
	}

	public EventBus getEventBus() {
		return eventBus;
	}
	
	@UiHandler("button")
	void onClick(ClickEvent e) {
		Window.alert(dateBox.getValue().toString());
	}

}
