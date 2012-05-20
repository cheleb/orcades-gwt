package ${package}.server;

import javax.inject.Named;

import ${package}.shared.HeloAction;
import ${package}.shared.HeloResult;
import net.orcades.gwt.server.ActionHandler;

@Named
public class HeloHandler implements ActionHandler<HeloAction, HeloResult> {

	@Override
	public Class<HeloAction> getActionType() {
		
		return HeloAction.class;
	}

	@Override
	public HeloResult execute() {
		// TODO Auto-generated method stub
		return new HeloResult();
	}

}
