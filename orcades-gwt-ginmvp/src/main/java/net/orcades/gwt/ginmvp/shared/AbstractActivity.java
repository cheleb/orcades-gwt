package net.orcades.gwt.ginmvp.shared;

import javax.inject.Inject;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;

public abstract class AbstractActivity<V extends IsWidget> extends
		com.google.gwt.activity.shared.AbstractActivity {

	@Inject
	protected V view;

	@Inject
	private PlaceController placeController;

	@Override
	final public void start(AcceptsOneWidget panel, EventBus eventBus) {
		GWT.log("currentPlace = " + placeController.getWhere());

		panel.setWidget(view.asWidget());
		onStart();
	}

	protected void onStart() {

	}

	/**
	 * @return
	 * @see com.google.gwt.place.shared.PlaceController#getWhere()
	 */
	public Place getWhere() {
		return placeController.getWhere();
	}

	/**
	 * @param newPlace
	 * @see com.google.gwt.place.shared.PlaceController#goTo(com.google.gwt.place.shared.Place)
	 */
	public void goTo(Place newPlace) {
		placeController.goTo(newPlace);
	}

}
