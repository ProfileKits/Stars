package com.predictor.library.utils;

import android.content.Context;
import android.util.Log;

import com.predictor.library.BuildConfig;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 一个简单的日志工具封装，可简单自己定位TAG，TAG的格式为：类名[方法名， 调用行数]
 *
 */
public abstract class CNLogUtil {
	
	public static final String TAG = "GalaxyStudio";
	private static final String LOG_FILE = "GalaxyStudio.log";

	public static boolean publicOnOff = BuildConfig.DEBUG;

	public static boolean allowD = publicOnOff;
	public static boolean allowE = publicOnOff;
	public static boolean allowI = publicOnOff;
	public static boolean allowV = publicOnOff;
	public static boolean allowW = publicOnOff;
	public static boolean allowWtf = publicOnOff;


	private static Context mContext = null;

	public static void openLog(Context context) {
		mContext = context;
	}

	// d方法
	// -----------------------------------------------------------------------------------------------------------------
	public static void d(String content) {
		if (!allowD || CNValidatorUtil.isEmpty(content)) {
			return;
		}
		StackTraceElement caller = getCallerMethodName();
		String tag = generateTag(caller);
		Log.d(tag, content);
	}

	public static void d(String content, Throwable tr) {
		if (!allowD || CNValidatorUtil.isEmpty(content)) {
			return;
		}
		StackTraceElement caller = getCallerMethodName();
		String tag = generateTag(caller);
		Log.d(tag, content, tr);
	}

	// e方法
	// ------------------------------------------------------------------------------------------------------------------
	public static void e(String content) {
		if (!allowE || CNValidatorUtil.isEmpty(content)) {
			return;
		}
		StackTraceElement caller = getCallerMethodName();
		String tag = generateTag(caller);
		Log.e(tag, content);
	}

	public static void e(String content, Throwable tr) {
		if (!allowE || CNValidatorUtil.isEmpty(content)) {
			return;
		}
		StackTraceElement caller = getCallerMethodName();
		String tag = generateTag(caller);
		Log.e(tag, content, tr);
	}

	private static long beginTime =0;
	private static String beginText;
	private static long endTime = 0;

	// time方法 打印两个间隔的时间差
	// -----------------------------------------------------------------------------------------------------------------
	public static void setTimeBegin(String content) {
		beginTime = System.currentTimeMillis();
		beginText = content;
	}
	public static void setTimeEnd(String content) {
		String time;
		if(beginTime!=0){
			endTime = System.currentTimeMillis() - beginTime;
			time = "->vijoz打印两个事件的时间差值为："+ endTime +" 毫秒";
		}else{
			return;
		}

		if (!allowI || CNValidatorUtil.isEmpty(content+ time)) {
			return;
		}
		StackTraceElement caller = getCallerMethodName();
		String tag = generateTag(caller);
		Log.i(tag, beginText + "(到)" +content +"的时间" + time);
		beginTime = 0;
		endTime = 0;
	}



	// i方法
	// -----------------------------------------------------------------------------------------------------------------
	public static void i(String content) {
		if (!allowI || CNValidatorUtil.isEmpty(content)) {
			return;
		}
		StackTraceElement caller = getCallerMethodName();
		String tag = generateTag(caller);
		Log.i(tag, content);
	}

	public static void i(String content, Throwable tr) {
		if (!allowI || CNValidatorUtil.isEmpty(content)) {
			return;
		}
		StackTraceElement caller = getCallerMethodName();
		String tag = generateTag(caller);
		Log.i(tag, content, tr);
	}

	// v方法
	// -----------------------------------------------------------------------------------------------------------------
	public static void v(String content) {
		if (!allowV || CNValidatorUtil.isEmpty(content)) {
			return;
		}
		StackTraceElement caller = getCallerMethodName();
		String tag = generateTag(caller);
		Log.v(tag, content);
	}

	public static void v(String content, Throwable tr) {
		if (!allowV || CNValidatorUtil.isEmpty(content)) {
			return;
		}
		StackTraceElement caller = getCallerMethodName();
		String tag = generateTag(caller);
		Log.v(tag, content, tr);
	}

	// w方法
	// -----------------------------------------------------------------------------------------------------------------
	public static void w(String content) {
		if (!allowW || CNValidatorUtil.isEmpty(content)) {
			return;
		}
		StackTraceElement caller = getCallerMethodName();
		String tag = generateTag(caller);
		Log.w(tag, content);
	}

	public static void w(String content, Throwable tr) {
		if (!allowW || CNValidatorUtil.isEmpty(content)) {
			return;
		}
		StackTraceElement caller = getCallerMethodName();
		String tag = generateTag(caller);
		Log.w(tag, content, tr);
	}

	public static void w(Throwable tr) {
		if (!allowW) {
			return;
		}
		StackTraceElement caller = getCallerMethodName();
		String tag = generateTag(caller);
		Log.w(tag, tr);
	}

	// wtf方法，非常恐怖的错误，这种错误原则上不应该出现在系统中，哈哈
	// -----------------------------------------------------------------------------------------------------------------
	public static void wtf(String content) {
		if (!allowWtf || CNValidatorUtil.isEmpty(content)) {
			return;
		}
		StackTraceElement caller = getCallerMethodName();
		String tag = generateTag(caller);
		Log.wtf(tag, content);
	}

	public static void wtf(String content, Throwable tr) {
		if (!allowWtf || CNValidatorUtil.isEmpty(content)) {
			return;
		}
		StackTraceElement caller = getCallerMethodName();
		String tag = generateTag(caller);
		Log.wtf(tag, content, tr);
	}

	public static void wtf(Throwable tr) {
		if (!allowWtf) {
			return;
		}
		StackTraceElement caller = getCallerMethodName();
		String tag = generateTag(caller);
		Log.wtf(tag, tr);
	}

	// 跟踪到调用该日志的方法
	private static StackTraceElement getCallerMethodName() {
		StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
		return stacks[4];
	}

	// 规范TAG格式：类名[方法名， 调用行数]

	/**
	 * 生成Tag
	 *
	 * @param stackTraceElement
	 * @return
	 */
	public static String generateTag(StackTraceElement stackTraceElement) {
		String tag = "[" + TAG + "] %s[%s, %d]";
		String callerClazzName = stackTraceElement.getClassName();
		callerClazzName = callerClazzName.substring(callerClazzName
				.lastIndexOf(".") + 1);
		tag = String.format(tag, callerClazzName, stackTraceElement.getMethodName(),
				stackTraceElement.getLineNumber());
		return tag;
	}

	/**
	 * 写log到文件中
	 *
	 * @param log
	 * @return
	 */
	public static boolean writeToFile(String log) {

		log = log + "\n";

		boolean res = false;
		try {
			// Properties properties = new Properties();
			FileOutputStream fOut = mContext.openFileOutput(LOG_FILE,
					Context.MODE_APPEND);
			try {
				fOut.write(log.getBytes());
				res = true;
			} catch (IOException e) {
				Log.e(TAG, e.toString());
			}

		} catch (FileNotFoundException e) {
			Log.e(TAG, e.toString());
		}
		return res;
	}

	public static String makeLogTag(Class cls) {
		return "vijoz:" + cls.getSimpleName();
	}
}