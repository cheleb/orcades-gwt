package net.orcades.gwt.ginmvp.client;

import javax.inject.Inject;
import javax.inject.Provider;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

/**
 * Adapted from
 * http://ars-codia.raphaelbauer.com/2011/04/gwt-gin-and-simple-split
 * -points.html
 * 
 * @author slynn1324
 * 
 * @param <T>
 */
public class ActivityAsyncProxy<T extends Activity> implements Activity {

	private AsyncProvider<T> asyncProvider;

	private boolean canceled = false;
	private T impl;

	@Inject
	public ActivityAsyncProxy(AsyncProvider<T> activityProvider) {
		this.asyncProvider = activityProvider;
	}

	@Override
	public String mayStop() {
		return (impl != null ? impl.mayStop() : null);
	}

	@Override
	public void onCancel() {
		if (impl != null) {
			impl.onCancel();
		} else {
			canceled = true;
		}
	}

	@Override
	public void onStop() {
		if (impl != null) {
			impl.onStop();
		}
	}

	@Override
	public void start(final AcceptsOneWidget panel, final EventBus eventBus) {

		if (impl != null) {
			impl.start(panel, eventBus);
		} else {
			asyncProvider.get(new AsyncCallback<T>() {

				@Override
				public void onFailure(Throwable caught) {
					GWT.log("failure: " + caught.getMessage());
				}

				@Override
				public void onSuccess(T result) {
					GWT.log("async loaded:" + result.getClass());
					if (!canceled) {
						impl = (T) result;
						impl.start(panel, eventBus);
					}
				}

			});
		}
	}

}
