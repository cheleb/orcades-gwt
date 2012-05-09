package net.orcades.gwt.spring.dispatch.security.client.service;

import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.ActionException;
import net.customware.gwt.dispatch.shared.Result;
import net.orcades.gwt.spring.dispatch.security.client.rpc.GWTSecurityException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("dispatch.gwtrpc")
public interface ISpringSecuredDispatchService extends RemoteService {
	Result execute( Action<?> action ) throws GWTSecurityException, ActionException;
}
