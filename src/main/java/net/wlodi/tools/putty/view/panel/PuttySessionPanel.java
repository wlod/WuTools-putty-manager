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

    private PuttySessionService puttySessionEntryDiffService = PuttySessionService.inst();

    private PuttySessionEntryDiifTableModel puttySessionEntryDiifTableModel;

    private JTable table;

    public PuttySessionPanel() throws IOException , InterruptedException {
        puttySessionEntryDiifTableModel = new PuttySessionEntryDiifTableModel( new ArrayList<PuttySessionEntryDiffDTO>() );
        initPanels();
        initGUI();
    }

    private void initPanels( ) throws IOException , InterruptedException {
        table = new JTable( puttySessionEntryDiifTableModel );
    }

    private void initGUI( ) {
        setViewportView( table );
    }

    public void updatePuttySession( String sessionName ) throws IOException , InterruptedException {
        List<PuttySessionEntryDiffDTO> newRows = puttySessionEntryDiffService.getPuttySessionEntryDiffSList( sessionName );
        puttySessionEntryDiifTableModel.removeRows();
        puttySessionEntryDiifTableModel.addRows( newRows );
        puttySessionEntryDiifTableModel.fireTableDataChanged();
    }

}