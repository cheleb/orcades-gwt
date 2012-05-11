package net.orcades.gwt.ginmvp.client;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;

/**
 * This module sets up the GWT MVP framework with Guice in a 
 * reusable, Type-Safe fashion.
 * 
 * To use, install this module in your app module, passing
 * the classes to use for the ActivityMapper, PlaceHistoryMapper,
 * and Place.
 * 
 * Example:
 * 
 * 		install(new GinVPModule(AppActivityMapper.class, AppPlaceHistoryMapper.class, HomePlace.class));
 * 
 * @author slynn1324
 *
 */
public class GinMvpModule extends AbstractGinModule {

	/**
	 * Default no-arg constructor to enable runtime usage by Gin.
	 */
	public GinMvpModule(){}
	
	/**
	 * Hold the defaultPlaceClass that should be used by the binder to set up
	 * the static injection of the defaultPlace
	 */
	private Class<? extends Place> defaultPlaceClass;
	private Class<? extends PlaceHistoryMapper> placeHistoryMapperClass;
	
	/**
	 * Constructor to be used when installing the MvpModule into another
	 * module.
	 * 
	 * Example: 
	 * 
	 *    install(new MvpModule(MyPlace.class));
	 *    
	 * @param defaultPlaceClass
	 */
	public GinMvpModule(
			Class<? extends PlaceHistoryMapper> placeHistoryMapperClass,
			Class<? extends Place> defaultPlaceClass
			){
		this.placeHistoryMapperClass = placeHistoryMapperClass;
		this.defaultPlaceClass = defaultPlaceClass;
	}
	
	
	@Override
	protected void configure() {
	
		if ( defaultPlaceClass == null ){
			throw new RuntimeException("No defaultPlaceClass was specified. Please use the constructor MvpModule(Class<? extends Place> defaultPlaceClass) when installing this gin module.");
		}
		
		// create a simple global event bus
		bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);
		
		// set up the binding for the default place above.
		bind(Place.class).annotatedWith(GinMvpDefault.class).to(defaultPlaceClass);
		
		// PlaceHistoryMapper instantiate new places based on the browser URL. You 
		// only need one of these for the entire app.
		bind(PlaceHistoryMapper.class).annotatedWith(GinMvpDefault.class).to(placeHistoryMapperClass).in(Singleton.class);
		
	}

	/**
	   * Creates a new PlaceHistoryHandler.  This object is responsible handling navigation based on the browser URL.
	   * You only need one of those for the entire app.
	   * 
	   * @param placeController the place controller.
	   * @param historyMapper This is used to map the URL to a Place object and vice versa.
	   * @param eventBus the event bus.
	   * @return
	   */
	  @Provides 
	  @Singleton
	  public PlaceHistoryHandler getHistoryHandler(PlaceController placeController,
	                                               @GinMvpDefault PlaceHistoryMapper historyMapper, 
	                                               EventBus eventBus,
	                                               @GinMvpDefault Place defaultPlace) {
	    PlaceHistoryHandler historyHandler =  new PlaceHistoryHandler(historyMapper);
	    historyHandler.register(placeController, eventBus, (Place)defaultPlace );
	    return historyHandler;
	  }

	  /**
	   * Creates a new PlaceController. The place controller is used by the PlaceHistoryHandler and activities
	   * to tell the app to navigate to a different place. You only need one for the
	   * entire app. However, it is essential for it to get instantiated in order
	   * for the application navigation to work.
	   * 
	   * @param eventBus the event bus.
	   * @return
	   */
	  @Provides
	  @Singleton
	  public PlaceController getPlaceController(EventBus eventBus) {
	    return new PlaceController(eventBus);
	  }
	
}
