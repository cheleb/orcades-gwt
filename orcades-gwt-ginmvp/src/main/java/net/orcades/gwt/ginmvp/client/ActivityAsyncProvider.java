package net.orcades.gwt.ginmvp.client;

import javax.inject.Inject;
import javax.inject.Provider;

import com.google.gwt.activity.shared.Activity;

public class ActivityAsyncProvider<T extends Activity> implements
		Provider<ActivityAsyncProxy<T>> {

	@Inject
	private Provider<ActivityAsyncProxy<T>> p;

	private ActivityAsyncProxy<T> cached;

	@Override
	public ActivityAsyncProxy<T> get() {
		if (cached == null) {
			cached = p.get();
		}
		return cached;
	}

}
