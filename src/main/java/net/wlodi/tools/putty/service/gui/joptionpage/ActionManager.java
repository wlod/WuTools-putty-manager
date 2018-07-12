package net.wlodi.tools.putty.service.gui.joptionpage;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ActionManager {
    
    private static final Logger LOGGER = LoggerFactory.getLogger( ActionManager.class );
    
    public enum ActionImplementation {
        CLOSE_MAIN_WINDOW;
    }

    private static final ActionManager instance = new ActionManager();

    private static final Map<ActionImplementation , Actions> implementedActions = new HashMap<>();

    public static ActionManager ints( ) {
        return instance;
    }

    private ActionManager() {

    }

    /**
     * 
     * @param scope
     *            - scope for actions
     * @param actions
     *            - implemented actions for provided scope
     * @return null or the previous actions associated with scope
     */
    public Actions put( ActionImplementation scope , Actions actions ) {
        return implementedActions.put( scope, actions );
    }

    public Actions get( ActionImplementation scope ) {
        return implementedActions.get( scope );
    }
    
    public void run( ActionImplementation scope, int jOptionPaneReturnValue ) {
        Actions actions = implementedActions.get( scope );
        if( actions == null ) {
            LOGGER.info( "Can not find actions for scope: {} and jOptionPaneReturnValue: {}.", scope, jOptionPaneReturnValue );
        }
        else {
            actions.runAction( jOptionPaneReturnValue );
        }
    }
}
