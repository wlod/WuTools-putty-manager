package net.wlodi.tools.putty.repository.conf;


public class AppLocale {

    public static final String APP_TITLE = "WuTools: PuTTY Session Manager";

    public static final String MAIN_WINDOW_TITLE = "Main";
    public static final String MAIN_WINDOW_DIALOG = APP_TITLE;
    
    // actions
    public static final String COMMAND_CREATE_NEW_REGISTRY_FILE = "Create new registry file";
    
    // dialog
    public static final String DIALOG_WINDOW_IS_EXIT_TITLE = "Confirm Exit";
    public static final String DIALOG_WINDOW_IS_EXIT_QUESTION = "Exit: " + APP_TITLE + "?";
    public static final String DIALOG_WINDOW_OPEN_WINDOWS_REGISTER_FILE = "Open windows register file";
    public static final String DIALOG_WINDOW_SAVE_NEW_WINDOWS_REGISTER_FILE = "Save new windows register file";
    
    // labels
    public static final String LABEL_NAME_FILE_PATH = "PuTTY theme file path (UCS-2 LE BOM - exported by regedit windows tool):";
    public static final String LABEL_WINDOW_OPEN_FILE = "Open file";
    public static final String LABEL_PUTTY_SESSIONS_SHORT = "PuTTY Sessions";
    
    // validator
    public static final String VAL_REQUIRED_FIELD = "The field is required.";
    
    // other
    public static final String APPEND_CONFIGURATION_ICON_ALT = "Append PuTTY configuration to below sessions";
    public static final String CHOOSE_FILE_ICON_ALT = "Open file";
    public static final String LOADER_ICON_ALT = "Processing";
    
    // app
    public static final String[] PUTTY_SESSION_COLUMN_NAME = {"Append", "Name", "Type", "Value", "File Value", "Comment"};
    
    // help
    public static final String HELP_DESCRIPTION = "<html><b><u>PuTTY configuration</u></b><br><br>Apply new PuTTY theme to current sessions. You can generate windows registry file with changes.<br />Source of below sessions is: " + AppConf.REGISTRY_KEY + "<br /></html>";
    
}
