package net.wlodi.tools.putty.view.panel;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.LayoutManager;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.border.Border;

import net.wlodi.tools.putty.repository.conf.AppLocale;
import net.wlodi.tools.putty.view.common.WhiteJPanel;


public class BottomPanel extends WhiteJPanel {

    private static final long serialVersionUID = 8417467752266201984L;
    
    private JButton appendAndSaveButton;
    
    public BottomPanel( LayoutManager layout , Border border ) {
        super( layout, border );
        iniGUI();
    }

    private void iniGUI( ) {
        add( appendAndSaveButton = new JButton( AppLocale.COMMAND_CREATE_NEW_REGISTRY_FILE ), BorderLayout.EAST );
        appendAndSaveButton.setBackground( Color.WHITE );
        appendAndSaveButton.setHorizontalAlignment( JLabel.LEFT );
    }

}
