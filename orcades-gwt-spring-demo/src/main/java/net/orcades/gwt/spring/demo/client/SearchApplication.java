package net.orcades.gwt.spring.demo.client;



import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;

public class SearchApplication implements EntryPoint {

	public void onModuleLoad() {
		
		ELNInjector elnInjector = GWT.create(ELNInjector.class);
		
		elnInjector.getApplicationPresenter().start();
		
		elnInjector.getPlaceManager().fireCurrentPlace();
		
		
		
		
	}

}
