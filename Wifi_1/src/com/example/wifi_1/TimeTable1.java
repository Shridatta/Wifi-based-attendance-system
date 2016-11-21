package com.example.wifi_1;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;



public class TimeTable1 extends Activity {

	HttpClient httpclient;
	HttpGet request;
	HttpResponse response;
	String url;

	/* Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timetable);

		// URL of PHP Script

		url = "http://10.0.2.2/users.php";

		// TextView to display result

		TextView result = (TextView) findViewById(R.id.tvResult);

		// Try to connect using Apache HttpClient Library
		JSONArray jArray = null;
		int len=0;
		try {
			httpclient = new DefaultHttpClient();
			request = new HttpGet(url);
			response = httpclient.execute(request);
			
			
			
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));
			String line = "";
			while ((line = rd.readLine()) != null) {

				// Appending result to textview
				result.append(line);
			}
			String ressString=result.getText().toString();
			jArray=new JSONArray(ressString);
			len=jArray.length();
			ressString="size"+len;
			JSONObject json_data = jArray.getJSONObject(1);
			String stime = String.valueOf(json_data.getInt("Id"));
			result.setText(stime);
		} 
		catch (Exception e) {
			// Code to handle exception
		}
		try {
			//JSONArray jArray = new JSONArray(result);
			TableLayout tv = (TableLayout) findViewById(R.id.table);
			tv.removeAllViewsInLayout();
			int flag = 1;
			for (int i = -1; i < len ; i++) {
				TableRow tr = new TableRow(TimeTable1.this);
				tr.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
				if (flag == 1) {
					TextView b6 = new TextView(TimeTable1.this);
					b6.setText("Id");
					
					b6.setTextColor(Color.BLUE);
					b6.setTextSize(15);
					tr.addView(b6);
					TextView b19 = new TextView(TimeTable1.this);
					b19.setPadding(10, 0, 0, 0);
					b19.setTextSize(15);
					b19.setText("Name");
					b19.setTextColor(Color.BLUE);
					tr.addView(b19);
					TextView b29 = new TextView(TimeTable1.this);
					b29.setPadding(10, 0, 0, 0);
					b29.setText("Status");
					b29.setTextColor(Color.BLUE);
					b29.setTextSize(15);
					tr.addView(b29);
					tv.addView(tr);
					final View vline = new View(TimeTable1.this);
					vline.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, 2));
					vline.setBackgroundColor(Color.BLUE);
					tv.addView(vline);
					flag = 0;
				} else {
					JSONObject json_data = jArray.getJSONObject(i);
					
					Log.i("log_tag", "id: " + json_data.getInt("Id") + ", Username: " + json_data.getString("username") + ", No: " + json_data.getString("password"));
					TextView b = new TextView(TimeTable1.this);
					String stime = String.valueOf(json_data.getInt("Id"));
					b.setText(stime);
					b.setTextColor(Color.RED);
					b.setTextSize(15);
					tr.addView(b);
					TextView b1 = new TextView(TimeTable1.this);
					b1.setPadding(10, 0, 0, 0);
					b1.setTextSize(15);
					String stime1 = json_data.getString("username");
					b1.setText(stime1);
					b1.setTextColor(Color.BLACK);
					tr.addView(b1);
					TextView b2 = new TextView(TimeTable1.this);
					b2.setPadding(10, 0, 0, 0);
					String stime2 = json_data.getString("password");
					b2.setText(stime2);
					b2.setTextColor(Color.BLACK);
					b2.setTextSize(15);
					tr.addView(b2);
					tv.addView(tr);
					final View vline1 = new View(TimeTable1.this);
					vline1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, 1));
					vline1.setBackgroundColor(Color.WHITE);
					tv.addView(vline1);
				}
			}
		} catch (JSONException e) {
			Log.e("log_tag", "Error parsing data" + e.toString());
			Toast.makeText(getApplicationContext(), "JsonArray fail", Toast.LENGTH_SHORT).show();
		}

		
	}
}