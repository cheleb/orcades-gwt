package net.orcades.gwt.spring.dispatch.security.shared.event;

import com.google.gwt.event.shared.EventHandler;

public interface AuthorizationRequiredEventEventHandler extends EventHandler {
  public void onAuthorizationRequired(AuthorizationRequiredEvent accessDeniedEvent);
}
