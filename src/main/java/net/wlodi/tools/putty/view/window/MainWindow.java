package net.wlodi.tools.putty.view.window;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.wlodi.tools.putty.repository.conf.WindowConf;
import net.wlodi.tools.putty.view.utils.GUIUtils;


public class MainWindow extends JFrame {
    
    private static final long serialVersionUID = -1563624512998391201L;

    private static final Logger LOGGER = LoggerFactory.getLogger( MainWindow.class );
    
    private static MainWindow inst = null;

    public static MainWindow inst( ) {
        return inst;
    };

    public MainWindow( ) {
        super();
        inst = this;

        LOGGER.info( "Start GUI" );

        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        initGUI();
        setVisible( true );
    }

    private void initGUI( ) {
        try {
            GUIUtils.setFrameData( this, WindowConf.MAIN_WINDOW );

            setLayout( new BorderLayout() );

        }
        catch ( Exception e ) {
            LOGGER.error( e.getMessage(), e );
        }
    }
    
}
