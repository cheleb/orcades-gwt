package net.orcades.gwt.spring.dispatch.security.shared.rpc;

import java.util.ArrayList;

import net.customware.gwt.dispatch.shared.Result;

public class GetAuthoritiesResult implements Result {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ArrayList<String> authorities;

	@SuppressWarnings("unused")
	private GetAuthoritiesResult() {

	}

	public GetAuthoritiesResult(ArrayList<String> authorities) {
		this.authorities = authorities;
	}

	public ArrayList<String> getAuthorities() {
		return authorities;
	}

	@Override
	public String toString() {
		StringBuffer stringBuffer = new StringBuffer("GetAuthoritiesResult: ");
		stringBuffer.append(authorities);
		return stringBuffer.toString();
	}

}
