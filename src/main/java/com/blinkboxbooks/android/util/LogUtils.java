/*******************************************************************************
 * Copyright (c) 2013 blinkbox Entertainment Limited. All rights reserved.
 *******************************************************************************/

package com.blinkboxbooks.android.util;

import android.os.Build;
import android.util.Log;

import com.blinkboxbooks.android.api.net.BBBHttpRequestMethod;
import com.blinkboxbooks.android.api.net.BBBRequest;
import com.blinkboxbooks.android.api.net.BBBResponse;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;

/** Helper class for logging data. Allows logging to be enabled/disabled easily. */
public class LogUtils {

	private static boolean sLoggingEnabled = false;

	private static boolean sRunningOnDevice = true;

	static {
		sRunningOnDevice = Build.BRAND != null;
	}

	/**
	 * enables/disables logging
	 * 
	 * @param enabled Set to true to enable logging or false to disable
	 */
	public static void setLoggingEnabled(boolean enabled) {
		sLoggingEnabled = enabled;
	}

	/**
	 * Log a message at the DEBUG level if logging is enabled
	 * 
	 * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
	 * @param message The message to log
	 */
	public static void d(String tag, String message) {

		if (sLoggingEnabled) {

			if (sRunningOnDevice) {
				Log.d(tag, message);
			} else {
				System.out.println(tag + " - " + message);
			}
		}
	}

	/**
	 * Log a message at the DEBUG level if logging is enabled
	 * 
	 * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
	 * @param message The message to log
	 * @param throwable The exception to log
	 */
	public static void d(String tag, String message, Throwable throwable) {

		if (sLoggingEnabled) {

			if (sRunningOnDevice) {
				Log.d(tag, message, throwable);
			} else {
				System.out.println(tag + " - " + message);
			}

			throwable.printStackTrace();
		}
	}

	/**
	 * Log a message at the VERBOSE level if logging is enabled
	 * 
	 * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
	 * @param message The message to log
	 */
	public static void v(String tag, String message) {

		if (sLoggingEnabled) {

			if (sRunningOnDevice) {
				Log.v(tag, message);
			} else {
				System.out.println(tag + " - " + message);
			}
		}
	}

	/**
	 * Log a message at the VERBOSE level if logging is enabled
	 * 
	 * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
	 * @param message The message to log
	 * @param throwable The exception to log
	 */
	public static void v(String tag, String message, Throwable throwable) {

		if (sLoggingEnabled) {

			if (sRunningOnDevice) {
				Log.v(tag, message, throwable);
			} else {
				System.out.println(tag + " - " + message);
			}

			throwable.printStackTrace();
		}
	}

	/**
	 * Log a message at the INFO level if logging is enabled
	 * 
	 * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
	 * @param message The message to log
	 */
	public static void i(String tag, String message) {

		if (sLoggingEnabled) {

			if (sRunningOnDevice) {
				Log.i(tag, message);
			} else {
				System.out.println(tag + " - " + message);
			}
		}
	}

	/**
	 * Log a message at the INFO level if logging is enabled
	 * 
	 * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
	 * @param message The message to log
	 * @param throwable The exception to log
	 */
	public static void i(String tag, String message, Throwable throwable) {

		if (sLoggingEnabled) {

			if (sRunningOnDevice) {
				Log.i(tag, message, throwable);
			} else {
				System.out.println(tag + " - " + message);
			}

			throwable.printStackTrace();
		}
	}

	/**
	 * Log a message at the WARNING level if logging is enabled
	 * 
	 * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
	 * @param message The message to log
	 */
	public static void w(String tag, String message) {

		if (sLoggingEnabled) {

			if (sRunningOnDevice) {
				Log.w(tag, message);
			} else {
				System.out.println(tag + " - " + message);
			}
		}
	}

	/**
	 * Log a message at the WARNING level if logging is enabled
	 * 
	 * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
	 * @param message The message to log
	 * @param throwable The exception to log
	 */
	public static void w(String tag, String message, Throwable throwable) {

		if (sLoggingEnabled) {

			if (sRunningOnDevice) {
				Log.w(tag, message, throwable);
			} else {
				System.out.println(tag + " - " + message);
			}

			throwable.printStackTrace();
		}
	}

	/**
	 * Log a message at the ERROR level if logging is enabled
	 * 
	 * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
	 * @param message The message to log
	 */
	public static void e(String tag, String message) {

		if (sLoggingEnabled) {

			if (sRunningOnDevice) {
				Log.e(tag, message);
			} else {
				System.out.println(tag + " - " + message);
			}

		}
	}

	/**
	 * Log a message at the ERROR level if logging is enabled
	 * 
	 * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
	 * @param message The message to log
	 * @param throwable The exception to log
	 */
	public static void e(String tag, String message, Throwable throwable) {

		if (sLoggingEnabled) {

			if (sRunningOnDevice) {
				Log.e(tag, message, throwable);
			} else {
				System.out.println(tag + " - " + message);
			}

			throwable.printStackTrace();
		}
	}

	/**
	 * Log a single string at ERROR level. The tag will be constuctor from the caller class, method and line number.
	 * 
	 * @param message
	 */
	public static void log(String message) {
		if (!sLoggingEnabled) {
			return;
		}
		StackTraceElement[] stack = new Throwable().getStackTrace();
		String tag = stack[1].getFileName() + ":" + stack[1].getLineNumber()
				+ " " + stack[1].getMethodName() + "()";
		Log.e(tag, message);
	}

	/**
	 * Log the stack trace of the caller to at ERROR level.
	 */
	public static void stack() {
		if (!sLoggingEnabled) {
			return;
		}
		Throwable throwable = new Throwable();
		StackTraceElement[] stack = throwable.getStackTrace();
		String tag = stack[1].getFileName() + ":" + stack[1].getLineNumber()
				+ " " + stack[1].getMethodName() + "()";
		Log.e(tag, getStackTraceString(throwable));
	}

	public static String getStackTraceString(Throwable tr) {
		if (tr == null) {
			return "";
		}
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		tr.printStackTrace(pw);
		return sw.toString();
	}

	/**
	 * Helper function to convert a java object (including all its fields) to a string.
	 * 
	 * @param object
	 * @return A string containing all the fields of the input object
	 */
	public static String createString(Object object) {
		if (object == null) {
			return "null";
		}

		StringBuilder result = new StringBuilder();
		String newLine = "\n";

		result.append(object.getClass().getName());
		result.append(" Object {");
		result.append(newLine);

		// determine fields declared in this class only (no fields of superclass)
		Field[] fields = object.getClass().getDeclaredFields();

		// print field names paired with their values
		for (Field field : fields) {
			result.append("  ");
			try {
				result.append(field.getName());
				result.append(": ");
				// requires access to private field:
				field.setAccessible(true);
				result.append(field.get(object));
			} catch (IllegalAccessException ex) {
				System.out.println(ex);
			}
			result.append(newLine);
		}
		result.append("}");

		return result.toString();
	}
	
	public static final boolean CURL_LOGS = true;

	public static void curlLog(BBBRequest request, BBBResponse response) {
		if (CURL_LOGS && sLoggingEnabled) {
			StringBuffer buffer = new StringBuffer("curl '" + request.getUrl() + "' ");
			if (request.getHeaders() != null) {
				for (String header : request.getHeaders().keySet()) {
					String value = request.getHeaders().get(header);
					buffer.append("-H '" + header + ":" + value + "' ");
				}
			}
			if (request.getRequestMethod() == BBBHttpRequestMethod.PUT) {
				buffer.append("-X PUT ");
			} else if (request.getRequestMethod() == BBBHttpRequestMethod.POST) {
				buffer.append("-X POST ");
			} else if (request.getRequestMethod() == BBBHttpRequestMethod.DELETE) {
				buffer.append("-X DELETE ");
			}
			byte [] post = request.getPostData();
			if (post != null) {
				buffer.append("-d '");
				buffer.append(new String(post));
				buffer.append("' ");
			}
			v("CURL", buffer.toString());
		}
	}
}
