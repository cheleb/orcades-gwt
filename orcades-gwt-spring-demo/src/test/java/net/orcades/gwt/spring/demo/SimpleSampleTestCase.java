package net.orcades.gwt.spring.demo;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;


public class SimpleSampleTestCase {
	
	/** 
* Log4j logger.
*/
private static final Logger LOGGER = Logger.getLogger(SimpleSampleTestCase.class);

	@Test
	public void testFirstMock() {
		LOGGER.debug("SimpleSampleTestCase.testFirstMock");
		final HasClickHandlers clickHandlers = createMock(HasClickHandlers.class);
		final HandlerRegistration handlerRegistration = createMock(HandlerRegistration.class);
		expect(clickHandlers.addClickHandler(isA(ClickHandler.class))).andReturn(handlerRegistration);
		
		replay(clickHandlers);

		HandlerRegistration handlerRegistration2 = clickHandlers.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				
			}
		});
		
		assertEquals(handlerRegistration, handlerRegistration2);
		
		verify(clickHandlers);
		
	}
	
}
