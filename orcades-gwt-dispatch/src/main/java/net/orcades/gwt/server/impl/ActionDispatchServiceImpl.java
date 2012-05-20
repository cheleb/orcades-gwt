package net.orcades.gwt.server.impl;

import javax.inject.Inject;

import net.orcades.gwt.server.ActionHandler;
import net.orcades.gwt.server.ActionHandlerRegistry;
import net.orcades.gwt.shared.Action;
import net.orcades.gwt.shared.ActionDispatchService;
import net.orcades.gwt.shared.Result;

public class ActionDispatchServiceImpl implements ActionDispatchService {

	
	@Inject
	private ActionHandlerRegistry actionHandlerRegistry;
	
//	@Override
//	public <A extends Action<R>, R extends Result> R execute(A action) {
//	
//		
//		
//		
//	}

	@Override
	public Result execute(Action<Result> action) throws Exception {
		ActionHandler<?,?> actionHandler = actionHandlerRegistry.getActionHandler(action);
		
		return actionHandler.execute();
		// TODO Auto-generated method stub
	}


	
}
