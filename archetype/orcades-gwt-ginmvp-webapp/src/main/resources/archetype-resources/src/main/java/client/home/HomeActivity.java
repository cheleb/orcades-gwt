package ${package}.client.home;

import net.orcades.gwt.ginmvp.shared.AbstractActivity;
import net.orcades.gwt.shared.ActionDispatchServiceAsync;
import ${package}.client.details.DetailsPlace;
import ${package}.shared.HeloAction;
import ${package}.shared.HeloResult;
import ${package}.shared.HeloServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;

public class HomeActivity extends AbstractActivity<HomeActivity.View> implements
		HomeView.Presenter {

	public interface View extends IsWidget {
		void setPresenter(HomeView.Presenter presenter);
	}

	
	private HeloServiceAsync heloServiceAsync = HeloServiceAsync.Util.getInstance();
	
	private ActionDispatchServiceAsync actionDispatchServiceAsync = ActionDispatchServiceAsync.Util.getInstance();
	
	public HomeActivity() {
		GWT.log("Home new");
	}
	
	@Override
	public void onStart() {
		GWT.log("Home start");
		view.setPresenter(this);
	}

	public void goToDetails() {
		goTo(new DetailsPlace(new Long(Random.nextInt(100))));
	}

	@Override
	public void sayHelo(String string) {

		heloServiceAsync.sayHelo(string, new AsyncCallback<String>() {
			
			@Override
			public void onSuccess(String result) {
			   GWT.log(result);	
			}
			
			@Override
			public void onFailure(Throwable caught) {
				GWT.log(caught.getLocalizedMessage());
			}
		});
		
	}

	@Override
	public void sendHeloAction(String string) {
		
		HeloAction heloAction = new HeloAction();

		actionDispatchServiceAsync.execute(heloAction, new AsyncCallback<HeloResult>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(HeloResult result) {
				GWT.log("Yeppppa");
				
			}
		});
		
	}

}
