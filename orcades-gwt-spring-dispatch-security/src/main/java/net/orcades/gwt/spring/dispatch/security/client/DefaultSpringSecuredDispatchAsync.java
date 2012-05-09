package net.orcades.gwt.spring.dispatch.security.client;

import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.Result;
import net.customware.gwt.presenter.client.EventBus;
import net.orcades.gwt.spring.dispatch.security.client.rpc.GWTAccessDeniedException;
import net.orcades.gwt.spring.dispatch.security.client.rpc.GWTAuthorizationRequiredException;
import net.orcades.gwt.spring.dispatch.security.client.service.ISpringSecuredDispatchService;
import net.orcades.gwt.spring.dispatch.security.client.service.ISpringSecuredDispatchServiceAsync;
import net.orcades.gwt.spring.dispatch.security.shared.event.AccessDeniedEvent;
import net.orcades.gwt.spring.dispatch.security.shared.event.AuthorizationRequiredEvent;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class DefaultSpringSecuredDispatchAsync implements DispatchAsync {

	private static final ISpringSecuredDispatchServiceAsync realService = GWT
			.create(ISpringSecuredDispatchService.class);

	@Inject
	private EventBus eventBus;

	public <A extends Action<R>, R extends Result> void execute(final A action,
			final AsyncCallback<R> callback) {
		realService.execute(action, new AsyncCallback<Result>() {
			public void onFailure(Throwable caught) {
				if (caught instanceof GWTAuthorizationRequiredException) {
					GWTAuthorizationRequiredException gse = (GWTAuthorizationRequiredException) caught;

					eventBus.fireEvent(new AuthorizationRequiredEvent(action, callback, gse.getMessage()));

				} else if (caught instanceof GWTAccessDeniedException) {
					GWTAccessDeniedException gse = (GWTAccessDeniedException) caught;
					eventBus.fireEvent(new AccessDeniedEvent(gse.getMessage()));
				} else {
					callback.onFailure(caught);
				}
			}

			public void onSuccess(Result result) {
				callback.onSuccess((R) result);
			}
		});

	}

}
