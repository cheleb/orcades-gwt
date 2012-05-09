package net.orcades.gwt.spring.demo.server;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.ActionException;
import net.orcades.gwt.spring.demo.shared.rpc.ZozoAction;
import net.orcades.gwt.spring.demo.shared.rpc.ZozoResult;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;


@Component
public class ZozoHandler implements ActionHandler<ZozoAction, ZozoResult> {

	
	@Secured(value="ROLE_ADMIN")
	public ZozoResult execute(ZozoAction action, ExecutionContext arg1)
			throws ActionException {
		
		
		
		return new ZozoResult(getResponse(action.getMessage()));
	}

	@Secured(value="ROLE_ADMIN") //Useless until aspect j support ... https://jira.springsource.org/browse/SEC-1232 
	private String getResponse(String string) {
		return "Server received : " + string;
	}


	public Class<ZozoAction> getActionType() {
		return ZozoAction.class;
	}

	
	public void rollback(ZozoAction arg0, ZozoResult arg1, ExecutionContext arg2)
			throws ActionException {
		
	}
	

}
