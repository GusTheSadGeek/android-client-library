/*******************************************************************************
 * Copyright (c) 2013 blinkbox Entertainment Limited. All rights reserved.
 *******************************************************************************/
package com.blinkboxbooks.android.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URLEncoder;

/** Helper class for common IO Operations */
public class IOUtils {

	private static final String TAG = IOUtils.class.getSimpleName();
	
	/**
	 * Reads the bytes from an InputStream and turns them into a String assuming UTF-8 encoding
	 * 
	 * @param inputStream The InputStream
	 * @return The string which was read from the InputStream or null if the was a problem reading the string
	 */
	public static String toString(InputStream inputStream) {
		return toString(inputStream, "UTF-8");
	}
		
	/**
	 * Reads the bytes from an InputStream and turns them into a String using the supplied encoding
	 * 
	 * @param inputStream The InputStream
	 * @param encoding The encoding of the bytes in the InputStream
	 * @return The string which was read from the InputStream or null if the was a problem reading the string
	 */
	public static String toString(InputStream inputStream, String encoding) {
		
		if(inputStream == null) {
			return null;
		}
		
		Writer writer = new StringWriter();
		char[] buffer = new char[1024];
		
		try {
			Reader reader = new BufferedReader(new InputStreamReader(inputStream, encoding));
			int n;
			
			while ((n = reader.read(buffer)) != -1) {
				writer.write(buffer, 0, n);
			}
			
			return writer.toString();
		} catch(IOException e) {
			LogUtils.v(TAG, e.getMessage(), e);
		} finally {		
			try { inputStream.close(); } catch(IOException e) {}
		}
		
		return null;
	}
	
	/**
	 * Encodes a string to be URL friendly e.g. swaps empty chars for '+'
	 * 
	 * @param str The string you want to be encoded
	 * @return The encoded string
	 */
	public static String urlEncode(String str) {
		
		try {
			String encoded = URLEncoder.encode(str, "UTF-8");
			return encoded;
		} catch (UnsupportedEncodingException e) {
			LogUtils.d(TAG, e.getMessage(), e);
		}
		
		return null;
	}
	
	/**
	 * Copies the contents of the file into the OutputStream
	 * 
	 * @param in the File to copy
	 * @param out the OutputStream to write to
	 * @return the number of bytes which were copied
	 * @throws IOException
	 */
    public static long copy(File in, OutputStream out) throws IOException {
        return copy(new FileInputStream(in), out);
    }

    /**
     * Copies the contents of the InputStream to the File
     * 
     * @param in the InputStream to copy from
     * @param out the File to write to
     * @return the number of bytes which were copied 
     * @throws IOException
     */
    public static long copy(InputStream in, File out) throws IOException {
        return copy(in, new FileOutputStream(out));
    }

    /*
     * copies the content of the inputsteam to the outputsteam 
     */
    private static long copy(InputStream input, OutputStream output) throws IOException {
        try {
            byte[] buffer = new byte[1024 * 4];
            long count = 0;
            int n;
            while (-1 != (n = input.read(buffer))) {
                output.write(buffer, 0, n);
                count += n;
            }
            output.flush();
            return count;
        } finally {
            try {
                input.close();
            } catch (IOException e) {
                LogUtils.i(TAG, "Failed to close InputStream", e);
            }
            try {
                output.close();
            } catch (IOException e) {
                LogUtils.i(TAG, "Failed to close OutputStream", e);
            }
        }
    }
}
