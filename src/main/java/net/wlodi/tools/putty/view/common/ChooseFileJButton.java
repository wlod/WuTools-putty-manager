package net.wlodi.tools.putty.view.common;


import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import net.wlodi.tools.putty.view.utils.GUIUtils;


public class ChooseFileJButton extends JButton {

    private static final long serialVersionUID = -1222862595810467065L;

    public ChooseFileJButton() {

        setMargin( new Insets( 0, 0, 0, 0 ) );
        setBorder( BorderFactory.createEmptyBorder() );
        setIcon( GUIUtils.CHOOSE_FILE_ICON );
        setBorderPainted( false );
        setContentAreaFilled( false );
        setFocusPainted( false );
        setOpaque( false );

    }

    public void appendListener( ActionListener actionListener ) {
        addActionListener( actionListener );
    }

}
