package net.wlodi.tools.putty.view.panel;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;

import net.wlodi.tools.putty.service.PuttySessionService;
import net.wlodi.tools.putty.service.dto.PuttySessionEntryDiffDTO;
import net.wlodi.tools.putty.view.common.PuttySessionEntryDiifTableModel;
import net.wlodi.tools.putty.view.common.WhiteJScrollPane;


public class PuttySessionPanel extends WhiteJScrollPane {

    private static final long serialVersionUID = -3608174121587252386L;

    private PuttySessionService puttySessionService = PuttySessionService.inst();

    private String sessionName;

    private PuttySessionEntryDiifTableModel puttySessionEntryDiifTableModel;

    private JTable table;

    public PuttySessionPanel() throws IOException , InterruptedException {
        puttySessionEntryDiifTableModel = new PuttySessionEntryDiifTableModel( new ArrayList<PuttySessionEntryDiffDTO>() );
        initPanels();
        initGUI();
    }

    private void initPanels( ) throws IOException , InterruptedException {
        table = new JTable( puttySessionEntryDiifTableModel ) {

            private static final long serialVersionUID = -5989858959747580556L;

            @Override
            public Class< ? > getColumnClass( int column ) {
                return PuttySessionEntryDiffDTO.getColumnClass( column );
            }
        };
    }

    private void initGUI( ) {

        table.setRowHeight( 25 );
        table.getColumnModel().getColumn( 0 ).setMaxWidth( 55 );

        setViewportView( table );
    }

    public void updatePuttySession( String sessionName ) throws IOException , InterruptedException {
        List<PuttySessionEntryDiffDTO> newRows = puttySessionService.getPuttySessionEntryDiffList( sessionName );
        puttySessionEntryDiifTableModel.removeRows();
        puttySessionEntryDiifTableModel.addRows( sessionName, newRows );
        puttySessionEntryDiifTableModel.fireTableDataChanged();
        this.sessionName = sessionName;
    }

    public void updateCurrentPuttySession( ) throws IOException , InterruptedException {
        if (sessionName != null) {
            updatePuttySession( sessionName );
        }
    }

    public String getSessionName( ) {
        return sessionName;
    }

}
