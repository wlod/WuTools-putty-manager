package net.wlodi.tools.putty.view.common;


import java.awt.Color;
import java.awt.LayoutManager;

import javax.swing.JPanel;
import javax.swing.border.Border;


public class WhiteJPanel extends JPanel {

    private static final long serialVersionUID = 1905054739565366186L;
    
    public WhiteJPanel( LayoutManager layout, Border border ) {
        super( layout );
        setBackground( Color.WHITE );
        setBorder( border );
    }
    
    public WhiteJPanel( LayoutManager layout ) {
        super( layout );
        setBackground( Color.WHITE );
    }

    public WhiteJPanel() {
        super();
        setBackground( Color.WHITE );
    }

}
