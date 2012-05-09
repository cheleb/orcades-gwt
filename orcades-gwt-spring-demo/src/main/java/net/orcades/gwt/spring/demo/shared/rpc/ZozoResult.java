package net.orcades.gwt.spring.demo.shared.rpc;

import net.customware.gwt.dispatch.shared.Result;

public class ZozoResult implements Result {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String message;

	
	
	public ZozoResult(String message) {
		super();
		this.message = message;
	}

	@SuppressWarnings("unused")
	private ZozoResult() {
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message + " 3";
	}

}
