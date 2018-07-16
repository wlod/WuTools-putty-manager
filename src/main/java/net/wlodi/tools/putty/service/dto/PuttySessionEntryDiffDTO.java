package net.wlodi.tools.putty.service.dto;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import net.wlodi.tools.putty.repository.conf.AppConf;
import net.wlodi.tools.putty.repository.dto.PuttySessionEntryDTO;

public class PuttySessionEntryDiffDTO {
    
    public static int COLUMN_COUNT_FOR_VIEW = 5;
    
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
            puttySessionEntryDiff.comment = "The registry entry is ignore.";
        }
        else
        if( (StringUtils.isBlank( puttySessionEntryDiff.value ) && StringUtils.isNotBlank( puttySessionEntryDiff.newValue)) ||
            (StringUtils.isNotBlank( puttySessionEntryDiff.value ) && puttySessionEntryDiff.value.equals( puttySessionEntryDiff.newValue ) == false )) {
            puttySessionEntryDiff.comment = "Value will be replace.";
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
                valueForColumn = name;
                break;
            case 1:
                valueForColumn = type;
                break;
            case 2:
                valueForColumn = value;
                break;
            case 3:
                valueForColumn = newValue;
                break;
            case 4:
                valueForColumn = comment;
                break;
            default:
                break;
        }

        return valueForColumn;
    }

    public void saveValue( int columnIndex , Object newValue ) {
        throw new UnsupportedOperationException( "The operation is not implemented yet!" );
    }
    
    private static void validateAccess( int columnIndex ) {
        if (columnIndex > COLUMN_COUNT_FOR_VIEW) {
            throw new IllegalArgumentException( "Column index cannot be greater than column count: " + COLUMN_COUNT_FOR_VIEW + ", columnIndex equals " + columnIndex );
        }
    }
    
}
