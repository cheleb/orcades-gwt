package ${package}.client.details;

import net.orcades.gwt.ginmvp.shared.AbstractActivity;

import ${package}.client.home.HomePlace;
import com.google.gwt.user.client.ui.IsWidget;

public class DetailsActivity extends AbstractActivity<DetailsActivity.View>
		implements DetailsView.Presenter {

	public interface View extends IsWidget {
		void setPresenter(DetailsView.Presenter presenter);
	}
	
	@Override
	public void onStart() {
		view.setPresenter(this);
	}

	public void goToHome() {
		goTo(new HomePlace());
	}

}