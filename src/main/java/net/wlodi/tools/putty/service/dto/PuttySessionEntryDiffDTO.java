package net.wlodi.tools.putty.service.dto;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import net.wlodi.tools.putty.repository.conf.AppConf;
import net.wlodi.tools.putty.repository.dto.PuttySessionEntryDTO;

public class PuttySessionEntryDiffDTO {
    
    public static int COLUMN_COUNT_FOR_VIEW = 6;
    
    // TODO maybe below values should be move to AppLocale
    public static String IGNORE_REGISTRY = "The registry entry is ignore.";
    public static String NEW_VALUE_REGISTRY = "Value will be replace.";
    
    boolean append; 
    
    String name;

    String type;

    String value;
    
    String newValue;
    
    String comment;
    
    public static PuttySessionEntryDiffDTO mapFrom(PuttySessionEntryDTO puttySessionEntry, Optional<PuttySessionEntryDTO> puttySessionFileEntry) {
        PuttySessionEntryDiffDTO puttySessionEntryDiff = new PuttySessionEntryDiffDTO();
        puttySessionEntryDiff.name = puttySessionEntry.getName();
        puttySessionEntryDiff.type = puttySessionEntry.getType();
        puttySessionEntryDiff.value = puttySessionEntry.getValue();
        
        if(puttySessionFileEntry.isPresent() == false) {
            return puttySessionEntryDiff;
        }
        
        puttySessionEntryDiff.newValue = puttySessionFileEntry.get().getValue();

        if(AppConf.REGISTRY_IGNORE_NAMES.contains( puttySessionEntryDiff.name.toLowerCase() )) {
            puttySessionEntryDiff.comment = IGNORE_REGISTRY;
        }
        else
        if( (StringUtils.isBlank( puttySessionEntryDiff.value ) && StringUtils.isNotBlank( puttySessionEntryDiff.newValue)) ||
            (StringUtils.isNotBlank( puttySessionEntryDiff.value ) && puttySessionEntryDiff.value.equals( puttySessionEntryDiff.newValue ) == false )) {
            puttySessionEntryDiff.comment = NEW_VALUE_REGISTRY;
            puttySessionEntryDiff.append = true;
        }
        
        return puttySessionEntryDiff;
    }

    private PuttySessionEntryDiffDTO() {
        
    }
    
    public Object getValueForColumn( int columnIndex ) {
        validateAccess( columnIndex );
        Object valueForColumn = null;
        switch ( columnIndex ) {
            case 0:
               valueForColumn = append;
               break;
            case 1:
                valueForColumn = name;
                break;
            case 2:
                valueForColumn = type;
                break;
            case 3:
                valueForColumn = value;
                break;
            case 4:
                valueForColumn = newValue;
                break;
            case 5:
                valueForColumn = comment;
                break;
            default:
                break;
        }

        return valueForColumn;
    }

    public void saveValue( int columnIndex , Object newValue ) {
        
        if(columnIndex == 0) {
            append = (boolean) newValue;
        }
        else {
            throw new UnsupportedOperationException( "The operation is not implemented yet!" );
        }
    }
    
    public static Class<?> getColumnClass(int column) {
        switch (column) {
            case 0:
                return Boolean.class;
            default:
                return String.class;
        }
    }
    
    public String getComment( ) {
        return comment;
    }
    
    
    
    public String getName( ) {
        return name;
    }

    public String getType( ) {
        return type;
    }

    
    public String getNewValue( ) {
        return newValue;
    }

    
    public boolean isAppend( ) {
        return append;
    }

    private static void validateAccess( int columnIndex ) {
        if (columnIndex > COLUMN_COUNT_FOR_VIEW) {
            throw new IllegalArgumentException( "Column index cannot be greater than column count: " + COLUMN_COUNT_FOR_VIEW + ", columnIndex equals " + columnIndex );
        }
    }

    @Override
    public String toString( ) {
        return "PuttySessionEntryDiffDTO [name=" + name + ", type=" + type + ", value=" + value + ", newValue=" + newValue + ", comment=" + comment + "]";
    }
    
}
