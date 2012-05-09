package net.orcades.gwt.spring.dispatch.security.client;

public class StringUtils {

	/**
	 * Determines if a string is empty (or null).
	 * @param text
	 * @return true is null or blank string.
	 */
	public static boolean isEmptyOrBlank(String text) {
		if(text==null) {
			return true;
		}
		return "".equals(text.trim());
	}
	
}

