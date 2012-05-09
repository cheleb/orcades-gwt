package net.orcades.gwt.spring.dispatch.security.shared.login;

import java.util.ArrayList;

import net.customware.gwt.dispatch.shared.Result;

public class LoginResult implements Result {

	private boolean authenticated;

	private ArrayList<String> authorities;
	
	@SuppressWarnings("unused")
	private LoginResult() {
	}

	public LoginResult(boolean b, ArrayList<String> auths) {
		authenticated = b;
		authorities = auths;
	}

	
	public boolean isAuthenticated() {
		return authenticated;
	}

	public ArrayList<String> getAuthorities() {
		return authorities;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
