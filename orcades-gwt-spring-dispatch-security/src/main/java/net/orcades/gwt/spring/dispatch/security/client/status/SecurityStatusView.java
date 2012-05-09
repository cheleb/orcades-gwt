package net.orcades.gwt.spring.dispatch.security.client.status;

import java.util.ArrayList;

import net.orcades.gwt.spring.dispatch.security.client.status.SecurityStatusPresenter.Display;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class SecurityStatusView extends Composite implements
		SecurityStatusPresenter.Display {

	private TextBox status;
	private Button logout;
	
	private ListBox authorities;

	public SecurityStatusView() {
		VerticalPanel panel = new VerticalPanel();

		this.status = new TextBox();
		status.setText("Not logued");
		this.logout = new Button("Logout");
		
		this.authorities = new ListBox();
		

		panel.add(status);
		panel.add(authorities);
		panel.add(logout);

		initWidget(panel);

	}


	

	
	public HasClickHandlers getLogInOut() {
		return logout;
	}

	public HasValue<String> getStatus() {
		// TODO Auto-generated method stub
		return status;
	}

	public Widget asWidget() {
		// TODO Auto-generated method stub
		return this;
	}

	public void startProcessing() {
		// TODO Auto-generated method stub

	}

	public void stopProcessing() {
		// TODO Auto-generated method stub

	}

	public void setEnabled(boolean b) {
		if(b){
			logout.setText("logout");
		}else{
			logout.setText("login");
		}
		

	}

	public void setAuthorities(ArrayList<String> auths) {
		
		authorities.clear();
		if (auths != null) {
			for (String auth : auths) {
				authorities.addItem(auth);
			}
			authorities.setVisibleItemCount(auths.size());
		}else {
			authorities.setVisibleItemCount(0);
		}
		

	}





	public void setRoleVisible(boolean b) {
		authorities.setVisible(b);
		
	}

}
