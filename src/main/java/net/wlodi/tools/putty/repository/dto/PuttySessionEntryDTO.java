package net.wlodi.tools.putty.repository.dto;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

import net.wlodi.tools.putty.repository.JavaUtils;

public class PuttySessionEntryDTO {

    String name;

    String type;

    String value;

    public static PuttySessionEntryDTO createFromRepositoryRawLine( String repositoryRawLine ) {
        List<String> nameTypeValue = Stream.of(repositoryRawLine.split("    "))
                .filter( StringUtils::isNotBlank )
                .collect( Collectors.toList() );
        
        if(nameTypeValue.size() < 2 || nameTypeValue.size() > 3) {
            throw new IllegalStateException("Can not reads [name] and [type] and [value] from repository row line. Line: " + nameTypeValue);
        }
        
        String name = nameTypeValue.get( 0 );
        String type = nameTypeValue.get( 1 );
        String value = nameTypeValue.size() == 3 ? nameTypeValue.get( 2 ) : null;
        
        return new PuttySessionEntryDTO(name, type, value);
    }
    
    /**
     * Example for repositoryRawLine
     * 
     * "WideBoldFont"=""
     * "WideBoldFontIsBold"=dword:00000000
     * 
     * @param repositoryRawLine
     * @return
     */
    public static PuttySessionEntryDTO createFromFileRawLine( String repositoryRawLine ) {
        
        
        String[] keyValue = repositoryRawLine.split( "\"=" );
        
        if(keyValue.length != 2) {
            throw new IllegalStateException("Problem with parsing following file raw line: " + repositoryRawLine);
        }
        
        String typeValue = keyValue[1];
        
        String name = keyValue[0].replaceFirst( "\"", "" );
        String type = null;
        String value = null;
        
        if(typeValue.startsWith( RegistryType.REG_DWORD.nameFromExportedFile() )) {
            value = keyValue[1].replaceFirst( RegistryType.REG_DWORD.nameFromExportedFile() + ":", "");
            type = RegistryType.REG_DWORD.name();
        }
        else {
            value = JavaUtils.replaceLast( keyValue[1], "\"", "" )
                             .replaceFirst( "\"", "" );
            type = RegistryType.REG_SZ.name();
        }
        
        return new PuttySessionEntryDTO(name, type, value);
    }

    private PuttySessionEntryDTO( String name , String type , String value ) {
        super();
        this.name = name;
        this.type = type;
        this.value = value;
    }
    
    public String getName( ) {
        return name;
    }

    
    public String getType( ) {
        return type;
    }

    
    public String getValue( ) {
        return value;
    }

    @Override
    public String toString( ) {
        return "PuttySessionEntry [name=" + name + ", type=" + type + ", value=" + value + "]";
    }
    
}
