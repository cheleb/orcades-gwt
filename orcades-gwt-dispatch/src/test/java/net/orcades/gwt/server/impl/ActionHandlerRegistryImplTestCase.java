package net.orcades.gwt.server.impl;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import net.orcades.gwt.server.ActionHandler;
import net.orcades.gwt.server.ActionHandlerRegistry;

import org.junit.Before;
import org.junit.Test;

public class ActionHandlerRegistryImplTestCase {

	
	public ActionHandlerRegistry actionHandlerRegistry;
	
	@Before
	public void before() {
		
		actionHandlerRegistry = new ActionHandlerRegistryImpl();
	}
	
	@Test
	public void testGetActionHandler() {
		actionHandlerRegistry.registerHandler(new TestHandler());
		ActionHandler<TestAction, TestResult> actionHandler = actionHandlerRegistry.getActionHandler(new TestAction());
		assertEquals(actionHandler.getActionType(), TestAction.class);
	}

	@Test
	public void testRegisterHandler() {
		actionHandlerRegistry.registerHandler(new TestHandler());
	}

	@Test
	public void testUnregisterHandler() {
		actionHandlerRegistry.registerHandler(new TestHandler());
		
		assertTrue(actionHandlerRegistry.unregisterHandler(new TestHandler()));
		assertFalse(actionHandlerRegistry.unregisterHandler(new TestHandler()));
		
	}

}
