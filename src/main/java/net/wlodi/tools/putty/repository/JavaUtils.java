package net.wlodi.tools.putty.repository;


import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import datacite.oai.provider.util.BOMUtil;


public class JavaUtils {
    
    private static final Logger LOGGER = LoggerFactory.getLogger( JavaUtils.class );

    public static String replaceLast( String string , String from , String to ) {
        int lastIndex = string.lastIndexOf( from );
        if (lastIndex < 0)
            return string;
        String tail = string.substring( lastIndex ).replaceFirst( from, to );
        return string.substring( 0, lastIndex ) + tail;
    }

    public static String conertToUTF16LE( String utf8String ) {
        return new String( utf8String.getBytes( StandardCharsets.UTF_8 ), StandardCharsets.UTF_16LE );
    }

    public static String conertToUTF8( String utf16String ) {
        return new String( utf16String.getBytes( StandardCharsets.UTF_16LE ), StandardCharsets.UTF_8 );
    }

    public static String removeUTF16_LE_BOM( String stringWithBOM ) {
        try {
            byte[] bytesWithoutBOM = BOMUtil.removeBOM( stringWithBOM.getBytes( StandardCharsets.UTF_16LE ), StandardCharsets.UTF_16LE );
            return new String( bytesWithoutBOM, StandardCharsets.UTF_16LE );
        }
        catch ( UnsupportedEncodingException e ) {
            LOGGER.warn( e.getMessage(), e );
        }
        return stringWithBOM;
    }

}
