package net.orcades.gwt.spring.dispatch.security.shared.event;

import com.google.gwt.event.shared.EventHandler;

public interface AutoritiesGrantedEventEventHandler extends EventHandler {
  public void onAuthoritiesGranted(AutoritiesGrantedEvent autoritiesGrantedEvent);
}
