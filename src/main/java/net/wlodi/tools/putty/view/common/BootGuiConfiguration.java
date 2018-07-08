package net.wlodi.tools.putty.view.common;


import java.awt.Color;

import javax.swing.UIManager;


public class BootGuiConfiguration {

    public static void boot() {
        UIManager.put( "OptionPane.background", Color.WHITE );
        UIManager.put( "Panel.background", Color.WHITE );
    }
    
}
