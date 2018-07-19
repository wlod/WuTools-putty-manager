package datacite.oai.provider.util;


/*******************************************************************************
 * Copyright (c) 2011 DataCite
 *
 * All rights reserved. This program and the accompanying materials are made available under the terms of the Apache License, Version 2.0 which
 * accompanies this distribution, and is available at http://www.apache.org/licenses/LICENSE-2.0
 *
 *******************************************************************************/

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;


/**
 * Encapsulates utils for BOM removal. Currently only UTF-8/16/32 supported.
 * 
 * @author PaluchM
 * 
 * Modified by @author Grzegorz Wlodek:
 * - Removed Apache commons to create array.
 * - Change encoding string version to charset. 
 * - Removed UTF32* support.
 * - Performance for removeBOM method.
 *
 */
public class BOMUtil {

    /** UTF-8 Byte Order Mark **/
    public static final byte[] UTF8_BOM = { (byte) 0xEF , (byte) 0xBB , (byte) 0xBF };
    /** UTF-16 Big Endian Byte Order Mark **/
    public static final byte[] UTF16BE_BOM = { (byte) 0xFE , (byte) 0xFF };
    /** UTF-16 Little Endian Byte Order Mark **/
    public static final byte[] UTF16LE_BOM = { (byte) 0xFF , (byte) 0xFE };

    /** Mapping of encoding string (alphanumeric only) to BOM **/
    private static final HashMap<Charset , byte[]> BOM_MAP = new HashMap<Charset , byte[]>();
    static {
        BOM_MAP.put( StandardCharsets.UTF_8, UTF8_BOM );
        BOM_MAP.put( StandardCharsets.UTF_16LE, UTF16LE_BOM );
        BOM_MAP.put( StandardCharsets.UTF_16BE, UTF16BE_BOM );
    }

    /** Hidden constructor **/
    private BOMUtil() {
    }

    /**
     * Remove a Byte Order Mark from the beginning of a byte[].
     * 
     * @param array
     *            The byte[] to remove a BOM from.
     * @param encoding
     *            The character encoding to remove the BOM for.
     * @return The original byte[] without BOM.
     */
    public static byte[] removeBOM( byte[] array , Charset encoding ) throws UnsupportedEncodingException {
        if (encoding == null) {
            throw new UnsupportedEncodingException( "Unsupported encoding: " + encoding );
        }
        else {
            byte[] bom = BOMUtil.BOM_MAP.get( encoding );
            if (bom == null) {
                throw new UnsupportedEncodingException( "Unsupported encoding: " + encoding );
            }
            else {
                return BOMUtil.removeBOM( array, bom );
            }
        }
    }

    /**
     * Remove a Byte Order Mark from the beginning of a byte[].
     * 
     * @param array
     *            The byte[] to remove a BOM from.
     * @param BOM
     *            The byte[] BOM to remove.
     * @return The original byte[] without BOM.
     */
    public static byte[] removeBOM( byte[] array , byte[] BOM ) {
        boolean hasBOM = true;
        for ( int i = 0; (i < BOM.length) && hasBOM; i++ ) {
            if ((byte) array[i] != (byte) BOM[i]) {
                hasBOM = false;
                break;
            }
        }
        if (hasBOM) {
            return Arrays.copyOfRange(array, BOM.length, array.length);
        }
        else {
            return array;
        }
    }
}