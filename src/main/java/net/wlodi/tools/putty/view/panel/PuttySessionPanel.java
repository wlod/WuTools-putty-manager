package net.wlodi.tools.putty.view.panel;


import java.awt.Color;
import java.awt.Point;
import java.io.IOException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;

import net.wlodi.tools.putty.service.PuttySessionService;
import net.wlodi.tools.putty.service.dto.PuttySessionEntryDiffDTO;
import net.wlodi.tools.putty.view.common.PuttySessionEntryDiifTableModel;


public class PuttySessionPanel extends JScrollPane {

    private static final long serialVersionUID = -3608174121587252386L;
    
    private PuttySessionService puttySessionEntryDiffService = PuttySessionService.inst();
    
    private PuttySessionEntryDiifTableModel puttySessionEntryDiifTableModel;
    
    private JTable table;
    
    public PuttySessionPanel() throws IOException , InterruptedException {
        initPanels();
        initGUI();
    }

    private void initPanels( ) throws IOException, InterruptedException {
        
        puttySessionEntryDiifTableModel = new PuttySessionEntryDiifTableModel(puttySessionEntryDiffService.getPuttySessionEntryDiffSList( "aktualizacja@10.48.192.77-hyper-jboss" ));
        
        table = new JTable( puttySessionEntryDiifTableModel );
    }

    private void initGUI( ) {
        setBorder( BorderFactory.createEmptyBorder( 10, 0, 10, 10 ) );
        setBackground( Color.WHITE );

        setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS );
        getViewport().setViewPosition( new Point( 0, 0 ) );
        getViewport().setScrollMode( JViewport.BACKINGSTORE_SCROLL_MODE );
        setViewportBorder( BorderFactory.createEmptyBorder( 0, 0, 0, 0 ) );

        setFocusable( true );
        requestFocusInWindow();
        setOpaque( true );
        
        setViewportView(table);
    }

    public void updatePuttySession( String sessionName ) throws IOException, InterruptedException {
        List<PuttySessionEntryDiffDTO> newRows = puttySessionEntryDiffService.getPuttySessionEntryDiffSList( sessionName );
        puttySessionEntryDiifTableModel.removeRows();
        puttySessionEntryDiifTableModel.addRows( newRows );
        puttySessionEntryDiifTableModel.fireTableDataChanged();
    }

}
