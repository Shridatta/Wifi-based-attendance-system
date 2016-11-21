package com.example.wifi_1;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class TimeTable extends  Activity {
	HttpClient httpclient;
	HttpPost httppost;
	HttpResponse response;
	String result=null;
	InputStream is=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	setContentView(R.layout.timetable);
	if (android.os.Build.VERSION.SDK_INT > 9) {
	    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	    StrictMode.setThreadPolicy(policy);
	}
	
	new Thread(new Runnable() {
		public void run() {
			Log.e("tag", "login called");

			getdata();
		}
	}).start();

	
	
	}
	void getdata()
	{
		try{
			httpclient=new DefaultHttpClient();
			
			httppost=new HttpPost("http://10.0.2.2/users.php");
			response=httpclient.execute(httppost);
			HttpEntity entity=response.getEntity();
			is=entity.getContent();
			Log.e("log_tag","connection success");
			Toast.makeText(getApplicationContext(), "connection successfullnnnnnnnnnnnnnnn", Toast.LENGTH_SHORT).show();
		}
		catch(Exception e){
			Log.e("log_tag","error in connection"+e.toString());
			Toast.makeText(getApplicationContext(), "connection failed"+e.toString(), Toast.LENGTH_SHORT).show();
		}
		try{
			BufferedReader reader=new BufferedReader(new InputStreamReader(is));
			StringBuilder sb=new StringBuilder();
			String line=null;
			while((line=reader.readLine())!=null)
			{
				sb.append(line+"\n");
			}
			is.close();
			result=sb.toString();
		}
		catch(Exception e){
			Log.e("log_tag", "error result convereting"+e.toString());
			Toast.makeText(getApplicationContext(), "input reader failed", Toast.LENGTH_SHORT).show();
			
		}
		try
		{
			JSONArray jarray=new JSONArray(result);
			TableLayout tv=(TableLayout)findViewById(R.id.table);
			tv.removeAllViewsInLayout();
			int flag=1;
			for(int i=0;i<jarray.length()-1;i++)
			{
				TableRow tr=new TableRow(TimeTable.this);
				tr.setLayoutParams(new LayoutParams(android.widget.TableLayout.LayoutParams.FILL_PARENT,android.widget.TableRow.LayoutParams.WRAP_CONTENT));
				if(flag==1)
				{
					TextView b6=new TextView(TimeTable.this);
					b6.setText("1");
					b6.setTextSize(15);
					tr.addView(b6);
					
					TextView b19=new TextView(TimeTable.this);
					b19.setPadding(10, 0, 0, 0);
					b19.setTextSize(15);
					b19.setText("2");
					tr.addView(b19);
					
					TextView b29=new TextView(TimeTable.this);
					b29.setPadding(10, 0, 0, 0);
					b29.setTextSize(15);
					b29.setText("2");
					tr.addView(b29);
					tv.addView(tr);
					final View vline=new View(TimeTable.this);
					vline.setLayoutParams(new
							TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,2));
					vline.setBackgroundColor(Color.BLUE);
					tv.addView(vline);
					flag=0;
				}
				else{
					JSONObject json_data=jarray.getJSONObject(i);
					Log.e("log+tag", "id"+json_data.getInt("Id")+",Username: "+json_data.getString("username")+",No:"+json_data.getString("comment"));
				TextView b=new TextView(TimeTable.this);
				String stime=String.valueOf(json_data.getInt("id"));
					b.setText(stime);
					b.setTextSize(15);
					TextView b1=new TextView(TimeTable.this);
					b1.setPadding(10, 0, 0, 0);
					b1.setTextSize(15);
					String stime1=json_data.getString("username");
					b1.setText(stime1);
					b1.setTextColor(Color.BLACK);
					tr.addView(b1);
					
					TextView b2=new TextView(TimeTable.this);
					String stime2=json_data.getString("password");
					b2.setText(stime2);
					b2.setPadding(10, 0, 0, 0);
					b2.setTextColor(Color.BLACK);
					b2.setTextSize(15);
					tr.addView(b2);
					tv.addView(tr);
					final View vline1=new View(TimeTable.this);
					vline1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,1));
					vline1.setBackgroundColor(Color.WHITE);
					tv.addView(vline1);
					
				}
			}
		}
		catch(Exception e)
		{
			Log.e("log_tag", "error pasing data"+e.toString());
			Toast.makeText(getApplicationContext(), "jsonarray failed", Toast.LENGTH_SHORT).show();
		}
		
	}

}
