package net.wlodi.tools.putty.view.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.miginfocom.swing.MigLayout;
import net.wlodi.tools.putty.repository.conf.AppLocale;
import net.wlodi.tools.putty.repository.conf.WindowConf;
import net.wlodi.tools.putty.service.gui.joptionpage.ActionManager.ActionImplementation;
import net.wlodi.tools.putty.view.common.ChooseFileJButton;
import net.wlodi.tools.putty.view.common.WhiteJPanel;
import net.wlodi.tools.putty.view.panel.PuttySessionPanel;
import net.wlodi.tools.putty.view.panel.PuttySessionsTreePanel;
import net.wlodi.tools.putty.view.utils.GUIUtils;


public class MainWindow extends JFrame {
    
    private static final long serialVersionUID = -1563624512998391201L;

    private static final Logger LOGGER = LoggerFactory.getLogger( MainWindow.class );
    
    private PuttySessionsTreePanel puttySessionsTreePanel;
    private PuttySessionPanel puttySessionPanel;
    
    private ChooseFileJButton chooseFileJButton;
    
    private JTextField filePathField;
    
    private JButton appendAndSaveButton;
    
    protected File selectedFile;
    
    private static MainWindow inst = null;

    public static MainWindow inst( ) {
        if(inst == null) {
            throw new RuntimeException( "MainWindow is null. The constructor was never be invoke." );
        }
        return inst;
    };

    public MainWindow( ) {
        super();
        inst = this;

        LOGGER.info( "Start GUI" );

        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        initGUI();
        initListeners();
        setVisible( true );
    }

    private void initGUI( ) {
        try {
            GUIUtils.setFrameData( this, WindowConf.MAIN_WINDOW );
            setLayout( new BorderLayout() );
            
            WhiteJPanel headerPanel = new WhiteJPanel( new GridLayout( 2, 1 ) );
            
            WhiteJPanel headerRow1Panel = new WhiteJPanel( new BorderLayout() );
            headerRow1Panel.setBorder( BorderFactory.createEmptyBorder( 10, 10, 10, 10 ) );
            headerRow1Panel.add(
                    new JLabel( AppLocale.HELP_DESCRIPTION ),
                    BorderLayout.CENTER );
            JButton headerIcon = GUIUtils.makeIconButton( GUIUtils.APPEND_CONFIGURATION_ICON, AppLocale.APPEND_CONFIGURATION_ICON_ALT, AppLocale.APPEND_CONFIGURATION_ICON_ALT );
            headerIcon.setHorizontalAlignment( JLabel.RIGHT );
            headerRow1Panel.add( headerIcon, BorderLayout.EAST );

            WhiteJPanel fileConfRow2Panel = new WhiteJPanel( new MigLayout( "insets 10 10 10 10" ) );
            fileConfRow2Panel.add( new JLabel( AppLocale.LABEL_NAME_FILE_PATH ), "align left, cell 0 0 12 1, height pref+4px" ); // cell column row width height
            fileConfRow2Panel.add( filePathField = new JTextField( 200 ), "cell 0 1 10 1, height pref+4px" );
            fileConfRow2Panel.add( chooseFileJButton = new ChooseFileJButton(), "align right, cell 10 1 2 1, height pref+4px" ); // cell column row width height
            
            GUIUtils.appendRequiredField( filePathField );

            WhiteJPanel centerPanel = new WhiteJPanel( new BorderLayout() );
            
            WhiteJPanel centerHeadertPanel = new WhiteJPanel( new MigLayout( "insets 10 10 0 10" ) );
            centerHeadertPanel.add( new JLabel( AppLocale.LABEL_PUTTY_SESSIONS ), "align left, cell 0 0 12 1, height pref+4px");
            
            JPanel centerContentPanel = new WhiteJPanel( new BorderLayout() );

            centerContentPanel.add( puttySessionsTreePanel = new PuttySessionsTreePanel(), BorderLayout.WEST );
            centerContentPanel.add( puttySessionPanel = new PuttySessionPanel(), BorderLayout.CENTER );

            JPanel appendAndSavePanel = new WhiteJPanel( new BorderLayout() );
            appendAndSavePanel.setBorder( BorderFactory.createEmptyBorder( 5, 5, 5, 5 ) );
            appendAndSaveButton = new JButton( AppLocale.COMMAND_CREATE_NEW_REGISTRY_FILE );
            appendAndSaveButton.setBackground( Color.WHITE );
            appendAndSaveButton.setHorizontalAlignment( JLabel.LEFT );
            
            
            // add panels to root + 1th deepth
            headerPanel.add( headerRow1Panel );
            headerPanel.add( fileConfRow2Panel );
            add( headerPanel, BorderLayout.NORTH );
            
            centerPanel.add( centerHeadertPanel, BorderLayout.NORTH );
            centerPanel.add( centerContentPanel, BorderLayout.CENTER );
            add( centerPanel, BorderLayout.CENTER );
            
            appendAndSavePanel.add( appendAndSaveButton, BorderLayout.EAST );
            add( appendAndSavePanel, BorderLayout.SOUTH );

        }
        catch ( Exception e ) {
            LOGGER.error( e.getMessage(), e );
        }
    }
    
    private void initListeners( ) {
        
        
        chooseFileJButton.appendListener(new ActionListener() {
            public void actionPerformed( ActionEvent ae ) {
                JFileChooser fileChooser = new JFileChooser();

                String currentPath = (StringUtils.isNotBlank( filePathField.getText() )) ? filePathField.getText()
                        : MainWindow.class.getProtectionDomain().getCodeSource().getLocation().getPath();

                fileChooser.setCurrentDirectory( new File( currentPath ) );
                int returnValue = fileChooser.showOpenDialog( GUIUtils.getJFrameIcon() );
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    selectedFile = fileChooser.getSelectedFile();
                    filePathField.setText( selectedFile.getPath() );
                }
            }
        });
        
               
     puttySessionsTreePanel.appendListener( new TreeSelectionListener() {
         public void valueChanged(TreeSelectionEvent e) {
             DefaultMutableTreeNode node = (DefaultMutableTreeNode) puttySessionsTreePanel.getjTree().getLastSelectedPathComponent();

             if (node == null) return;

             Object nodeInfo = node.getUserObject();
             System.out.println( nodeInfo );
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
