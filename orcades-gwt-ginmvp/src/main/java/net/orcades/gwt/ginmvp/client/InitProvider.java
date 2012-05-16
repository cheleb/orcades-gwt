package net.orcades.gwt.ginmvp.client;

import javax.inject.Inject;
import javax.inject.Provider;

public class InitProvider<T extends Initable> implements Provider<T> {

	@Inject
	private Provider<T> provider;

	@Override
	public T get() {
		T t = provider.get();
		t.init();
		return t;
	}

}
