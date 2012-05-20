package net.orcades.gwt.server;


import net.orcades.gwt.shared.Action;
import net.orcades.gwt.shared.Result;

public interface ActionHandler<A extends Action<R>, R extends Result> {
	public Class<A> getActionType();

	public R execute();
}
