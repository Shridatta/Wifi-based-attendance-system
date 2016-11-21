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
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.sax.TextElementListener;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AdminPanel extends Activity{

	   HttpPost httppost;
	   StringBuffer buffer;
	   HttpResponse response;
	   HttpClient httpclient;
	   HttpGet request;
	   List<NameValuePair> nameValuePairs;
	   ProgressDialog dialog =null;
	   Button bt_send;
	TextView tv;
	String[] device;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adminhome);
		Button bt1=(Button)findViewById(R.id.bt_reg_detail);
		bt_send=(Button)findViewById(R.id.bt_timetable_adminhome);
		tv=(TextView)findViewById(R.id.tv_list_adminpanel);
		Intent intent=getIntent();
		if(null!=intent)
		{
			device=intent.getStringArrayExtra("KEY_LIST");
		}
		
		
		bt1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			tv.setText(tv.getText()+device[0]+device[1]);
				
			}
		});
		
		bt_send.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			//	dialog = ProgressDialog.show(AdminPanel.this,"sending data...", null, true);
	       	working();
				new Thread(new Runnable() {
	        	public void run() {
	        	login();
	        	}
	        	}).start();
	        	}
			
		});
	
	}

	void login(){
		
		   try{           
           
         httpclient=new DefaultHttpClient();
         httppost= new HttpPost("http://192.168.0.10/attendance/insert_attendance.php"); // make sure the url is correct.
         //add your data
         working();
         nameValuePairs = new ArrayList<NameValuePair>(1);
         // Always use the same variable name for posting i.e the android side variable name and php side variable name should be similar,
         nameValuePairs.add(new BasicNameValuePair("username","nit56"));  // $Edittext_value = $_POST['Edittext_value'];
        // nameValuePairs.add(new BasicNameValuePair("device_id",device[0].trim()));
       //  nameValuePairs.add(new BasicNameValuePair("slot_id","1".trim()));
         httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
         //Execute HTTP Post Request
         response=httpclient.execute(httppost);
         // edited from here....
         ResponseHandler<String> responseHandler = new BasicResponseHandler();
         final String response = httpclient.execute(httppost, responseHandler);
        System.out.println("Response : " + response);
         runOnUiThread(new Runnable() {
             public void run() {
                 //tv.setText("Response from PHP : " + response);
                 dialog.dismiss();
           }
             
         });
         Toast.makeText(AdminPanel.this,response, Toast.LENGTH_SHORT).show();
         /*
        if(response.equalsIgnoreCase("Successful")){
             runOnUiThread(new Runnable() {
                 public void run() {
                     Toast.makeText(AdminPanel.this,"Success", Toast.LENGTH_SHORT).show();
                 }
             });
              
             startActivity(new Intent(AdminPanel.this, MainPage.class));
           //  setContentView(R.layout.main_page);
         }else{
             showAlert();               
         }
    */      
     }
     catch (ClientProtocolException e){
    	    dialog.dismiss();
            System.out.println("Exception : " + e.getMessage());
        
     }
     catch(IOException e){
    	    dialog.dismiss();
            System.out.println("Exception : " + e.getMessage());
        	
     }
     catch(Exception e){
         dialog.dismiss();
         System.out.println("Exception : " + e.getMessage());
     }
 
	}
		   
		   
		   
		   public void showAlert(){
			AdminPanel.this.runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					AlertDialog.Builder builder =new AlertDialog.Builder(AdminPanel.this);
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

		   public void working() {
				// TODO Auto-generated method stub
				 Dialog d = new Dialog(this);
				 d.setTitle("hech yea!");
				 TextView tv = new TextView(this);
				 tv.setText("Success");
				 d.setContentView(tv);
				 d.show();
				
			}
}