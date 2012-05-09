package net.orcades.gwt.spring.demo.client;

import net.customware.gwt.presenter.client.place.PlaceManager;
import net.orcades.gwt.spring.demo.client.mvp.ApplicationPresenter;
import net.orcades.gwt.spring.dispatch.security.client.gin.SpringSecuredClientDispatchModule;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;

//@GinModules({ClientDispatchModule.class, ELNSearchModule.class})
@GinModules({SpringSecuredClientDispatchModule.class, ELNSearchModule.class})
public interface ELNInjector extends Ginjector {

	PlaceManager getPlaceManager();

	ApplicationPresenter getApplicationPresenter();
	
}
