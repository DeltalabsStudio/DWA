package id.delta.whatsapp.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import id.delta.whatsapp.activities.ProblemActivity;

public class ExceptionHandler implements Thread.UncaughtExceptionHandler {
	private final Context myContext;
	private final String LINE_SEPARATOR = "\n";

	public ExceptionHandler(Context context) {
		myContext = context;
	}

	public void uncaughtException(Thread thread, Throwable exception) {
		try{
			StringWriter stackTrace = new StringWriter();
			exception.printStackTrace(new PrintWriter(stackTrace));
			StringBuilder errorReport = new StringBuilder();
			errorReport.append("==== CAUSE OF ERROR ====");
			errorReport.append(LINE_SEPARATOR);
			errorReport.append(stackTrace.toString());
			errorReport.append(LINE_SEPARATOR);
			errorReport.append("==== DEVICE INFORMATION ====");
			errorReport.append(LINE_SEPARATOR);
			errorReport.append("Brand : ");
			errorReport.append(Build.BRAND);
			errorReport.append(LINE_SEPARATOR);
			errorReport.append("Device : ");
			errorReport.append(Build.DEVICE);
			errorReport.append(LINE_SEPARATOR);
			errorReport.append("Model : ");
			errorReport.append(Build.MODEL);
			errorReport.append(LINE_SEPARATOR);
			errorReport.append("Id : ");
			errorReport.append(Build.ID);
			errorReport.append(LINE_SEPARATOR);
			errorReport.append("Product : ");
			errorReport.append(Build.PRODUCT);
			errorReport.append(LINE_SEPARATOR);
			errorReport.append("SDK : ");
			errorReport.append(Build.VERSION.SDK);
			errorReport.append(LINE_SEPARATOR);
			errorReport.append("Release : ");
			errorReport.append(Build.VERSION.RELEASE);
			errorReport.append(LINE_SEPARATOR);
			errorReport.append("Incremental : ");
			errorReport.append(Build.VERSION.INCREMENTAL);
			errorReport.append(LINE_SEPARATOR);
            Prefs.putString(Keys.KEY_PROBLEM, errorReport.toString());
			Intent i = new Intent(myContext, ProblemActivity.class);
			i.putExtra(Keys.KEY_PROBLEM, errorReport.toString());
			myContext.startActivity(i);
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(2);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	public static void setCustomException(Context context){
		if(Prefs.getBoolean(Keys.KEY_REPORT_PROBLEM, true)){
			Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(context));
		}
	}
}