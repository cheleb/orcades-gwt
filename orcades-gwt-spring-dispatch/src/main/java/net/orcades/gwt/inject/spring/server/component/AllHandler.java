package net.orcades.gwt.inject.spring.server.component;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import net.orcades.gwt.server.ActionHandler;
import net.orcades.gwt.server.ActionHandlerRegistry;
import net.orcades.gwt.shared.Action;
import net.orcades.gwt.shared.Result;

@Named
public class AllHandler {

	@Inject
	List<ActionHandler<? extends Action<? extends Result>, ? extends Result>> handlers;

	@Inject
	private ActionHandlerRegistry actionHandlerRegistry;

	@PostConstruct
	public void init() {
		for (ActionHandler<? extends Action<?>, ? extends Result> handler : handlers) {
			actionHandlerRegistry.registerHandler(handler);
		}
	}

}
