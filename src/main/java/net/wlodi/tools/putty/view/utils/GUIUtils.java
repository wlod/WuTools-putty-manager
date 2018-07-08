package net.wlodi.tools.putty.view.utils;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.wlodi.tools.putty.repository.conf.AppLocale;
import net.wlodi.tools.putty.repository.conf.UIConf;
import net.wlodi.tools.putty.repository.conf.WindowConf;

public class GUIUtils {
    
    private static final Logger LOGGER = LoggerFactory.getLogger( GUIUtils.class );
    
    public static final Image LOGO_IMAGE = Toolkit.getDefaultToolkit().getImage( GUIUtils.class.getResource( UIConf.MAIN_WINDOW_PATH_TO_LOGO ) );
    
    public static void setFrameData( JFrame frame , WindowConf conf ) {
        frame.setTitle( AppLocale.APP_TITLE + " - " + conf.title() );
        frame.setSize( conf.width(), conf.height() );
        frame.setResizable( conf.resizable() );
        frame.setLocationRelativeTo( null );
        if (conf.isSetMinimumSize()) {
            frame.setMinimumSize( new Dimension( conf.width(), conf.height() ) );
        }
        setIcon( frame );
        setSystemTemplate( frame );
    }
    
    public static void setIcon( JFrame window ) {
        window.setIconImage( LOGO_IMAGE );
    }
    
    public static void setSystemTemplate( Component component ) {
        try {
            UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
            SwingUtilities.updateComponentTreeUI( component );
        }
        catch ( Exception ex ) {
            LOGGER.error( "Can not update style for component: [{}].", component.getClass().getCanonicalName(), ex );
        }
    }
    
}
