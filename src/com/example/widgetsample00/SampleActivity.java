package com.example.widgetsample00;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SampleActivity  extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
 
	EditText text_edit;
	Button save_btn;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_activity);
 
        text_edit = (EditText)findViewById(R.id.text_edit);
        save_btn = (Button) findViewById(R.id.save_btn);
 
        save_btn.setOnClickListener(this);
    }
 
    @Override
  	public void onClick(View v) {
	    // TODO 自動生成されたメソッド・スタブ
	 
	    Intent widgetUpdate = new Intent("sample_widget_filter");
	    Bundle bundle = new Bundle();
	    bundle.putString("text", text_edit.getText().toString());
	    widgetUpdate.putExtras(bundle);
	    sendBroadcast(widgetUpdate);
	 
	    finish();
	}
}
