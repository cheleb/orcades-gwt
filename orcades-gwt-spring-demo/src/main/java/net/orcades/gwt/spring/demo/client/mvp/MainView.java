package net.orcades.gwt.spring.demo.client.mvp;

import net.orcades.gwt.spring.demo.client.ui.MyUI;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class MainView extends Composite implements MainPresenter.Display {

	
	private TextBox messageBox;
	
	private Button buttonSend;
	
	private PushButton showList;

	
	private MyUI myUI;
	
	@Inject
	public MainView(MyUI myUI) {
		final FlowPanel panel = new FlowPanel();
		this.myUI = myUI;
		initWidget(panel);
		messageBox = new TextBox();
		messageBox.setText("<3");
		buttonSend = new Button("send now");
	
		showList = new PushButton("show list");
		panel.add(messageBox);
		panel.add(buttonSend);
		
		panel.add(showList);
		//panel.add(myUI);
	}
	
	
	public HasValue<String> messageBox() {
		return messageBox;
	}


	public Widget asWidget() {
		return this;
	}


	public void startProcessing() {
		
	}


	public void stopProcessing() {
		
	}


	public HasClickHandlers getSend() {
		return buttonSend;
	}


	


	public HasClickHandlers getShowList() {
		return showList;
	}


	

}
