package com.example.widgetsample00;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.net.TrafficStats;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class SampleActivity  extends Activity implements OnClickListener {
    /** Called when the activity is first created. */

	TextView textView1;
	TextView textView2;
	TextView textView3;
	TextView textView4;
	
	private CustomData itemData;
	private Drawable icon = null;
 
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

        PackageManager pm = this.getPackageManager();
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        //呼び出したいActivityのカテゴリを指定する
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        //カテゴリとアクションに一致するアクティビティの情報を取得する
        final List<ResolveInfo> appInfoList = pm.queryIntentActivities(intent, 0);

        List<CustomData> objects = new ArrayList<CustomData>();
        
        //以下、取得したアクティビティ情報からアプリケーションリストの作成
        for(ResolveInfo ri : appInfoList){
            itemData = new CustomData();
 
            if(ri.loadLabel(pm).toString()!=null){
                itemData.setTextData(ri.loadLabel(pm).toString());
            }else{
                itemData.setTextData("NoName");
            }
 
            try {
                icon = pm.getApplicationIcon(ri.activityInfo.packageName);
            } catch (NameNotFoundException e) {
                e.printStackTrace();
            }
            itemData.setImageData(icon);
 
            objects.add(itemData);

            CustomAdapter customAdapater = new CustomAdapter(this, 0, objects);
            ListView listView = (ListView)findViewById(R.id.list);
            customAdapater = new CustomAdapter(this, 0, objects);
            listView.setAdapter(customAdapater);
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
    
    public class CustomData {
        private Drawable imageData_;
        private String textData_;
     
        public void setImageData(Drawable image) {
            imageData_ = image;
        }
     
        public Drawable getImageData() {
            return imageData_;
        }
     
        public void setTextData(String text) {
            textData_ = text;
        }
     
        public String getTextData() {
            return textData_;
        }
    }
    
    public class CustomAdapter extends ArrayAdapter<CustomData> {
    	 private LayoutInflater layoutInflater_;
    	 
    	 public CustomAdapter(Context context, int textViewResourceId, List<CustomData> objects) {
	    	 super(context, textViewResourceId, objects);
	    	 layoutInflater_ = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	 }
    	 
    	 @Override
    	 public View getView(int position, View convertView, ViewGroup parent) {
	    	 // 特定の行(position)のデータを得る
	    	 CustomData item = (CustomData)getItem(position);
	    	 
	    	 // convertViewは使い回しされている可能性があるのでnullの時だけ新しく作る
	    	 if (null == convertView) {
	    		 convertView = layoutInflater_.inflate(R.layout.sample_activity, null);
	    	 }
	    	 
	    	 // CustomDataのデータをViewの各Widgetにセットする
	    	 ImageView imageView;
	    	 imageView = (ImageView)convertView.findViewById(R.id.image);
	    	 imageView.setImageDrawable(item.getImageData());
	    	 
	    	 TextView textView;
	    	 textView = (TextView)convertView.findViewById(R.id.text);
	    	 textView.setText(item.getTextData());
	    	 
	    	 return convertView;
    	 }
	}
}
