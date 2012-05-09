package net.orcades.gwt.spring.dispatch.security.shared.login;

import net.customware.gwt.dispatch.shared.Action;

public class LoginAction implements Action<LoginResult>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String login;
	
	private String password;

	@SuppressWarnings("unused")
	private LoginAction() {
	}
	
	public LoginAction(String login, String password) {
		super();
		this.login = login;
		this.password = password;
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

}
