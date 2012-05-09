package net.orcades.gwt.spring.dispatch.security.shared.event;

import com.google.gwt.event.shared.EventHandler;

public interface LogoutEventHandler extends EventHandler {
  public void onLogout(LogoutEvent logoutEvent);
}
