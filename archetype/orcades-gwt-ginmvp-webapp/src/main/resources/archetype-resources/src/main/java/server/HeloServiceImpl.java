package ${package}.server;

import javax.inject.Named;

import ${package}.shared.HeloService;

@Named
public class HeloServiceImpl implements HeloService {

	@Override
	public String sayHelo(String to) {
		return "helo " + to;
	}

}
