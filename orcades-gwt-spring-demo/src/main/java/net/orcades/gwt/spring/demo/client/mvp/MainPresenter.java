package net.orcades.gwt.spring.demo.client.mvp;

import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.presenter.client.DisplayCallback;
import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.place.Place;
import net.customware.gwt.presenter.client.place.PlaceRequest;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;
import net.customware.gwt.presenter.client.widget.WidgetPresenter;
import net.orcades.gwt.inject.spring.client.mvp.BindedWidgetPresenter;
import net.orcades.gwt.spring.demo.shared.event.ZozoEvent;
import net.orcades.gwt.spring.demo.shared.event.ZozoEventHandler;
import net.orcades.gwt.spring.demo.shared.rpc.ZozoAction;
import net.orcades.gwt.spring.demo.shared.rpc.ZozoResult;
import net.orcades.gwt.spring.dispatch.security.shared.event.AuthorizationRequiredEvent;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class MainPresenter extends BindedWidgetPresenter<MainPresenter.Display> {

	public static final Place PLACE = new Place("zozo");

	private final DispatchAsync dispatcher;

	@Inject
	private Provider<ListView> listViewProvider;
	
	
	@Inject
	public MainPresenter(Display display, EventBus eventBus,
			final DispatchAsync dispatcher) {
		super(display, eventBus);
		this.dispatcher = dispatcher;

		
	}

	public interface Display extends WidgetDisplay {
		public HasValue<String> messageBox();

		public HasClickHandlers getSend();
		
		public HasClickHandlers getShowList();
		
	}

	@Override
	public Place getPlace() {
		return PLACE;
	}

	@Override
	protected void onBind() {
		
		RootPanel.get("zozoCompositeContainer").add(display.asWidget());
		
		display.getSend().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				doSend();

			}
		});

		
		
		display.getShowList().addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
			
				ListView listView = listViewProvider.get();
				EventBus eventBus = listView.getEventBus();
				listView.init();
				RootPanel.get("zozoCompositeContainer").add(new Label("IIII"));
				RootPanel.get("zozoCompositeContainer").add(listView);
			}
		});

		eventBus.addHandler(ZozoEvent.TYPE, new ZozoEventHandler() {

			public void onZozoEvent(ZozoEvent event) {
				Window.alert(event.getMessage());
			}

		});

		eventBus.addHandler(ZozoEvent.TYPE, new ZozoEventHandler() {

			public void onZozoEvent(ZozoEvent event) {
				display.messageBox().setValue(event.getMessage());
			}

		});

	}

	protected void doSend() {
		dispatcher.execute(new ZozoAction(display.messageBox().getValue()),
				new DisplayCallback<ZozoResult>(display) {

					@Override
					protected void handleFailure(Throwable e) {

					}

					@Override
					protected void handleSuccess(ZozoResult value) {
						eventBus.fireEvent(new ZozoEvent(value.getMessage()));
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
