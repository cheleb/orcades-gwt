package net.orcades.gwt.spring.dispatch.security.client.gin;

import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.presenter.client.DefaultEventBus;
import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.gin.AbstractPresenterModule;
import net.orcades.gwt.spring.dispatch.security.client.DefaultSpringSecuredDispatchAsync;
import net.orcades.gwt.spring.dispatch.security.client.login.LoginPanelPresenter;
import net.orcades.gwt.spring.dispatch.security.client.login.LoginPanelView;
import net.orcades.gwt.spring.dispatch.security.client.status.SecurityStatusPresenter;
import net.orcades.gwt.spring.dispatch.security.client.status.SecurityStatusView;

import com.google.gwt.inject.client.Ginjector;
import com.google.inject.Singleton;

/**
 * This module binds the {@link DispatchAsync} to {@link DefaultSpringSecuredDispatchAsync}.
 * Add this as a @GinModule in your {@link Ginjector} instance.
 * 
 * @author Olivier NOUGUIER olivier.nouguier@gmail.com
 */
public class SpringSecuredClientDispatchModule extends AbstractPresenterModule {

	/**
	 * Binds:
	 * <ul>* {@link EventBus} to {@link DefaultEventBus}.</ul>
	 * <ul>* {@link DispatchAsync} to {@link DefaultSpringSecuredDispatchAsync}</ul>
	 * 
	 */
    @Override
    protected void configure() {
    	bind(EventBus.class).to(DefaultEventBus.class).in(Singleton.class);
        bind( DispatchAsync.class ).to( DefaultSpringSecuredDispatchAsync.class ).in( Singleton.class );
        
        bind(LoginPanelPresenter.class).asEagerSingleton();
        bindDisplay(LoginPanelPresenter.Display.class, LoginPanelView.class);
        
      
        
        bind(SecurityStatusPresenter.class).asEagerSingleton();
		bindDisplay(SecurityStatusPresenter.Display.class, SecurityStatusView.class);
        
    }

}
