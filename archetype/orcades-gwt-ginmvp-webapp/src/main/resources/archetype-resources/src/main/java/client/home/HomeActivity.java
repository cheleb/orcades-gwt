package ${package}.client.home;

import net.orcades.gwt.ginmvp.shared.AbstractActivity;

import ${package}.client.details.DetailsPlace;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.IsWidget;

public class HomeActivity extends AbstractActivity<HomeActivity.View> implements
		HomeView.Presenter {

	public interface View extends IsWidget {
		void setPresenter(HomeView.Presenter presenter);
	}

	@Override
	public void onStart() {
		view.setPresenter(this);
	}

	public void goToDetails() {
		goTo(new DetailsPlace(new Long(Random.nextInt(100))));
	}

}
