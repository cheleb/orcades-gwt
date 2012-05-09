package net.orcades.gwt.inject.spring.server.component;

import java.util.List;

import javax.annotation.PostConstruct;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ActionHandlerRegistry;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AllHandler {

	@Autowired(required=false)
	List<ActionHandler<? extends Action<? extends Result>, ? extends Result>> handlers;

//	@Autowired
//	public <A extends Action<R>, R extends Result> void setList(
//			List<ActionHandler<A, R>> handlers) {
//
//	}

	@Autowired
	private ActionHandlerRegistry actionHandlerRegistry;

	@SuppressWarnings("restriction")
	@PostConstruct
	public void init() {
		for (ActionHandler<? extends Action<?>, ? extends Result> handler : handlers) {
			actionHandlerRegistry.addHandler(handler);
		}
	}

}
