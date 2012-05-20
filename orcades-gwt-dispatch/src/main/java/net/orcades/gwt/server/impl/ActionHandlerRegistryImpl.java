package net.orcades.gwt.server.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import net.orcades.gwt.server.ActionHandler;
import net.orcades.gwt.server.ActionHandlerRegistry;
import net.orcades.gwt.shared.Action;
import net.orcades.gwt.shared.Result;

public class ActionHandlerRegistryImpl implements ActionHandlerRegistry {

	
	private Map<Class<?>, Object> actionHandlers = new HashMap<Class<?>, Object>();
	
	@Inject
	private List<ActionHandler> handlers;
	
	
	@PostConstruct
	public void init() {
		for (ActionHandler actionHandler : handlers) {
			registerHandler(actionHandler);
		}
	}
	
	@Override
	public <A extends Action<R>, R extends Result> ActionHandler<A, R> getActionHandler(
			A action) {
		return (ActionHandler<A, R>) actionHandlers.get(action.getClass());
	}

	@Override
	public <A extends Action<R>, R extends Result> void registerHandler(
			ActionHandler<A, R> handler) {
		actionHandlers.put(handler.getActionType(), handler);
		
	}
	
	

	@Override
	public <A extends Action<R>, R extends Result> boolean unregisterHandler(
			ActionHandler<A, R> handler) {
		return actionHandlers.remove(handler.getActionType()) != null;
	}

	@Override
	public String toString() {
		return ActionHandlerRegistryImpl.class.getName() + ": " + handlers.size();
	}
}
