package net.wlodi.tools.putty.view.common;

import java.awt.Color;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

public class WhiteJScrollPane extends JScrollPane {

    private static final long serialVersionUID = 8837111188206185244L;
    
    public WhiteJScrollPane() {
        setBorder( BorderFactory.createEmptyBorder( 10, 10, 10, 10 ) );
        setBackground( Color.WHITE );
        
        setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS );
        getViewport().setViewPosition( new Point( 0, 0 ) );
        getViewport().setScrollMode( JViewport.BACKINGSTORE_SCROLL_MODE );
        getViewport().setBackground( Color.WHITE );
        setViewportBorder( BorderFactory.createEmptyBorder( 0, 0, 0, 0 ) );

        setFocusable( true );
        requestFocusInWindow();
        setOpaque( true );
    }
    
}
