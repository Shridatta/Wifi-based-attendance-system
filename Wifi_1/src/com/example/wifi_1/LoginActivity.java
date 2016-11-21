package com.example.wifi_1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")
public class LoginActivity extends Activity {

	protected static final Context LoginActivity = null;
	int flag=1;
	Button b1,b2;
	EditText un,pass;//et_ip;
	TextView tv;
	HttpPost httppost;
	StringBuffer buffer;
	HttpResponse response;
	HttpClient httpclient;
	HttpGet request;
	List<NameValuePair> nameValuePairs;

	ProgressDialog dialog =null;
	@SuppressLint("NewApi")
	@Override				
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		if (android.os.Build.VERSION.SDK_INT > 9) {
		    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		}

		//String url;
	//  url = "http://192.168.0.10/android_connect/login.php";

		tv=(TextView)findViewById(R.id.tv1);
		b1=(Button)findViewById(R.id.bt_login);
		b2=(Button)findViewById(R.id.bt_dlogin);
		un = (EditText) findViewById(R.id.et_user_name);
		pass = (EditText) findViewById(R.id.et_password);
		//setting=(Button)findViewById(R.id.al_bt_setting);
		// ok=(Button)findViewById(R.id.al_bt_ok);
		//  et_ip=(EditText)findViewById(R.id.al_ed_ip);
		pass=(EditText)findViewById(R.id.et_password);
		/*    un.bringToFront();
        pass.bringToFront();
      /*
        setting.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

			}
		});*/
		b2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(LoginActivity.this, MainPage.class));

			}
		});
		b1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				dialog = ProgressDialog.show(LoginActivity.this,
						"Validating user...", "a", true);

				Toast.makeText(LoginActivity.this, "Thread starteds",
						Toast.LENGTH_LONG).show();

				new Thread(new Runnable() {
					public void run() {
						Log.e("tag", "login called");

						login();
					}
				}).start();
			}
			/*try{
        		Intent i=new Intent (com.example.wifi_1.MainPage);
        	}
        	catch(Exception e){

        	}
			 */       	
			});
	}
	void login(){

		try{           

			httpclient=new DefaultHttpClient();
			httppost= new HttpPost("http://192.168.137.1/android_connect/logi.php"); // make sure the url is correct.
			//httppost= new HttpPost("http://10.0.2.2/test.php");
			
			Log.e("tag", "http post created");
			
			//add your data
			nameValuePairs = new ArrayList<NameValuePair>(2);
			// Always use the same variable name for posting i.e the android side variable name and php side variable name should be similar,
			nameValuePairs.add(new BasicNameValuePair("username",un.getText().toString().trim()));  // $Edittext_value = $_POST['Edittext_value'];
			nameValuePairs.add(new BasicNameValuePair("password",pass.getText().toString().trim()));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			//Execute HTTP Post Request
			response=httpclient.execute(httppost);
			
			Log.e("tag", "client executed : response : " + response);
			
			// edited from here....
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			Log.e("tag", "response handler : response : " + response);
			
     final	String response = httpclient.execute(httppost, responseHandler);
			
			
			
			Log.e("tag", "response handler : response : " + response);
			
			runOnUiThread(new Runnable() {
				public void run() {
					tv.setText("Response from PHP : " + response);
					//tv.setText("");     
					dialog.dismiss();
				}

			});

 //String s=response.getStatusLine().toString();
		if(!response.contains("No Such User Found"))
			{

				runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(LoginActivity.this,"Login Success", Toast.LENGTH_SHORT).show();
					}
				});

				startActivity(new Intent(LoginActivity.this, MainPage.class));
				//   setContentView(R.layout.main_page);
			}
			else
			{

				showAlert();               
			}
			
		}
		
		catch(IOException e){
			Log.e("tag", "IOException : " + e.getMessage());
		}
		finally{
			dialog.dismiss();
		}

	}
	public void showAlert(){
		LoginActivity.this.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder =new AlertDialog.Builder(LoginActivity.this);
				builder.setTitle("Login Error.");
				builder.setMessage("User not Found.")						
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id){}
				});
				AlertDialog alert = builder.create();
				alert.show();               
			}
		});
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

}
