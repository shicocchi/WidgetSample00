package com.example.widgetsample00;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

public class HelloAndroidWidgetProvider extends AppWidgetProvider {

	@Override
	public void onEnabled(Context context) {
		Log.v("HelloAndroidWidgetProvider", "onEnabled");
		super.onEnabled(context);
	}
	
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		Log.v("HelloAndroidWidgetProvider", "onUpdate");
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		
		Intent widgetIntent = new Intent(context, WidgetService.class);
		context.startService(widgetIntent);
		
		//ClickŽž‚ÌActivity
	    Intent inact = new Intent(context, SampleActivity.class);
	    PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, inact, PendingIntent.FLAG_UPDATE_CURRENT);
	    RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.main);
	    views.setOnClickPendingIntent(R.id.main_ll, pendingIntent);
	    ComponentName widget = new ComponentName(context, HelloAndroidWidgetProvider.class);
	    appWidgetManager.updateAppWidget(widget, views);
	}
	
	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		Log.v("HelloAndroidWidgetProvider", "onDeleted");
		super.onDeleted(context, appWidgetIds);
	}
	
	@Override
	public void onDisabled(Context context) {
		Log.v("HelloAndroidWidgetProvider", "onDisabled");
		super.onDisabled(context);
		Intent intent = new Intent(context, WidgetService.class);
		context.stopService(intent);
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.v("HelloAndroidWidgetProvider", "onReceive");
		super.onReceive(context, intent);
	}
	
	public static class WidgetService extends Service {
        @Override
        public void onStart(Intent intent, int si) {
    		Log.v("WidgetService", "onStart");
    		IntentFilter intentFilter = new IntentFilter();
    		intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
            registerReceiver(batteryReceiver, intentFilter);

            IntentFilter filter = new IntentFilter();
            filter.addAction("sample_widget_filter");
            registerReceiver(b_Receiver, filter);
        }
        
        @Override
        public IBinder onBind(Intent iintentn) {
    		Log.v("WidgetService", "onBind");
            return null;
        }
    }

	private static BroadcastReceiver batteryReceiver = new BroadcastReceiver() {
		int scale = 100;
	    int level = 0;
	    
	    @Override
	    public void onReceive(Context context, Intent intent) {
			Log.v("BroadcastReceiver", "onReceive");
	    	String action = intent.getAction();
	        if (action.equals(Intent.ACTION_BATTERY_CHANGED)) {
	            level = intent.getIntExtra("level", 0);
	            scale = intent.getIntExtra("scale", 0);
	        }
	        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
	        ComponentName componentName = new ComponentName(context, HelloAndroidWidgetProvider.class);
	        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.main);
	        remoteViews.setTextViewText(R.id.textView, "" + (int) (level * 100 / scale));
	        appWidgetManager.updateAppWidget(componentName, remoteViews);
	    }
	};
	

	private static BroadcastReceiver b_Receiver = new BroadcastReceiver() {
	 
	    @Override
	    public void onReceive(Context context, Intent intent) {
	    	String ac = intent.getAction();
	    	Log.v(getClass().getSimpleName()  , "Action Rrn = " + ac);
	 
	    	if (ac.equals("sample_widget_filter")) {
	    		Bundle bundle = intent.getExtras();
	    		if (bundle == null) {
	    			Log.v(getClass().getSimpleName()  , "Bundle = null");
	    		} else {
	    			String msg = bundle.getString("text");
	    			setTextView(context, msg);
	    		}
	    	}
	    }
	 
	    void setTextView(Context context, String msg_st) {
	    	RemoteViews remoteViews;
	    	remoteViews = new RemoteViews(context.getPackageName(), R.layout.main);
	 
//	    	remoteViews.setTextViewText(R.id.text_tv, msg_st);
	 
	    	ComponentName thisWidget = new ComponentName(context, HelloAndroidWidgetProvider.class);
	    	AppWidgetManager manager = AppWidgetManager.getInstance(context);
	    	manager.updateAppWidget(thisWidget, remoteViews);
	    }
	};
}
