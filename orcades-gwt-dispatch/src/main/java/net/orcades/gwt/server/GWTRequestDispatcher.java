package net.orcades.gwt.server;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.orcades.gwt.shared.ApplicationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.server.rpc.RPC;
import com.google.gwt.user.server.rpc.RPCRequest;
import com.google.gwt.user.server.rpc.RPCServletUtils;

public abstract class GWTRequestDispatcher {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(GWTRequestDispatcher.class);

	@Inject
	protected GWTPayloadHelper payloadHelper;
	
	
	
	
	
	protected void doHandleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			payloadHelper.begin(request, response);
			String payload = RPCServletUtils.readContentAsGwtRpc(request);
			RPCServletUtils.writeResponse(request.getSession().getServletContext(), response, processCall(payload), false);
		} catch (SerializationException e) {
			LOGGER.error(e.getLocalizedMessage(),e);
		} finally {
			payloadHelper.end();
		}
		
	}
	

	/**
	 * Processes the request:
	 * <ul>
	 * Decode the GWT payload
	 * </ul>
	 * <ul>
	 * Locate / Call implementation service</b>
	 * <ul>
	 * Encode the response
	 * </ul>
	 * 
	 * @param payload
	 *            The GWT payload.
	 */
	
	public String processCall(String payload) throws SerializationException {

		RPCRequest request = RPC.decodeRequest(payload, null, payloadHelper);

		Method method = request.getMethod();

		@SuppressWarnings("rawtypes")
		Class clazz = method.getDeclaringClass();

		Map<String, Object> map = getServices(clazz);

		int numberOfImplemention = map.size();

		Object targetInjectedInstance = null;

		switch (numberOfImplemention) {
		case 0:
			LOGGER.error("No implementation of [" + clazz.getName()
					+ "] found in context");
			logRPCCall(request, method);
			throw new SerializationException("No implementation of ["
					+ clazz.getName() + "] found in context");
		case 1:
			targetInjectedInstance = map.values().iterator().next();
			LOGGER.debug("Only one implementation found!");
			break;
		default:
			LOGGER.debug(numberOfImplemention + " implementations found!");
			targetInjectedInstance = determineTargetInstance(map);
			break;

		}

		try {

			if (LOGGER.isDebugEnabled()) {
				logRPCCall(request, method);
			}
			// The call.
			Object result = method.invoke(targetInjectedInstance,
					request.getParameters());

			if (LOGGER.isDebugEnabled()) {
				logResult(result);
			}

			return RPC.encodeResponseForSuccess(method, result,
					request.getSerializationPolicy());
		} catch (Throwable th) {

			if (th instanceof InvocationTargetException) {
				InvocationTargetException invocationTargetException = (InvocationTargetException) th;
				if (invocationTargetException.getTargetException() instanceof ApplicationException) {
					LOGGER.warn(invocationTargetException.getTargetException()
							.getMessage());
					return RPC.encodeResponseForFailure(method,
							invocationTargetException.getTargetException(),
							request.getSerializationPolicy());
				} else {
					LOGGER.error(clazz.getName() + "::" + method.getName()
							+ "() failed!",
							invocationTargetException.getTargetException());
					logRPCCall(request, method);
					LOGGER.error(clazz.getName() + "::" + method.getName()
							+ "() failed!", th);
					return RPC.encodeResponseForFailure(method,
							invocationTargetException.getTargetException(),
							request.getSerializationPolicy());
				}
			} else {

			}
			logRPCCall(request, method);
			LOGGER.error(clazz.getName() + "::" + method.getName()
					+ "() failed!", th);
			return RPC.encodeResponseForFailure(method, th,
					request.getSerializationPolicy());

		}

	}
	
	
	protected abstract Map<String, Object> getServices(Class clazz);


	/**
	 * Resolve the service instance delegate the job to an implementation of
	 * {@link IBeanNameResolver}.
	 * 
	 * @param map
	 *            the Spring map of Implementation of the service.
	 * @return
	 */
	private Object determineTargetInstance(Map<String, Object> map) {
		
		
		LOGGER.debug("Multiple implementation not implemented yet, returning first one");
		return map.values().iterator().next();
	}


	/**
	 * Log helper.
	 * 
	 * @param priority
	 *            Of log (level).
	 * @param request
	 *            The rpc request.
	 * @param method
	 *            The call method.
	 */

	private void logRPCCall(RPCRequest request, Method method) {
		@SuppressWarnings("rawtypes")
		Class parameterClasses[] = method.getParameterTypes();
		StringBuffer stringBuffer = new StringBuffer(method.getName());
		stringBuffer.append(" (");
		for (int i = 0; i < parameterClasses.length; i++) {
			@SuppressWarnings("rawtypes")
			Class paramClass = parameterClasses[i];
			stringBuffer.append(paramClass.getName());
			if (i < parameterClasses.length - 1) {
				stringBuffer.append(", ");
			}
		}
		stringBuffer.append(")");
		LOGGER.debug(stringBuffer.toString());
		Object parameter[] = request.getParameters();
		for (int i = 0; i < parameter.length; i++) {
			Object object = parameter[i];
			LOGGER.debug("params[" + i + "]: " + object);
		}
	}

	private void logResult(Object result) {
		if (result == null) {
			LOGGER.debug("Return null!");
		} else if (result instanceof List) {
			@SuppressWarnings("rawtypes")
			List list = (List) result;
			if (list.size() > 0) {
				LOGGER.debug(list.size() + " object in returned list");
			} else {
				LOGGER.debug("Return an empty list");
			}
			int c = 0;
			for (@SuppressWarnings("rawtypes")
			Iterator iterator = list.iterator(); iterator.hasNext(); c++) {
				LOGGER.debug(c + ": " + iterator.next());
			}
		} else {
			LOGGER.debug("Return: " + result);
		}

	}

}
