package net.wlodi.tools.putty.view.utils;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.wlodi.tools.putty.repository.conf.AppLocale;
import net.wlodi.tools.putty.repository.conf.UIConf;
import net.wlodi.tools.putty.repository.conf.WindowConf;
import net.wlodi.tools.putty.service.gui.joptionpage.ActionManager;
import net.wlodi.tools.putty.service.gui.joptionpage.ActionManager.ActionImplementation;

public class GUIUtils {
    
    private static final Logger LOGGER = LoggerFactory.getLogger( GUIUtils.class );
    
    public static final Image LOGO_IMAGE = Toolkit.getDefaultToolkit().getImage( GUIUtils.class.getResource( UIConf.MAIN_WINDOW_PATH_TO_LOGO_PATH ) );
    public static final Icon LOGO_ICON = new ImageIcon( GUIUtils.class.getResource( UIConf.MAIN_WINDOW_PATH_TO_LOGO_PATH ) );
    public static final ImageIcon CHOOSE_FILE_ICON = new ImageIcon( GUIUtils.class.getResource( UIConf.CHOOSE_FILE_ICON_PATH ), AppLocale.CHOOSE_FILE_ICON_ALT );
    public static final ImageIcon APPEND_CONFIGURATION_ICON = new ImageIcon( GUIUtils.class.getResource( UIConf.APPEND_CONFIGURATION_ICON_PATH ),
            AppLocale.APPEND_CONFIGURATION_ICON_ALT );
    
    private static final Map<JTextField , DocumentListener> REQUIRED_FIELDS_CACHE = new HashMap<>();
    
    public static void exitConfirmDialog( Component componentToClose , ActionImplementation scope ) {

        int exit = JOptionPane.showConfirmDialog( componentToClose, AppLocale.DIALOG_WINDOW_IS_EXIT_QUESTION,
                AppLocale.DIALOG_WINDOW_IS_EXIT_TITLE, JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE, GUIUtils.LOGO_ICON );

        if (exit == JOptionPane.YES_OPTION) {

            ActionManager.ints().run( scope, JOptionPane.YES_OPTION );

            System.exit( 0 );
        }
    }
    
    public static JFrame getJFrameIcon( ) {
        JFrame fileChooserContainer = new JFrame();
        setIcon( fileChooserContainer );
        fileChooserContainer.setTitle( AppLocale.APP_TITLE + " - " + AppLocale.LABEL_WINDOW_OPEN_FILE );
        return fileChooserContainer;
    }
    
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
    
    public static JButton makeIconButton( ImageIcon imageIcon , String toolTipText ) {
        // Create and initialize the button.
        JButton button = new JButton();
        button.setToolTipText( toolTipText );
        button.setMargin( new Insets( 2, 2, 2, 2 ) );
        button.setBorder( BorderFactory.createEmptyBorder() );
        button.setBorderPainted( false );
        button.setContentAreaFilled( false );
        button.setFocusPainted( false );
        button.setOpaque( false );

        if (imageIcon != null) {
            button.setIcon( imageIcon );
        }
        else {
            button.setText( toolTipText );
        }

        return button;
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
    
    public static void removeRequiredField( JTextField field ) {
        DocumentListener dl = REQUIRED_FIELDS_CACHE.get( field );
        if (dl != null) {
            REQUIRED_FIELDS_CACHE.remove( field );
        }
    }

    public static void appendRequiredField( JTextField field ) {
        appendRequiredField( field, AppLocale.VAL_REQUIRED_FIELD );
    }

    public static void appendRequiredField( final JTextField field , String message ) {
        DocumentListener dl = REQUIRED_FIELDS_CACHE.get( field );
        if (dl != null) {
            throw new IllegalStateException( "The field currenlty contains a required field document listener." );
        }

        DocumentListener documentListener = new DocumentListener() {

            public void changedUpdate( DocumentEvent e ) {
                changed();
            }

            public void removeUpdate( DocumentEvent e ) {
                changed();
            }

            public void insertUpdate( DocumentEvent e ) {
                changed();
            }

            public void changed( ) {
                if (StringUtils.isBlank( field.getText() )) {
                    field.setBackground( UIConf.VAL_REQUIRED_FIELD_COLOR );
                }
                else {
                    field.setBackground( UIConf.DEFAULT_BG_COLOR );
                }

            }
        };
        field.getDocument().addDocumentListener( documentListener );
        REQUIRED_FIELDS_CACHE.put( field, documentListener );
    }
    
}
