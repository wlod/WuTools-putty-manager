package net.wlodi.tools.putty.view.panel;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.wlodi.tools.putty.repository.PuttySessionWindowsRegistryRepository;
import net.wlodi.tools.putty.repository.conf.AppLocale;
import net.wlodi.tools.putty.service.PuttySessionService;
import net.wlodi.tools.putty.view.common.PuttySessionTreeNode;
import net.wlodi.tools.putty.view.common.WhiteJScrollPane;


public class PuttySessionsTreePanel extends WhiteJScrollPane {

    private static final long serialVersionUID = 457139083953323485L;

    @SuppressWarnings ( "unused" )
    private static final Logger LOGGER = LoggerFactory.getLogger( PuttySessionsTreePanel.class );

    private PuttySessionWindowsRegistryRepository puttySessionRepository = PuttySessionWindowsRegistryRepository.inst();

    private PuttySessionService puttySessionService = PuttySessionService.inst();

    private JTree jTree;

    // TODO get from DefaultMutableTreeNode root
    private List<PuttySessionTreeNode> puttySessionTreeNodes = new ArrayList<>();

    public PuttySessionsTreePanel() throws IOException , InterruptedException {
        initPanels();
        initGUI();
    }

    private void initPanels( ) throws IOException , InterruptedException {
        jTree = new JTree();
        DefaultMutableTreeNode root = new PuttySessionTreeNode( AppLocale.LABEL_PUTTY_SESSIONS_SHORT );
        for ( String sessionName : puttySessionRepository.getSessionsName() ) {
            PuttySessionTreeNode treeNode = new PuttySessionTreeNode( sessionName );
            puttySessionTreeNodes.add( treeNode );
            root.add( treeNode );
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

    public void updateModel( ) {
        for ( PuttySessionTreeNode puttySessionTreeNode : puttySessionTreeNodes ) {
            Integer numberOfChanges = puttySessionService.getNumberOfChanges( puttySessionTreeNode.getSessionName() );
            System.out.println( "numberOfChanges " + numberOfChanges + " puttySessionTreeNode.getSessionName() " + puttySessionTreeNode.getSessionName()  );
            puttySessionTreeNode.setNumberOfChanges( numberOfChanges );
        }
        revalidate();
        repaint();
    }

}
