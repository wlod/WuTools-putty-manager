package net.wlodi.tools.putty.view.common;


import javax.swing.tree.DefaultMutableTreeNode;


public class PuttySessionTreeNode extends DefaultMutableTreeNode {

    private static final long serialVersionUID = 6041342818413999419L;
    private String sessionName;
    private Integer numberOfChanges;

    public PuttySessionTreeNode( String sessionName ) {
        super( sessionName );
        this.sessionName = sessionName;
    }

    private String numberOfChanges() {
        return numberOfChanges != null ? " (" + numberOfChanges + ") - " : "";
    }
    
    @Override
    public String toString( ) {
        return numberOfChanges() + sessionName;
    }

    
    public String getSessionName( ) {
        return sessionName;
    }

    
    public Integer getNumberOfChanges( ) {
        return numberOfChanges;
    }

    
    public void setNumberOfChanges( Integer numberOfChanges ) {
        this.numberOfChanges = numberOfChanges;
    }
    
}
