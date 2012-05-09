package net.orcades.gwt.spring.demo.shared.rpc;

import net.customware.gwt.dispatch.shared.Action;

public class ZozoAction implements Action<ZozoResult> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String message;

	
	@SuppressWarnings("unused")
	private ZozoAction() {
		
	}

	public ZozoAction(String value) {
		this.message = value;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}


}
