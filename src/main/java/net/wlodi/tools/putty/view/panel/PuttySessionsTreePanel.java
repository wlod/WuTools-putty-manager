package net.wlodi.tools.putty.view.panel;


import java.awt.Color;
import java.awt.Point;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.JViewport;
import javax.swing.event.TreeSelectionListener;
import javax.swing.plaf.basic.BasicTreeUI;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import net.wlodi.tools.putty.repository.PuttySessionWindowsRegistryRepository;
import net.wlodi.tools.putty.repository.conf.AppLocale;


public class PuttySessionsTreePanel extends JScrollPane {

    private static final long serialVersionUID = 457139083953323485L;

    private PuttySessionWindowsRegistryRepository puttySessionRepository = PuttySessionWindowsRegistryRepository.inst();

    private JTree jTree;

    public PuttySessionsTreePanel() throws IOException , InterruptedException {
        initPanels();
        initGUI();
    }

    private void initPanels( ) throws IOException, InterruptedException {
        jTree = new JTree();
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(AppLocale.LABEL_PUTTY_SESSIONS_SHORT);
        for ( String sessionName : puttySessionRepository.getSessionsName() ) {
            root.add( new DefaultMutableTreeNode( sessionName ) );
        }
        jTree.setModel( new DefaultTreeModel( root ) );
        add( jTree );
    }

    private void initGUI( ) {
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground( Color.WHITE );
        
        setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS );
        getViewport().setViewPosition( new Point( 0, 0 ) );
        getViewport().setScrollMode( JViewport.BACKINGSTORE_SCROLL_MODE );
        setViewportBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        
        setFocusable( true );
        requestFocusInWindow();
        setOpaque( true );
        
        setViewportView(jTree);
        
        /*DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) jTree.getCellRenderer();
        renderer.setLeafIcon(null);
        renderer.setClosedIcon(null);
        renderer.setOpenIcon(null);
        
        jTree.setCellRenderer(new DefaultTreeCellRenderer() {
            @Override
            public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
                JLabel component = (JLabel) super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
                ImageIcon iconPath = ((WizardNode) value).getIcon();
                component.setIcon(iconPath);
                return this;
            }
        });
        
        jTree.setUI(new BasicTreeUI() {
            @Override
            protected boolean shouldPaintExpandControl(final TreePath path, final int row
                    , final boolean isExpanded, final boolean hasBeenExpanded, final boolean isLeaf)
            {
                boolean shouldDisplayExpandControl = false;
                return shouldDisplayExpandControl;
            }
        };
        */
        
    }

    public void appendListener( TreeSelectionListener treeSelectionListener ) {
        jTree.addTreeSelectionListener( treeSelectionListener );
    }

    
    public JTree getjTree( ) {
        return jTree;
    }

}
