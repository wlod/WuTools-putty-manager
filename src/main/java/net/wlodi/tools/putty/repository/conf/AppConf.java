package net.wlodi.tools.putty.repository.conf;

import java.util.List;
import java.util.Collections;

import static java.util.stream.Stream.of;
import static java.util.stream.Collectors.toList;


public class AppConf {

    public static final String REGISTRY_KEY = "HKEY_CURRENT_USER\\Software\\SimonTatham\\PuTTY\\Sessions";
    public static final String REGISTRY_KEY_FOR_REPLACE = "HKEY_CURRENT_USER\\\\Software\\\\SimonTatham\\\\PuTTY\\\\Sessions\\\\";

    public static final String CURRENT_PATH = AppConf.class.getProtectionDomain().getCodeSource().getLocation().getPath();

    public static final String REGISTRY_EXPORTED_FILE_KEY_TYPE_VALUE_PATTERN = "^\".*\"=.*";
    public static final String REGISTRY_FIRST_LINE_NAME_PATTERN = "Windows Registry Editor Version";
    
    public static final List<String> REGISTRY_IGNORE_NAMES = Collections.unmodifiableList( of(
                                                                    "hostName".toLowerCase() )
                                                                    .collect( toList() ) );
    
    /**
     * TODO args from command line 
     */
    public static boolean LOAD_ALL_SESSIONS_ENTRY_ON_STARTUP = true;
    
}
