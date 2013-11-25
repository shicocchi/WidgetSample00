package com.example.widgetsample00;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Intent;
import android.net.TrafficStats;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SampleActivity  extends Activity implements OnClickListener {
    /** Called when the activity is first created. */

	TextView textView1;
	TextView textView2;
	TextView textView3;
	TextView textView4;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_activity);

        textView1 = (TextView)findViewById(R.id.mobileRxBytes);
        textView2 = (TextView)findViewById(R.id.mobileTxBytes);
        textView3 = (TextView)findViewById(R.id.totalRxBytes);
        textView4 = (TextView)findViewById(R.id.totalTxBytes);
 
        long mobileRxBytes = TrafficStats.getMobileRxBytes();
        long mobileTxBytes = TrafficStats.getMobileTxBytes();
        long totalRxBytes = TrafficStats.getTotalRxBytes();
        long totalTxBytes = TrafficStats.getTotalTxBytes();

        textView1.setText(String.valueOf(mobileRxBytes));
        textView2.setText(String.valueOf(mobileTxBytes));
        textView3.setText(String.valueOf(totalRxBytes));
        textView4.setText(String.valueOf(totalTxBytes));

        ActivityManager am = (ActivityManager)getSystemService(ACTIVITY_SERVICE);
        for (RunningAppProcessInfo info : am.getRunningAppProcesses()) {
          long uidRxBytes = TrafficStats.getUidRxBytes(info.uid);
          long uidTxBytes = TrafficStats.getUidTxBytes(info.uid);
          Log.v(getClass().getSimpleName(), String.valueOf(uidRxBytes));
          Log.v(getClass().getSimpleName(), String.valueOf(uidTxBytes));
        }

        
//        save_btn.setOnClickListener(this);
    }
 
    @Override
  	public void onClick(View v) {
	    // TODO 自動生成されたメソッド・スタブ
	 
//	    Intent widgetUpdate = new Intent("sample_widget_filter");
//	    Bundle bundle = new Bundle();
//	    bundle.putString("text", text_edit.getText().toString());
//	    widgetUpdate.putExtras(bundle);
//	    sendBroadcast(widgetUpdate);
//	 
	    finish();
	}
}
