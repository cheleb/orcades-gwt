package ${package}.client;

import javax.inject.Inject;
import javax.inject.Provider;

import net.orcades.gwt.ginmvp.client.ActivityAsyncProvider;
import net.orcades.gwt.ginmvp.client.SimpleActivityMapper;
import ${package}.client.details.DetailsActivity;
import ${package}.client.details.DetailsPlace;
import ${package}.client.home.HomeActivity;
import ${package}.client.home.HomePlace;

/**
 * AppActivityMapper implementation that is Gin friendly.  
 * 
 * We use Gin injection to get an instance of the activity providers
 * that we need for our application, and then pass each to the underlying
 * 'addProvider' method creating a relationship between Places and Activity providers.
 * 
 * To have an activity code-split, inject a ActivityAsyncProvider<Activity>, 
 * rather than a Provider<Activity>.  
 * 
 * @author slynn1324
 */
public class AppActivityMapper extends SimpleActivityMapper {

	@Inject
	public AppActivityMapper(
			final Provider<HomeActivity> homeActivityProvider,
			final ActivityAsyncProvider<DetailsActivity> detailsActivityProvider) {
		
		addProvider(HomePlace.class, homeActivityProvider);
		addProvider(DetailsPlace.class, detailsActivityProvider);
	}

}
