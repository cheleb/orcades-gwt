package net.orcades.gwt.spring.component;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.google.gwt.user.server.rpc.RPC;
import com.google.gwt.user.server.rpc.RPCRequest;
import com.google.gwt.user.server.rpc.RPCServletUtils;
import com.google.gwt.user.server.rpc.SerializationPolicy;
import com.google.gwt.user.server.rpc.SerializationPolicyLoader;
import com.google.gwt.user.server.rpc.SerializationPolicyProvider;

/**
 * 
 * Helper to decode GWT Payload and provide {@link SerializationPolicy}. <br />
 * Cut && Paste from google GWT code.
 * 
 * @author olivier.nouguier@gmail.com
 * 
 */
@Component
public class GWTPayloadHelper implements SerializationPolicyProvider {

	/**
	 * {@link ThreadLocal} for {@link HttpServletRequest}.
	 */

	private final ThreadLocal<HttpServletRequest> perThreadRequest = new ThreadLocal<HttpServletRequest>();

	/**
	 * {@link ThreadLocal} form {@link HttpServletResponse}.
	 */
	private final ThreadLocal<HttpServletResponse> perThreadResponse = new ThreadLocal<HttpServletResponse>();

	/**
	 * A cache of moduleBaseURL and serialization policy strong name to
	 * {@link SerializationPolicy}.
	 */
	private final Map<String, SerializationPolicy> serializationPolicyCache = new HashMap<String, SerializationPolicy>();

	/**
	 * ThreadLocal cache in.
	 * 
	 * @param request
	 *            to put in cache.
	 * @param response
	 *            to put in cache.
	 */
	public void begin(HttpServletRequest request, HttpServletResponse response) {
		perThreadRequest.set(request);
		perThreadResponse.set(response);
	}

	/**
	 * {@link ThreadLocal} cache clean.
	 */
	public void end() {
		perThreadRequest.set(null);
		perThreadResponse.set(null);

	}

	/**
	 * Getter for {@link SerializationPolicy}.
	 * 
	 * @param moduleBaseURL
	 *            "search"
	 * @param strongName
	 *            "SKJSKJSJKSKJ18771878"
	 * @return the current {@link SerializationPolicy}
	 */
	public final SerializationPolicy getSerializationPolicy(
			String moduleBaseURL, String strongName) {

		SerializationPolicy serializationPolicy = getCachedSerializationPolicy(
				moduleBaseURL, strongName);
		if (serializationPolicy != null) {
			return serializationPolicy;
		}

		serializationPolicy = doGetSerializationPolicy(getThreadLocalRequest(),
				moduleBaseURL, strongName);

		if (serializationPolicy == null) {
			// Failed to get the requested serialization policy; use the default
			getServletContext()
					.log(
							"WARNING: Failed to get the SerializationPolicy '"
									+ strongName
									+ "' for module '"
									+ moduleBaseURL
									+ "'; a legacy, 1.3.3 compatible, serialization policy will be used."
									+ "  You may experience SerializationExceptions as a result.");
			serializationPolicy = RPC.getDefaultSerializationPolicy();
		}

		// This could cache null or an actual instance. Either way we will not
		// attempt to lookup the policy again.
		putCachedSerializationPolicy(moduleBaseURL, strongName,
				serializationPolicy);

		return serializationPolicy;
	}

	/**
	 * Convenience method to retrieve the {@link ServletContext} from the
	 * {@link ThreadLocal}.
	 * 
	 * @return the {@link ServletContext}
	 */
	private ServletContext getServletContext() {

		return getThreadLocalRequest().getSession().getServletContext();
	}

	/**
	 * Gets the {@link SerializationPolicy} for given module base URL and strong
	 * name if there is one.
	 * 
	 * Override this method to provide a {@link SerializationPolicy} using an
	 * alternative approach.
	 * 
	 * @param request
	 *            the HTTP request being serviced
	 * @param moduleBaseURL
	 *            as specified in the incoming payload
	 * @param strongName
	 *            a strong name that uniquely identifies a serialization policy
	 *            file
	 * @return a {@link SerializationPolicy} for the given module base URL and
	 *         strong name, or <code>null</code> if there is none
	 */
	protected SerializationPolicy doGetSerializationPolicy(
			HttpServletRequest request, String moduleBaseURL, String strongName) {
		// The request can tell you the path of the web app relative to the
		// container root.
		String contextPath = request.getContextPath();

		String modulePath = null;
		if (moduleBaseURL != null) {
			try {
				modulePath = new URL(moduleBaseURL).getPath();
			} catch (MalformedURLException ex) {
				// log the information, we will default
				getServletContext().log(
						"Malformed moduleBaseURL: " + moduleBaseURL, ex);
			}
		}

		SerializationPolicy serializationPolicy = null;

		/*
		 * Check that the module path must be in the same web app as the servlet
		 * itself. If you need to implement a scheme different than this,
		 * override this method.
		 */
		if (modulePath == null || !modulePath.startsWith(contextPath)) {
			String message = "ERROR: The module path requested, "
					+ modulePath
					+ ", is not in the same web application as this servlet, "
					+ contextPath
					+ ".  Your module may not be properly configured or your client and server code maybe out of date.";
			getServletContext().log(message);
		} else {
			// Strip off the context path from the module base URL. It should be
			// a
			// strict prefix.
			String contextRelativePath = modulePath.substring(contextPath
					.length());

			String serializationPolicyFilePath = SerializationPolicyLoader
					.getSerializationPolicyFileName(contextRelativePath
							+ strongName);

			// Open the RPC resource file read its contents.
			InputStream is = getServletContext().getResourceAsStream(
					serializationPolicyFilePath);
			

			try {
				if (is != null) {
					try {
						serializationPolicy = SerializationPolicyLoader
								.loadFromStream(is, null);
					} catch (ParseException e) {
						getServletContext().log(
								"ERROR: Failed to parse the policy file '"
										+ serializationPolicyFilePath + "'", e);
					} catch (IOException e) {
						getServletContext().log(
								"ERROR: Could not read the policy file '"
										+ serializationPolicyFilePath + "'", e);
					}
				} else {
					String message = "ERROR: The serialization policy file '"
							+ serializationPolicyFilePath
							+ "' was not found; did you forget to include it in this deployment?";
					getServletContext().log(message);
				}
			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (IOException e) {
						// Ignore this error
					}
				}
			}
		}

		return serializationPolicy;
	}

	/**
	 * Override this method to control what should happen when an exception
	 * escapes the {@link #processCall(String)} method. The default
	 * implementation will log the failure and send a generic failure response
	 * to the client.
	 * <p/>
	 * 
	 * An "expected failure" is an exception thrown by a service method that is
	 * declared in the signature of the service method. These exceptions are
	 * serialized back to the client, and are not passed to this method. This
	 * method is called only for exceptions or errors that are not part of the
	 * service method's signature, or that result from SecurityExceptions,
	 * SerializationExceptions, or other failures within the RPC framework.
	 * <p/>
	 * 
	 * Note that if the desired behavior is to both send the GENERIC_FAILURE_MSG
	 * response AND to rethrow the exception, then this method should first send
	 * the GENERIC_FAILURE_MSG response itself (using getThreadLocalResponse),
	 * and then rethrow the exception. Rethrowing the exception will cause it to
	 * escape into the servlet container.
	 * 
	 * @param e
	 *            the exception which was thrown
	 */
	public void doUnexpectedFailure(Throwable e) {
		ServletContext servletContext = getServletContext();
		RPCServletUtils.writeResponseForUnexpectedFailure(servletContext,
				getThreadLocalResponse(), e);
	}

	/**
	 * Gets the <code>HttpServletRequest</code> object for the current call. It
	 * is stored thread-locally so that simultaneous invocations can have
	 * different request objects.
	 */
	protected final HttpServletRequest getThreadLocalRequest() {
		return perThreadRequest.get();
	}

	/**
	 * Gets the <code>HttpServletResponse</code> object for the current call. It
	 * is stored thread-locally so that simultaneous invocations can have
	 * different response objects.
	 */
	protected final HttpServletResponse getThreadLocalResponse() {
		return perThreadResponse.get();
	}

	/**
	 * Override this method to examine the serialized response that will be
	 * returned to the client. The default implementation does nothing and need
	 * not be called by subclasses.
	 */
	protected void onAfterResponseSerialized(String serializedResponse) {
	}

	/**
	 * Override this method to examine the serialized version of the request
	 * payload before it is deserialized into objects. The default
	 * implementation does nothing and need not be called by subclasses.
	 */
	public void onBeforeRequestDeserialized(String serializedRequest) {
	}

	/**
	 * Determines whether the response to a given servlet request should or
	 * should not be GZIP compressed. This method is only called in cases where
	 * the requestor accepts GZIP encoding.
	 * <p>
	 * This implementation currently returns <code>true</code> if the response
	 * string's estimated byte length is longer than 256 bytes. Subclasses can
	 * override this logic.
	 * </p>
	 * 
	 * @param request
	 *            the request being served
	 * @param response
	 *            the response that will be written into
	 * @param responsePayload
	 *            the payload that is about to be sent to the client
	 * @return <code>true</code> if responsePayload should be GZIP compressed,
	 *         otherwise <code>false</code>.
	 */
	protected boolean shouldCompressResponse(HttpServletRequest request,
			HttpServletResponse response, String responsePayload) {
		return RPCServletUtils
				.exceedsUncompressedContentLengthLimit(responsePayload);
	}

	private SerializationPolicy getCachedSerializationPolicy(
			String moduleBaseURL, String strongName) {
		synchronized (serializationPolicyCache) {
			return serializationPolicyCache.get(moduleBaseURL + strongName);
		}
	}

	private void putCachedSerializationPolicy(String moduleBaseURL,
			String strongName, SerializationPolicy serializationPolicy) {
		synchronized (serializationPolicyCache) {
			serializationPolicyCache.put(moduleBaseURL + strongName,
					serializationPolicy);
		}
	}

	public RPCRequest decodeRPCRequest() throws IOException, ServletException {
		//
		// Read the request fully.
		//
		String requestPayload = RPCServletUtils
				.readContentAsUtf8(getThreadLocalRequest());
		//
		// Let subclasses see the serialized request.
		//
		onBeforeRequestDeserialized(requestPayload);

		RPCRequest rpcRequest = RPC.decodeRequest(requestPayload, null, this);
		return rpcRequest;

	}
}
