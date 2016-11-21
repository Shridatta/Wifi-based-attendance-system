package com.example.wifi_1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DataSend extends Activity {
	ProgressDialog dialog = null;
	HttpPost httppost;
	StringBuffer buffer;
	HttpResponse response;
	HttpClient httpclient;
	HttpGet request;
	List<NameValuePair> nameValuePairs;
	String allmac = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.datasend);

		int i = 0, count = 0;
		String[] device = null;
		TextView tv1 = (TextView) findViewById(R.id.datasent_tv_label);
		Button bt1 = (Button) findViewById(R.id.datasent_bt_presentdata);

		Intent intent = getIntent();
		if (null != intent) {
			device = intent.getStringArrayExtra("KEY_LIST");
			count = intent.getIntExtra("KEY_LISTNUMBER", 1);

		}
		/*
		 * if(device[0]!=null) tv1.setText(tv1.getText()+device[i]);
		 */
		for (i = 0; i < count; i++) {
			//
			if (i != 0)
				allmac = allmac + "," + device[i];
			else
				allmac = device[i];
		}
		tv1.setText(allmac);
		bt1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog = ProgressDialog.show(DataSend.this,
						"Validating user...", null, true);
				new Thread(new Runnable() {
					public void run() {
						send();
					}
				}).start();
			}
		});

	}

	void send() {

		try {

			httpclient = new DefaultHttpClient();
			httppost = new HttpPost("http://192.168.137.1/attendance/send_attendance1.php"); // make
																			// sure
																			// the
																			// url
																			// is
																			// correct.
			// add your data

			nameValuePairs = new ArrayList<NameValuePair>(5);
			// Always use the same variable name for posting i.e the android
			// side variable name and php side variable name should be similar,
			nameValuePairs.add(new BasicNameValuePair("username", "bagadesir".toString())); // $Edittext_value =$_POST['Edittext_value'];
			nameValuePairs.add(new BasicNameValuePair("slot", "3"));
			nameValuePairs.add(new BasicNameValuePair("sub", "1"));
			nameValuePairs.add(new BasicNameValuePair("div", "111"));
			nameValuePairs.add(new BasicNameValuePair("arr", allmac.toString()));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			// Execute HTTP Post Request
			response = httpclient.execute(httppost);
			// edited from here....
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			final String response = httpclient.execute(httppost,
					responseHandler);
		//	System.out.println("Response : " + response);
			runOnUiThread(new Runnable() {
				public void run() {
					// tv.setText("Response from PHP : " + response);
					dialog.dismiss();
				}

			});
			Toast.makeText(DataSend.this, response, Toast.LENGTH_SHORT).show();
			
			  if(response.equalsIgnoreCase("Updated")){ runOnUiThread(new
			  Runnable() { public void run() {
			  Toast.makeText(DataSend.this,"Success",Toast.LENGTH_LONG).show(); } });
			  
			  }
			  else
			  { 
				  showAlert(); 
			  }
			 
		} catch (ClientProtocolException e) {
			dialog.dismiss();
			System.out.println("Exception : " + e.getMessage());

		} catch (IOException e) {
			dialog.dismiss();
			System.out.println("Exception : " + e.getMessage());

		} catch (Exception e) {
			dialog.dismiss();
			System.out.println("Exception : " + e.getMessage());
		}

	}

	public void showAlert() {
		DataSend.this.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new AlertDialog.Builder(
						DataSend.this);
				builder.setTitle("Error.");
				builder.setMessage("Sending Error.")
						.setCancelable(false)
						.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
									}
								});
				AlertDialog alert = builder.create();
				alert.show();
			}
		});
	}
}
