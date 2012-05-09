package net.orcades.gwt.spring.component;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import net.orcades.gwt.spring.shared.ApplicationException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestHandler;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.server.rpc.RPC;
import com.google.gwt.user.server.rpc.RPCRequest;
import com.google.gwt.user.server.rpc.RPCServletUtils;

/**
 * The {@link HttpRequestHandler} to handle GWT RPC request.
 * @author olivier.nouguier@gmail.com
 *
 */
@Component("gwt-handler")
public class GWTHttpRequestHandler implements HttpRequestHandler,
		ApplicationContextAware {


	/**
	 * Logger.
	 */
	public static final Logger LOGGER = Logger
			.getLogger(GWTHttpRequestHandler.class);

	@Autowired
	private GWTPayloadHelper payloadHelper;

	public void handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {
			payloadHelper.begin(request, response);
			String payload = RPCServletUtils.readContentAsUtf8(request);
			RPCServletUtils.writeResponse(request.getSession().getServletContext(), response, processCall(payload), false);
		} catch (SerializationException e) {
			LOGGER.error(e);
		} finally {
			payloadHelper.end();
		}

	}

	/**
	 * Spring application context.
	 */
	private ApplicationContext applicationContext;

	
	
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
	@SuppressWarnings("unchecked")
	public String processCall(String payload) throws SerializationException {

		RPCRequest request = RPC.decodeRequest(payload, null, payloadHelper);

		Method method = request.getMethod();

		Class clazz = method.getDeclaringClass();

		Map<String, Object> map = applicationContext.getBeansOfType(clazz);

		int numberOfImplemention = map.size();

		Object targetInjectedInstance = null;

		switch (numberOfImplemention) {
		case 0:
			LOGGER.error("No implementation of [" + clazz.getName()
					+ "] found in context");
			logRPCCall(Level.ERROR, request, method);
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
				logRPCCall(Level.DEBUG, request, method);
			}
			// The call.
			Object result = method.invoke(targetInjectedInstance, request
					.getParameters());

			if (LOGGER.isDebugEnabled()) {
				logResult(result);
			}

			return RPC.encodeResponseForSuccess(method, result, request
					.getSerializationPolicy());
		} catch (Throwable th) {
			
			if (th instanceof InvocationTargetException) {
				InvocationTargetException invocationTargetException = (InvocationTargetException) th;
				if(invocationTargetException.getTargetException() instanceof ApplicationException){
				  LOGGER.warn(invocationTargetException.getTargetException().getMessage());	
				  return RPC.encodeResponseForFailure(method,
							invocationTargetException.getTargetException(), request
									.getSerializationPolicy());
				}else{
				LOGGER.error(clazz.getName() + "::" + method.getName()
						+ "() failed!", invocationTargetException
						.getTargetException());
				logRPCCall(Level.ERROR, request, method);
				LOGGER.error(th);
				return RPC.encodeResponseForFailure(method,
						invocationTargetException.getTargetException(), request
								.getSerializationPolicy());
				}
			}else{
				
			}
			logRPCCall(Level.ERROR, request, method);
			LOGGER.error(th);
			return RPC.encodeResponseForFailure(method, th, request
					.getSerializationPolicy());

		}

	}

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
	@SuppressWarnings("unchecked")
	private void logRPCCall(org.apache.log4j.Level priority,
			RPCRequest request, Method method) {
		Class parameterClasses[] = method.getParameterTypes();
		StringBuffer stringBuffer = new StringBuffer(method.getName());
		stringBuffer.append(" (");
		for (int i = 0; i < parameterClasses.length; i++) {
			Class paramClass = parameterClasses[i];
			stringBuffer.append(paramClass.getName());
			if (i < parameterClasses.length - 1) {
				stringBuffer.append(", ");
			}
		}
		stringBuffer.append(")");
		LOGGER.log(priority, stringBuffer.toString());
		Object parameter[] = request.getParameters();
		for (int i = 0; i < parameter.length; i++) {
			Object object = parameter[i];
			LOGGER.log(priority, "params[" + i + "]: " + object);
		}
	}

	@SuppressWarnings("unchecked")
	private void logResult(Object result) {
		if (result == null) {
			LOGGER.debug("Return null!");
		} else if (result instanceof List) {
			List list = (List) result;
			if (list.size() > 0) {
				LOGGER.debug(list.size() + " object in returned list");
			} else {
				LOGGER.debug("Return an empty list");
			}
			int c = 0;
			for (Iterator iterator = list.iterator(); iterator.hasNext(); c++) {
				LOGGER.debug(c + ": " + iterator.next());
			}
		} else {
			LOGGER.debug("Return: " + result);
		}

	}

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;

	}

}
