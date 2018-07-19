package net.wlodi.tools.putty.view.panel;

import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.apache.commons.lang3.StringUtils;

import net.wlodi.tools.putty.repository.conf.AppConf;
import net.wlodi.tools.putty.repository.conf.AppLocale;
import net.wlodi.tools.putty.view.common.ChooseFileJButton;
import net.wlodi.tools.putty.view.common.SimpleDocumentListener;
import net.wlodi.tools.putty.view.common.WhiteJPanel;
import net.wlodi.tools.putty.view.utils.GUIUtils;

public class FilePanel extends WhiteJPanel {

    private static final long serialVersionUID = 4753513791986407496L;
    
    private ChooseFileJButton chooseFileJButton;
    
    private JTextField filePathField;

    private File selectedFile;
    
    public FilePanel(LayoutManager layout ) {
        super( layout );
        initGUI();
        initListeners();
    }

    private void initGUI( ) {
        add( new JLabel( AppLocale.LABEL_NAME_FILE_PATH ), "align left, cell 0 0 12 1, height pref+4px" ); // cell column row width height
        add( filePathField = new JTextField( 200 ), "cell 0 1 10 1, height pref+4px" );
        add( chooseFileJButton = new ChooseFileJButton(), "align right, cell 10 1 2 1, height pref+4px" ); // cell column row width height
    }
    
    private void initListeners( ) {
        chooseFileJButton.appendListener( new ActionListener() {

            public void actionPerformed( ActionEvent ae ) {
                
                String currentPath = (StringUtils.isNotBlank( filePathField.getText() )) ? filePathField.getText() : AppConf.CURRENT_PATH;
                
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory( new File( currentPath ) );
                
                int returnValue = fileChooser.showOpenDialog( GUIUtils.getJFrameIcon() );
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    selectedFile = fileChooser.getSelectedFile();
                    filePathField.setText( selectedFile.getPath() );
                }
            }
        } );
        
    }
    
    public void appendListener( SimpleDocumentListener filePathListener ) {
        filePathField.getDocument().addDocumentListener(filePathListener);
    }

    public File getSelectedFile( ) {
        return selectedFile;
    }
    
    
}
