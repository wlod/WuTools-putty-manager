package net.wlodi.tools.putty;


import java.awt.Toolkit;

import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.wlodi.tools.putty.view.common.BootGuiConfiguration;
import net.wlodi.tools.putty.view.system.EventQueueProxy;
import net.wlodi.tools.putty.view.window.MainWindow;


public class WuPuttySessionsManager {

    private static final Logger LOGGER = LoggerFactory.getLogger( WuPuttySessionsManager.class );

    public static void main( String[] args ) {

        LOGGER.info( "[WuPuttySessionsManager Started]" );

        LOGGER.info( "------------------------------------------" );
        
        initGUI();

    }

    private static void initGUI( ) {
        try {
            BootGuiConfiguration.boot();
            Toolkit.getDefaultToolkit().getSystemEventQueue().push( new EventQueueProxy() );
            SwingUtilities.invokeLater( new Runnable() {

                public void run( ) {
                    new MainWindow( );
                }
            } );
        }
        catch ( Exception e ) {
            LOGGER.error( "Problem with Java Swing.", e );
        }
    }

}
