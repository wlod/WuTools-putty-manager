package net.wlodi.tools.putty.view.panel;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.border.Border;

import net.wlodi.tools.putty.repository.conf.AppConf;
import net.wlodi.tools.putty.repository.conf.AppLocale;
import net.wlodi.tools.putty.service.PuttySessionService;
import net.wlodi.tools.putty.view.common.WhiteJPanel;
import net.wlodi.tools.putty.view.utils.GUIUtils;
import net.wlodi.tools.putty.view.window.MainWindow;


public class BottomPanel extends WhiteJPanel {

    private static final long serialVersionUID = 8417467752266201984L;

    private PuttySessionService puttySessionService = PuttySessionService.inst();
    
    private JButton createRegistryFileButton;

    private File selectedFile;
    
    public BottomPanel( LayoutManager layout , Border border ) {
        super( layout, border );
        iniGUI();
        initListeners();
    }

    private void iniGUI( ) {
        add( createRegistryFileButton = new JButton( AppLocale.COMMAND_CREATE_NEW_REGISTRY_FILE ), BorderLayout.EAST );
        createRegistryFileButton.setBackground( Color.WHITE );
        createRegistryFileButton.setHorizontalAlignment( JLabel.LEFT );
        disableActions();
    }

    private void initListeners( ) {
        createRegistryFileButton.addActionListener( new ActionListener() {

            public void actionPerformed( ActionEvent ae ) {

                String currentPath = selectedFile != null ? selectedFile.getPath() : AppConf.CURRENT_PATH;

                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle( AppLocale.DIALOG_WINDOW_SAVE_NEW_WINDOWS_REGISTER_FILE );
                fileChooser.setCurrentDirectory( new File( currentPath ) );

                int returnValue = fileChooser.showOpenDialog( GUIUtils.getJFrameIcon() );
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    selectedFile = fileChooser.getSelectedFile();
                    createFile();
                }
            }

            private void createFile( ) {
                MainWindow.inst().startProcessing();
                Runnable createFileTask = () -> {
                    puttySessionService.createRegitryMergeFile( selectedFile );
                    MainWindow.inst().stopProcessing();
                };
                Thread thread = new Thread(createFileTask, "create-file");
                thread.start();
            }
        } );
    }
    
    public void disableActions( ) {
        createRegistryFileButton.setEnabled( false );
    }

    public void enableActions( ) {
        createRegistryFileButton.setEnabled( true );
    }

}
