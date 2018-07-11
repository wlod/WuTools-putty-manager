package net.wlodi.tools.putty.repository.dto;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

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
