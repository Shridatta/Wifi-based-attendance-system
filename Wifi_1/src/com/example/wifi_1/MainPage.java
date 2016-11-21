package com.example.wifi_1;

import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.inputmethodservice.Keyboard.Key;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainPage extends Activity implements OnClickListener{
	
	public static final String KEY_LIST="list";	
	   
   WifiManager mainWifiObj;
   WifiScanReceiver wifiReciever;
   ListView list;
   String wifis[],device[]={"74e543e3b007"};
   int count;
   TextView pcount;
 
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.main_page);
      list = (ListView)findViewById(R.id.listView1);
      final Button bt=(Button)findViewById(R.id.bt_scan);
 		Button bt1=(Button)findViewById(R.id.bt_other);
 		Button tt=(Button)findViewById(R.id.bt_timetable);
 		
 		pcount=(TextView)findViewById(R.id.pcount_id); 
 		bt1.setOnClickListener(this);
	     final TextView tv =(TextView)findViewById(R.id.tv_sucess);
 		mainWifiObj = (WifiManager) getSystemService(Context.WIFI_SERVICE);
 		wifiReciever = new WifiScanReceiver();
 		tt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				Intent Intent=new Intent(MainPage.this,TimeTable1.class);
				startActivity(Intent);
			}
		});
      
      bt.setOnClickListener(new View.OnClickListener() {
	
    	  @Override
    	  public void onClick(View arg0) {
    		  //	 TODO Auto-generated method stub
    		  mainWifiObj.startScan();
    		//  bt1.setVisibility(View.VISIBLE);
    		
    		  working();
    		 for(int i=1;i<count;i++)
    			  device[0]+=device[i];
    		 pcount.setText("Present Count"+count+device[0]);
    		
    		//	tv.setText("hhhhhhhhhhhh");
    			
    		 
    	  }
      });
      
      
      
   }


   protected void onPause() {
      unregisterReceiver(wifiReciever);
      super.onPause();
   }

   protected void onResume() {
      registerReceiver(wifiReciever, new IntentFilter(
      WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
      super.onResume();
   }

   class WifiScanReceiver extends BroadcastReceiver {
      @SuppressLint("UseValueOf")
      public void onReceive(Context c, Intent intent) {
         List<ScanResult> wifiScanList = mainWifiObj.getScanResults();
         wifis = new String[wifiScanList.size()];
         device = new String[wifiScanList.size()];
         
         count=wifiScanList.size();
         ScanResult bestSignal = null;
         for (ScanResult result : wifiScanList) {
        	 if (bestSignal == null ||
        	 WifiManager.compareSignalLevel(
        	 bestSignal.level,result.level) < 0)
        	 bestSignal = result;
        	 }
         String connSummary = bestSignal.BSSID;
        		 Toast.makeText(MainPage.this,
        		 connSummary, Toast.LENGTH_LONG).show();
         for(int i = 0; i < wifiScanList.size(); i++){
            wifis[i] = ((wifiScanList.get(i)).toString());
        device[i]=((wifiScanList.get(i)).BSSID);
 
         }

         list.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
         android.R.layout.simple_list_item_1,wifis));
         
      }
   }
   public void working() {
		// TODO Auto-generated method stub
		 Dialog d = new Dialog(this);
		 d.setTitle("hech yea!");
		 TextView tv = new TextView(this);
		 tv.setText("Success");
		 d.setContentView(tv);
		 d.show();	
	}

@Override
public void onClick(View arg0) {
	// TODO Auto-generated method stub
	switch(arg0.getId())
	{
		case R.id.bt_other:
		/*Intent i=new Intent();
			startActivity(i);
			setContentView(R.layout.other);*/
			Intent Intent=new Intent(MainPage.this,DataSend.class);
			Intent.putExtra("KEY_LIST", device);
			Intent.putExtra("KEY_LISTNUMBER", count);
			startActivity(Intent);
			/*Intent Intent1=new Intent(MainPage.this,AdminPanel.class);
			Intent1.putExtra("KEY_LIST", device);
			startActivity(Intent1);*/
            	//setContentView(R.layout.adminhome);
		break;
	}
}
}