package net.wlodi.tools.putty.repository.dto;


public enum RegistryType {
    
    REG_DWORD("dword"),
    REG_SZ("");
    
    
    private String nameFromExportedFile;

    private RegistryType(String nameFromExportedFile) {
        this.nameFromExportedFile = nameFromExportedFile;
    }

    
    public String nameFromExportedFile( ) {
        return nameFromExportedFile;
    }
    
}
