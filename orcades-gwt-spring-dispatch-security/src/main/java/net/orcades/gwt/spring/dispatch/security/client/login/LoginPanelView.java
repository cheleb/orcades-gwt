package net.orcades.gwt.spring.dispatch.security.client.login;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * @author olivier nouguier olivier@orcades.net Simple login panel. On
 *         successful login, the panel will (re)invoke the click that opened the
 *         login box (same behavior of webapp login form).
 * 
 */
public class LoginPanelView extends PopupPanel implements LoginPanelPresenter.Display {

	/**
	 * Top container.
	 */
	private FlexTable table = new FlexTable();
	/**
	 * Login entry box.
	 */
	private TextBox loginTextBox;
	/**
	 * Password entry box.
	 */
	private PasswordTextBox passwordTextBox;
	/**
	 * 
	 */
	private TextBox logMessage;
	private PushButton cancelButton;
	private PushButton submitButton;

	/**
	 * Constructor.
	 * 
	 * @param authServiceEndPoint
	 *            authentication end point.
	 * @param message
	 *            Message to show in header.
	 * @param clickListener
	 *            to call on successful authentication, may be null.
	 */
	public LoginPanelView() {
		setWidget(table);
		table.setWidget(0, 0, new Label("MVP Inside"));
		table.getFlexCellFormatter().setColSpan(0, 0, 2);
		table.setWidget(1, 0, new Label("Login"));
		table.setWidget(1, 1, loginTextBox = new TextBox());

		table.setWidget(2, 0, new Label("Password"));
		table.setWidget(2, 1, passwordTextBox = new PasswordTextBox());

		table.setWidget(3, 0, cancelButton = new PushButton("Cancel"));
		table.setWidget(3, 1, submitButton = new PushButton("submit"));
		table.setWidget(4, 0, logMessage = new TextBox());
		logMessage.setEnabled(false);
		table.getFlexCellFormatter().setColSpan(4, 0, 2);
		
	}

	public Widget asWidget() {
		return this;
	}

	public void startProcessing() {
		// TODO Auto-generated method stub
		
	}

	public void stopProcessing() {
		// TODO Auto-generated method stub
		
	}

	public HasClickHandlers getCancel() {
		return cancelButton;
	}

	public HasValue<String> getLogin() {
		return loginTextBox;
	}

	public HasValue<String> getPassword() {
		return passwordTextBox;
	}

	public HasClickHandlers getSubmit() {
		return submitButton;
	}

	public HasValue<String> getLogMessage() {
		return logMessage;
	}

	public PopupPanel getPopup() {
		return this;
	}

}
