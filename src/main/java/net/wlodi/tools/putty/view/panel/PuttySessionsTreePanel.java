package net.wlodi.tools.putty.view.panel;


import java.io.IOException;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import net.wlodi.tools.putty.repository.PuttySessionWindowsRegistryRepository;
import net.wlodi.tools.putty.repository.conf.AppLocale;
import net.wlodi.tools.putty.view.common.WhiteJScrollPane;


public class PuttySessionsTreePanel extends WhiteJScrollPane {

    private static final long serialVersionUID = 457139083953323485L;

    private PuttySessionWindowsRegistryRepository puttySessionRepository = PuttySessionWindowsRegistryRepository.inst();

    private JTree jTree;

    public PuttySessionsTreePanel() throws IOException , InterruptedException {
        initPanels();
        initGUI();
    }

    private void initPanels( ) throws IOException , InterruptedException {
        jTree = new JTree();
        DefaultMutableTreeNode root = new DefaultMutableTreeNode( AppLocale.LABEL_PUTTY_SESSIONS_SHORT );
        for ( String sessionName : puttySessionRepository.getSessionsName() ) {
            root.add( new DefaultMutableTreeNode( sessionName ) );
        }
        jTree.setModel( new DefaultTreeModel( root ) );
        add( jTree );
    }

    private void initGUI( ) {
        setViewportView( jTree );
    }

    public void appendListener( TreeSelectionListener treeSelectionListener ) {
        jTree.addTreeSelectionListener( treeSelectionListener );
    }

    public JTree getjTree( ) {
        return jTree;
    }

}
