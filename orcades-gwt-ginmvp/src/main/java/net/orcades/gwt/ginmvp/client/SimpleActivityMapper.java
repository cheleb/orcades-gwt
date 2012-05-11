package net.orcades.gwt.ginmvp.client;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Provider;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

/**
 * 
 * SimpleActivityMapper can be extended to create a simplified GinMvp
 * ActivityMapper for simple use cases where you have a 1-1 relationship between
 * Activities and Places.
 * 
 * Example:
 * 
 * <pre>
 * public class AppActivityMapper extends SimpleActivityMapper {
 * 
 * 	&#064;Inject
 * 	public AppActivityMapper(final Provider&lt;HomeActivity&gt; homeActivityProvider,
 * 			final Provider&lt;DetailsActivity&gt; detailsActivityProvider) {
 * 
 * 		addProvider(HomePlace.class, homeActivityProvider);
 * 		addProvider(DetailsPlace.class, detailsActivityProvider);
 * 	}
 * }
 * </pre>
 * 
 * @author slynn1324
 */
public class SimpleActivityMapper implements ActivityMapper {

	Map<Class<? extends Place>, Provider<? extends Activity>> providerMap = new HashMap<Class<? extends Place>, Provider<? extends Activity>>();

	public void addProvider(Class<? extends Place> placeClass,
			Provider<? extends Activity> provider) {
		providerMap.put(placeClass, provider);
	}

	public Provider<? extends Activity> getProvider(
			Class<? extends Place> placeClass) {
		return providerMap.get(placeClass);
	}

	@Override
	public Activity getActivity(Place place) {
		Provider<? extends Activity> provider = getProvider(place.getClass());
		if (provider != null) {
			return provider.get();
		}

		return null;
	}

}
