package net.wlodi.tools.putty.repository.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum RegistryType {
    
    REG_DWORD( "dword" ),
    REG_SZ( "" );
    
    private static final Logger LOGGER = LoggerFactory.getLogger( RegistryType.class );
    
    private String nameFromExportedFile;

    private RegistryType( String nameFromExportedFile ) {
        this.nameFromExportedFile = nameFromExportedFile;
    }

    public String nameFromExportedFile( ) {
        return nameFromExportedFile;
    }
    
    public static String getTypeNameForFile(String type) {
        for(RegistryType registryType: values()) {
            if(registryType.name().toLowerCase().equals( type.toLowerCase() )) {
                return registryType.nameFromExportedFile;
            }
        }
        LOGGER.warn( "Type {} is not recognized - return empty value.", type );
        return "";
    }

}
