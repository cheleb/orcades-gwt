package ${package}.client;



import ${package}.client.details.DetailsActivity;
import ${package}.client.details.DetailsViewImpl;
import ${package}.client.home.HomePlace;
import ${package}.client.home.HomeActivity;
import ${package}.client.home.HomeViewImpl;

import net.orcades.gwt.ginmvp.client.GinMvpModule;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;

public class AppGinModule extends AbstractGinModule {

	@Override
	protected void configure() {

		install(new GinMvpModule(AppPlaceHistoryMapper.class, HomePlace.class));
		
		// ActivityMapper maps the place to a new activity instance.
		// you should have one activity mapper for each display area.
		bind(ActivityMapper.class).to(AppActivityMapper.class).in(Singleton.class);
		
		bind(HomeActivity.View.class).to(HomeViewImpl.class).in(Singleton.class);
		bind(DetailsActivity.View.class).to(DetailsViewImpl.class).in(Singleton.class);
		
	}

}
