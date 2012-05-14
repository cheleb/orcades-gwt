package ${package}.client.home;

import com.google.gwt.user.client.ui.IsWidget;

public interface HomeView extends IsWidget {

	public interface Presenter {
		public void goToDetails();
	}
	
}
