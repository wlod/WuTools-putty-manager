package net.wlodi.tools.putty.view.system;


import java.awt.AWTEvent;
import java.awt.EventQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class EventQueueProxy extends EventQueue {

    private static final Logger LOGGER = LoggerFactory.getLogger( EventQueueProxy.class );

    protected void dispatchEvent( AWTEvent newEvent ) {
        try {
            super.dispatchEvent( newEvent );
        }
        catch ( Throwable t ) {
            LOGGER.error( t.getMessage(), t );
        }
    }
}
