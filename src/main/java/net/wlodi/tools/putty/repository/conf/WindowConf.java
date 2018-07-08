package net.wlodi.tools.putty.repository.conf;

public enum WindowConf {
    
    MAIN_WINDOW( AppLocale.MAIN_WINDOW_TITLE ,
            UIConf.MAIN_WINDOW_WIDTH ,
            UIConf.MAIN_WINDOW_HEIGHT ,
            UIConf.MAIN_WINDOW_RESIZABLE ,
            false ),
    
    DIALOG( AppLocale.MAIN_WINDOW_DIALOG ,
            UIConf.APP_WINDOW_DIALOG_WIDTH ,
            UIConf.APP_WINDOW_DIALOG_HEIGHT ,
            UIConf.APP_WINDOW_DIALOG_RESIZABLE ,
            false );
    
    private String title;
    private int width;
    private int height;
    private boolean resizable;
    private boolean setMinimumSize;

    private WindowConf( String title , int width , int height , boolean resizable, boolean setMinimumSize ) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.resizable = resizable;
        this.setMinimumSize = setMinimumSize;
    }
    
    public String title() {
        return title;
    }
    
    public int width() {
        return width;
    }
    
    public int height() {
        return height;
    }
    
    public boolean resizable() {
        return resizable;
    }

    
    public boolean isSetMinimumSize( ) {
        return setMinimumSize;
    }
}
