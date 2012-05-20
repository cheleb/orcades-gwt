package net.orcades.gwt.server;

import net.orcades.gwt.shared.Action;
import net.orcades.gwt.shared.Result;

public interface ActionHandlerRegistry {

	
	public <A extends Action<R>,R extends Result> ActionHandler<A,R> getActionHandler(A actionClass);
	
	/**
     * Register handler instance to the registry.
     * 
     * @param handler
     *            The action handler to register.
     */
    public <A extends Action<R>,R extends Result> void registerHandler( ActionHandler<A, R> handler );

    /**
     * Unregister the specified handler.
     * 
     * @param handler
     *            The handler to unregister.
     * @return <code>true</code> if handler was present.
     */
    public <A extends Action<R>,R extends Result> boolean unregisterHandler( ActionHandler<A, R> handler );
	
}
