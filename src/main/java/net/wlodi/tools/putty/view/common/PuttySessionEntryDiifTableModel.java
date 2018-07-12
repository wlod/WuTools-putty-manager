package net.wlodi.tools.putty.view.common;


import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.wlodi.tools.putty.repository.conf.AppLocale;
import net.wlodi.tools.putty.service.dto.PuttySessionEntryDiffDTO;


public class PuttySessionEntryDiifTableModel extends AbstractTableModel {

    private static final long serialVersionUID = -8107099027866494848L;

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
    }

    @Override
    public String getColumnName( int columnIndex ) {
        return AppLocale.PUTTY_SESSION_COLUMN_NAME[columnIndex];
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

    public boolean addRow( PuttySessionEntryDiffDTO row ) {
        return rows.add( row );
    }

    public boolean addRows( List<PuttySessionEntryDiffDTO> rows ) {
        return this.rows.addAll( rows );
    }

}
