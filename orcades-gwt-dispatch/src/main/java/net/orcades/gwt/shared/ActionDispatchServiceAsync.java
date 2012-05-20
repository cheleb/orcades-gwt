package net.orcades.gwt.shared;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public interface ActionDispatchServiceAsync
{

    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see test.myginmvp.shared.HeloService
     */
    void execute( Action<?> to, AsyncCallback<?> callback );


    /**
     * Utility class to get the RPC Async interface from client-side code
     */
    public static final class Util 
    { 
        private static ActionDispatchServiceAsync instance;

        public static final ActionDispatchServiceAsync getInstance()
        {
            if ( instance == null )
            {
                instance = GWT.create( ActionDispatchService.class );
                ServiceDefTarget target = (ServiceDefTarget) instance;
                target.setServiceEntryPoint( GWT.getModuleBaseURL() + "/helo.gwtrpc" );
            }
            return instance;
        }

        private Util()
        {
            // Utility class should not be instanciated
        }
    }
}