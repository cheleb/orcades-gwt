package net.orcades.gwt.spring.dispatch.security.shared.event;

import com.google.gwt.event.shared.EventHandler;

public interface AccessDeniedEventEventHandler extends EventHandler {
  public void onAccessDenied(AccessDeniedEvent accessDeniedEvent);
}
