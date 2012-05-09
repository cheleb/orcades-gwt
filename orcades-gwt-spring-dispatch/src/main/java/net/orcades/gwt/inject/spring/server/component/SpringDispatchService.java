package net.orcades.gwt.inject.spring.server.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.customware.gwt.dispatch.client.service.DispatchService;
import net.customware.gwt.dispatch.server.Dispatch;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.Result;
/**
 * Spring implementation of the <b>client</b> {@link DispatchService}.<br />
 * This implementation simply delegate the call to injected <b>Server</b> {@link Dispatch}.
 * @author Olivier NOUGUIER
 *
 */
@Component
public class SpringDispatchService implements DispatchService {

	/**
	 * Dispatch <b>Server</b> implementation, injected at construction.
	 */
	private final Dispatch dispatch;
	
	/**
	 * Constructor.
	 * @param dispatch injected.
	 */
	@Autowired
	public SpringDispatchService(Dispatch dispatch) {
		this.dispatch = dispatch;
	}
	
	/**
	 * Execution of the command.
	 * @param action to execute. 
	 */
	public Result execute(Action<?> action) throws Exception {
		return dispatch.execute(action);
	}

}
