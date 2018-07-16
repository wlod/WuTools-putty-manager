package net.wlodi.tools.putty.view.window;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.event.DocumentEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.miginfocom.swing.MigLayout;
import net.wlodi.tools.putty.repository.conf.WindowConf;
import net.wlodi.tools.putty.service.PuttySessionService;
import net.wlodi.tools.putty.service.gui.joptionpage.ActionManager.ActionImplementation;
import net.wlodi.tools.putty.view.common.SimpleDocumentListener;
import net.wlodi.tools.putty.view.common.WhiteJPanel;
import net.wlodi.tools.putty.view.panel.BottomPanel;
import net.wlodi.tools.putty.view.panel.FilePanel;
import net.wlodi.tools.putty.view.panel.HeaderPanel;
import net.wlodi.tools.putty.view.panel.PuttySessionPanel;
import net.wlodi.tools.putty.view.panel.PuttySessionsTreePanel;
import net.wlodi.tools.putty.view.utils.GUIUtils;


public class MainWindow extends JFrame {

    private static final long serialVersionUID = -1563624512998391201L;

    private static final Logger LOGGER = LoggerFactory.getLogger( MainWindow.class );

    private PuttySessionService puttySessionService = PuttySessionService.inst();

    private PuttySessionsTreePanel puttySessionsTreePanel;
    private PuttySessionPanel puttySessionPanel;
    private FilePanel filePanel;

    private static MainWindow inst = null;

    public static MainWindow inst( ) {
        if (inst == null) {
            throw new RuntimeException( "MainWindow is null. The constructor was never be invoke." );
        }
        return inst;
    };

    public MainWindow() {
        super();
        inst = this;
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        initGUI();
        initListeners();

        setVisible( true );
    }

    private void initGUI( ) {
        try {
            GUIUtils.setFrameData( this, WindowConf.MAIN_WINDOW );
            setLayout( new BorderLayout() );
            setBackground( Color.WHITE );

            WhiteJPanel headerPanel = new WhiteJPanel( new GridLayout( 2, 1 ) );
            headerPanel.add( new HeaderPanel( new BorderLayout() ) );
            headerPanel.add( filePanel = new FilePanel( new MigLayout( "insets 10 10 10 10" ) ) );

            WhiteJPanel centerContentPanel = new WhiteJPanel( new BorderLayout() );
            centerContentPanel.add( puttySessionsTreePanel = new PuttySessionsTreePanel(), BorderLayout.WEST );
            centerContentPanel.add( puttySessionPanel = new PuttySessionPanel(), BorderLayout.CENTER );

            BottomPanel bottomPanel = new BottomPanel( new BorderLayout(), BorderFactory.createEmptyBorder( 5, 5, 5, 5 ) );

            add( headerPanel, BorderLayout.NORTH );
            add( centerContentPanel, BorderLayout.CENTER );
            add( bottomPanel, BorderLayout.SOUTH );

        }
        catch ( Exception e ) {
            LOGGER.error( e.getMessage(), e );
        }
    }

    private void initListeners( ) {

        filePanel.appendListener( new SimpleDocumentListener() {

            @Override
            public void update( DocumentEvent event ) {
                File selectedFile = filePanel.getSelectedFile();
                if (selectedFile == null) {
                    LOGGER.info( "Registry file is empty." );
                    return;
                }
                try {
                    puttySessionService.loadWindowsExportedRegistryFile( selectedFile );
                    puttySessionPanel.updateCurrentPuttySession();
                    LOGGER.info( "Loaded new windows registry file: {}.", selectedFile.getPath() );
                }
                catch ( IOException e ) {
                    LOGGER.info( "Can not loaded new windows registry file: {}.", selectedFile.getPath(), e );
                }
                catch ( InterruptedException e ) {
                    LOGGER.error( e.getMessage(), e );
                }
            }
        } );

        puttySessionsTreePanel.appendListener( new TreeSelectionListener() {

            public void valueChanged( TreeSelectionEvent event ) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) puttySessionsTreePanel.getjTree().getLastSelectedPathComponent();

                if (node == null || (node.getUserObject() instanceof String) == false) {
                    return;
                }
                try {
                    String sessionName = (String) node.getUserObject();
                    LOGGER.debug( "Try show values from putty session {}.", sessionName );
                    puttySessionPanel.updatePuttySession( sessionName );
                }
                catch ( IOException | InterruptedException e ) {
                    LOGGER.error( e.getMessage(), e );
                }

            }
        } );

    }

    @Override
    protected void processWindowEvent( WindowEvent e ) {
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            GUIUtils.exitConfirmDialog( MainWindow.this, ActionImplementation.CLOSE_MAIN_WINDOW );
        }
        else {
            super.processWindowEvent( e );
        }
    }

}
