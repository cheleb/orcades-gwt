package net.orcades.gwt.spring.dispatch.security.client.login;

import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.presenter.client.DisplayCallback;
import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.place.Place;
import net.customware.gwt.presenter.client.place.PlaceRequest;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;
import net.customware.gwt.presenter.client.widget.WidgetPresenter;
import net.orcades.gwt.spring.dispatch.security.client.StringUtils;
import net.orcades.gwt.spring.dispatch.security.shared.event.AccessDeniedEvent;
import net.orcades.gwt.spring.dispatch.security.shared.event.AccessDeniedEventEventHandler;
import net.orcades.gwt.spring.dispatch.security.shared.event.AuthorizationRequiredEvent;
import net.orcades.gwt.spring.dispatch.security.shared.event.AuthorizationRequiredEventEventHandler;
import net.orcades.gwt.spring.dispatch.security.shared.event.AutoritiesGrantedEvent;
import net.orcades.gwt.spring.dispatch.security.shared.event.AutoritiesGrantedEventEventHandler;
import net.orcades.gwt.spring.dispatch.security.shared.login.LoginAction;
import net.orcades.gwt.spring.dispatch.security.shared.login.LoginResult;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.inject.Inject;

public class LoginPanelPresenter extends
		WidgetPresenter<LoginPanelPresenter.Display> {

	public static final Place PLACE = new Place("security");

	public interface Display extends WidgetDisplay {
		public HasValue<String> getLogin();

		public HasValue<String> getPassword();

		public HasValue<String> getLogMessage();

		public HasClickHandlers getSubmit();

		public HasClickHandlers getCancel();

		public PopupPanel getPopup();
	}

	private DispatchAsync dispatch;

	private Action<?> lastAttemp;

	protected Action<?> action;

	protected AsyncCallback callback;

	@Inject
	public LoginPanelPresenter(Display display, EventBus eventBus,
			final DispatchAsync dispatcher) {
		super(display, eventBus);
		this.dispatch = dispatcher;

		bind();
	}

	@Override
	public Place getPlace() {
		return PLACE;
	}

	@Override
	protected void onBind() {
		display.getSubmit().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent clickEvent) {

				if (StringUtils.isEmptyOrBlank(display.getLogin().getValue())
						|| StringUtils.isEmptyOrBlank(display.getPassword()
								.getValue())) {
					display.getLogMessage().setValue(
							"Login AND password must be provided");
					return;
				}
				dispatch.execute(new LoginAction(display.getLogin().getValue(),
						display.getPassword().getValue()),
						new DisplayCallback<LoginResult>(display) {

							protected void handleFailure(Throwable e) {
								display.getLogMessage().setValue("fail");

							}

							protected void handleSuccess(LoginResult value) {
								display.getLogMessage().setValue("ok");
								eventBus.fireEvent(new AutoritiesGrantedEvent(
										value.getAuthorities()));
								if (action != null) {
									Action toDo = action;
									AsyncCallback toCall = callback;
									action = null;
									callback = null;
									dispatch.execute(toDo, toCall);
								}
							}
						});
			}
		});

		display.getCancel().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				display.getPopup().hide();

			}

		});

		eventBus.addHandler(AuthorizationRequiredEvent.TYPE,
				new AuthorizationRequiredEventEventHandler() {

					public void onAuthorizationRequired(
							AuthorizationRequiredEvent accessDeniedEvent) {

						LoginPanelPresenter.this.action = accessDeniedEvent
								.getAction();
						LoginPanelPresenter.this.callback = accessDeniedEvent
								.getCallback();

						display.getLogMessage().setValue(
								accessDeniedEvent.getMessage());
						display.getPopup().center();
						display.getPopup().show();

					}

				});

		eventBus.addHandler(AccessDeniedEvent.TYPE,
				new AccessDeniedEventEventHandler() {

					public void onAccessDenied(
							AccessDeniedEvent accessDeniedEvent) {
						Window.alert("Denied: "
								+ accessDeniedEvent.getMessage());

					}
				});

		eventBus.addHandler(AutoritiesGrantedEvent.TYPE,
				new AutoritiesGrantedEventEventHandler() {

					public void onAuthoritiesGranted(
							AutoritiesGrantedEvent autoritiesGrantedEvent) {
						display.getPopup().hide();

					}
				});
	}

	@Override
	protected void onPlaceRequest(PlaceRequest request) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onUnbind() {
		// TODO Auto-generated method stub

	}

	public void refreshDisplay() {
		// TODO Auto-generated method stub

	}

	public void revealDisplay() {
		// TODO Auto-generated method stub

	}

}
