package net.orcades.gwt.spring.dispatch.security.client;

import com.google.inject.Singleton;

@Singleton
public class SpringSecurityConfiguration {
	private String id = "right";

	private boolean showRoles = true;
	
	public SpringSecurityConfiguration() {
	}
	
	public SpringSecurityConfiguration(String string) {
		id = string;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setShowRoles(boolean showRoles) {
		this.showRoles = showRoles;
	}

	public boolean isShowRoles() {
		return showRoles;
	}
	
}
