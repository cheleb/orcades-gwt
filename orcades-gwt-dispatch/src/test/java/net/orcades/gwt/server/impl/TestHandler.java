package net.orcades.gwt.server.impl;

import net.orcades.gwt.server.ActionHandler;

public class TestHandler implements ActionHandler<TestAction, TestResult> {

	@Override
	public Class<TestAction> getActionType() {
		return TestAction.class;
	}

	@Override
	public TestResult execute() {
		return new TestResult();
	}

}
