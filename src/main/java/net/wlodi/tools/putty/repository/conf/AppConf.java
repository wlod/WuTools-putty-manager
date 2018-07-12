package net.wlodi.tools.putty.repository.conf;

public class AppConf {

    public static final String REGISTRY_KEY = "HKEY_CURRENT_USER\\Software\\SimonTatham\\PuTTY\\Sessions";
    public static final String REGISTRY_KEY_FOR_REPLACE = "HKEY_CURRENT_USER\\\\Software\\\\SimonTatham\\\\PuTTY\\\\Sessions\\\\";

    public static final String CURRENT_PATH = AppConf.class.getProtectionDomain().getCodeSource().getLocation().getPath();
    
    public static final String REGISTRY_EXPORTED_FILE_KEY_TYPE_VALUE_PATTERN = "^\".*\"=.*";
    
}
