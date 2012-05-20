package net.orcades.gwt.shared;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;



@RemoteServiceRelativePath("/dispatch.gwtrpc")
public interface ActionDispatchService extends RemoteService {
	Result execute(Action<Result> action) throws Exception;
}
