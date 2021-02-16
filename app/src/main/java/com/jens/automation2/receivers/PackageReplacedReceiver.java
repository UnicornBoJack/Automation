package com.jens.automation2.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.jens.automation2.AutomationService;
import com.jens.automation2.Miscellaneous;
import com.jens.automation2.R;
import com.jens.automation2.Settings;

public class PackageReplacedReceiver extends BroadcastReceiver
{	
	@Override
	public void onReceive(Context context, Intent intent)
	{
//		Toast.makeText(context, "package replaced", Toast.LENGTH_LONG).show();
//		int intentUid = intent.getExtras().getInt("android.intent.extra.UID");	// userid of the application that has just been updated
//		int myUid = android.os.Process.myUid();										// userid of this application
//
//		boolean replacing = intent.getBooleanExtra(Intent.EXTRA_REPLACING, false);
		
//		if(intentUid == myUid)
//		{
			Settings.readFromPersistentStorage(context);

			Miscellaneous.logEvent("i", context.getResources().getString(R.string.applicationHasBeenUpdated), context.getResources().getString(R.string.applicationHasBeenUpdated), 2);
			if(hasServiceBeenRunning() && Settings.startServiceAfterAppUpdate)
			{
				Miscellaneous.logEvent("i", "Service", context.getResources().getString(R.string.logStartingServiceAfterAppUpdate), 1);
				AutomationService.startAutomationService(context, true);
			}
			else
			{
				Miscellaneous.logEvent("i", "Service", context.getResources().getString(R.string.logNotStartingServiceAfterAppUpdate), 2);
			}
//		}
//		else
//			Miscellaneous.logEvent("i", "Service", "Some other app has been updated.", 5);
	}
	
	private static boolean hasServiceBeenRunning()
	{
		return Settings.hasServiceBeenRunning;
	}
	
	public static void setHasServiceBeenRunning(boolean state, Context context)
	{
	    Miscellaneous.logEvent("i", "State", "Writing stateFile to " + String.valueOf(state), 4);
	    Settings.readFromPersistentStorage(context);
//		Settings.initializeSettings(context, false);
		Settings.hasServiceBeenRunning = state;
		Settings.writeSettings(context);
	}

}
