package ${package}.shared;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("/helo.gwtrpc")
public interface HeloService extends RemoteService {
	
	String sayHelo(String to);

}
