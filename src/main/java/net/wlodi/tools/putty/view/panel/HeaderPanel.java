package net.wlodi.tools.putty.view.panel;

import java.awt.BorderLayout;
import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import net.wlodi.tools.putty.repository.conf.AppLocale;
import net.wlodi.tools.putty.view.common.WhiteJPanel;
import net.wlodi.tools.putty.view.utils.GUIUtils;


public class HeaderPanel extends WhiteJPanel {

    private static final long serialVersionUID = -1835033578160474782L;
    
    public HeaderPanel ( LayoutManager layout ) {
        super( layout );
        initGUI();
    }

    private void initGUI( ) {
        setBorder( BorderFactory.createEmptyBorder( 10, 10, 10, 10 ) );
        add( new JLabel( AppLocale.HELP_DESCRIPTION ), BorderLayout.CENTER );
        add( GUIUtils.makeIconButton( GUIUtils.APPEND_CONFIGURATION_ICON, AppLocale.APPEND_CONFIGURATION_ICON_ALT ), BorderLayout.EAST );
    }
    
}
