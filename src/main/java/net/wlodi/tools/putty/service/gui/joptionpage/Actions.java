package net.wlodi.tools.putty.service.gui.joptionpage;


import javax.swing.JOptionPane;


public abstract class Actions {

    public void runAction( int jOptionPaneReturnValue ) {
        switch ( jOptionPaneReturnValue ) {
            case JOptionPane.YES_OPTION:
                runYesOption();
                break;
            case JOptionPane.NO_OPTION:
                runNoOption();
                break;
            case JOptionPane.CANCEL_OPTION:
                runCancelOption();
                break;
                
            case JOptionPane.CLOSED_OPTION:
                runClosedOption();
                break;
                
            default:
                throw new IllegalStateException( "Not recognize the input value: " + jOptionPaneReturnValue );
        }
    }

    protected void runYesOption( ) {
        
    }

    protected void runCancelOption( ) {
        
    }

    protected void runNoOption( ) {
        
    }

    protected void runClosedOption( ) {
        
    }

}