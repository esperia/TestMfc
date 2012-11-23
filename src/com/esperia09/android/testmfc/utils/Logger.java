package com.esperia09.android.testmfc.utils;

import android.util.Log;

import com.esperia09.android.testmfc.BuildConfig;

public class Logger {
	public static final boolean debug = BuildConfig.DEBUG;
	private static final int TRACE_CALLER_COUNT = 2;
	private static Class<?>[] mIgnoreList = null;
	
	private static void loadIgnoreList() {
		if (debug) {
			mIgnoreList = new Class<?>[] {
					
			};
		}
	}

	public static void d() {
		if (debug) {
			StackTraceElement ste = getTargetEl();
			if (!isMatchIgnoreList(ste.getClass())) {
				Log.d(getClassName(ste), getFunctionName(ste));
			}
		}
	}

	public static void d(String msg) {
		if (debug) {
			msg = (msg == null) ? "(null)" : msg;

			StackTraceElement ste = getTargetEl();
			if (!isMatchIgnoreList(ste.getClass())) {
				Log.d(getClassName(ste), getFunctionName(ste) + ", " + msg);
			}
		}
	}

	public static void w() {
		if (debug) {
			StackTraceElement ste = getTargetEl();
			if (!isMatchIgnoreList(ste.getClass())) {
				Log.w(getClassName(ste), getFunctionName(ste));
			}
		}
	}

	public static void w(String msg) {
		if (debug) {
			msg = (msg == null) ? "(null)" : msg;

			StackTraceElement ste = getTargetEl();
			if (!isMatchIgnoreList(ste.getClass())) {
				Log.w(getClassName(ste), getFunctionName(ste) + ", " + msg);
			}
		}
	}

	public static void w(String msg, Throwable e) {
		if (debug) {
			msg = (msg == null) ? "(null)" : msg;

			StackTraceElement ste = getTargetEl();
			if (!isMatchIgnoreList(ste.getClass())) {
				Log.w(getClassName(ste), getFunctionName(ste) + ", " + msg, e);
			}
		}
	}

	public static void e(String msg) {
		if (debug) {
			msg = (msg == null) ? "(null)" : msg;

			StackTraceElement ste = getTargetEl();
			if (!isMatchIgnoreList(ste.getClass())) {
				Log.e(getClassName(ste), getFunctionName(ste) + ", " + msg);
			}
		}
	}

	public static void e(String msg, Throwable e) {
		if (debug) {
			msg = (msg == null) ? "(null)" : msg;

			StackTraceElement ste = getTargetEl();
			if (!isMatchIgnoreList(ste.getClass())) {
				Log.e(getClassName(ste), getFunctionName(ste) + ", " + msg, e);
			}
		}
	}

	private static boolean isMatchIgnoreList(Class<?> klass) {
		loadIgnoreList();
		if (mIgnoreList != null && mIgnoreList.length != 0) {
			for (Class<?> i : mIgnoreList) {
				if (i == klass) {
					return true;
				}
			}
		}

		return false;
	}

	private static StackTraceElement getTargetEl() {
		try {
			return new Throwable().getStackTrace()[TRACE_CALLER_COUNT];
		} catch (Exception e) {
		}

		return null;
	}

	private static String getClassName(StackTraceElement ste) {
		if (ste != null) {
			return ste.getClassName().replaceFirst("^.*\\.", "");
		}
		
		return "(null)";
	}

	private static String getFunctionName(StackTraceElement ste) {
		try {
			return ste.getMethodName();
		} catch (Exception e) {}
		
		return "(null)";
	}
}