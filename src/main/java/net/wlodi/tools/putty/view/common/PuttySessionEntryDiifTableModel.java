package net.wlodi.tools.putty.view.common;


import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.wlodi.tools.putty.repository.conf.AppLocale;
import net.wlodi.tools.putty.service.PuttySessionService;
import net.wlodi.tools.putty.service.dto.PuttySessionEntryDiffDTO;
import net.wlodi.tools.putty.view.window.MainWindow;


public class PuttySessionEntryDiifTableModel extends AbstractTableModel {

    private static final long serialVersionUID = -8107099027866494848L;
    
    private PuttySessionService puttySessionService = PuttySessionService.inst();
    
    private String sessionName;
    
    private List<PuttySessionEntryDiffDTO> rows;

    private static final Logger LOGGER = LoggerFactory.getLogger( PuttySessionEntryDiifTableModel.class );

    public PuttySessionEntryDiifTableModel( List<PuttySessionEntryDiffDTO> rows ) {
        super();
        if (rows == null) {
            throw new IllegalArgumentException( "Param cannot be null." );
        }
        this.rows = rows;
    }

    @Override
    public int getRowCount( ) {
        return rows.size();
    }

    @Override
    public int getColumnCount( ) {
        return PuttySessionEntryDiffDTO.COLUMN_COUNT_FOR_VIEW;
    }

    @Override
    public Object getValueAt( int rowIndex , int columnIndex ) {
        if (rows.size() <= rowIndex) {
            LOGGER.error( "The method try get value from row: [{}], but the size of rows is {}.", rowIndex, rows.size() );
            return null;
        }
        return rows.get( rowIndex ).getValueForColumn( columnIndex );
    }

    @Override
    public void setValueAt( Object aValue , int rowIndex , int columnIndex ) {
        PuttySessionEntryDiffDTO line = rows.get( rowIndex );
        line.saveValue( columnIndex, aValue );
        
        // TODO ;-(
        if(columnIndex == 0) {
            puttySessionService.updateNumberOfChanges( sessionName, (boolean) aValue ? 1 : -1 );
            MainWindow.inst().getPuttySessionsTreePanel().updateModel();
        }
    }

    @Override
    public String getColumnName( int columnIndex ) {
        return AppLocale.PUTTY_SESSION_COLUMN_NAME[columnIndex];
    }

    @Override
    public boolean isCellEditable( int rowIndex , int colIndex ) {
        return colIndex == 0;
    }

    public PuttySessionEntryDiffDTO getValueAt( int rowIndex ) {
        if (rows.size() <= rowIndex) {
            LOGGER.error( "The method try get value from row: [{}], but the size of rows is {}.", rowIndex, rows.size() );
            return null;
        }
        return rows.get( rowIndex );
    }

    public void removeRows( ) {
        rows.clear();
    }

    public boolean addRows( String sessionName, List<PuttySessionEntryDiffDTO> rows ) {
        this.sessionName = sessionName;
        return this.rows.addAll( rows );
    }

}
