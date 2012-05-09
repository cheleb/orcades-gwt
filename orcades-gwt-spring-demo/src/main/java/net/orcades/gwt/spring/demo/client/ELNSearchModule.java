package net.orcades.gwt.spring.demo.client;

import net.customware.gwt.presenter.client.gin.AbstractPresenterModule;
import net.customware.gwt.presenter.client.place.PlaceManager;
import net.orcades.gwt.spring.demo.client.mvp.MainPresenter;
import net.orcades.gwt.spring.demo.client.mvp.MainView;

import com.google.inject.Singleton;

public class ELNSearchModule extends AbstractPresenterModule {

	@Override
	protected void configure() {
		bind(PlaceManager.class).in(Singleton.class);
		bind(MainPresenter.class).asEagerSingleton();
		bindDisplay(MainPresenter.Display.class, MainView.class);
	}

}