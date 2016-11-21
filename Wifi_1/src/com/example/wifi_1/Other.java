package com.example.wifi_1;


import android.app.Activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Other extends Activity {//implements View.OnClickListener{
	
	Boolean didwork;
	Context context;
	TextView tv =(TextView)findViewById(R.id.tv_getdata);
	
	 public void onCreate(Bundle savedInstanceState){
		 super.onCreate(savedInstanceState);
	      setContentView(R.layout.other);
	  
	/*      Button bt1=(Button)findViewById(R.id.bt_send);
	  	
	      Button bt2=(Button)findViewById(R.id.bt_getdata);
	  	context=this;
	  	
	      bt1.setOnClickListener(this);
		  bt2.setOnClickListener(this);
	    //  working();
	  */  
	 }
	 /*
	   public void working() {
			// TODO Auto-generated method stub
			 Dialog d = new Dialog(this);
			 d.setTitle("hech yea!");
			 TextView tv = new TextView(this);
			 tv.setText("Success");
			 d.setContentView(tv);
			 d.show();
			
		}
	   private void notworking(String s) {
			// TODO Auto-generated method stub
			 Dialog d = new Dialog(this);
			 d.setTitle("error!");
			 TextView tv = new TextView(this);
			 tv.setText("unSuccess"+s);
			 d.setContentView(tv);
			 d.show();
			
		}

	 @Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		 String mac=new String();
		 switch(arg0.getId()){
		 	
		 case R.id.bt_getdata:
			try{
	
				Data info1=new Data(Other.this);			
				info1.open();
  				String infodata=info1.getData();
  				info1.close();
  				TextView tv =(TextView)findViewById(R.id.tv_getdata);
			
  				tv.setText(infodata);
  				working();
			}
  			catch(Exception e){
  				didwork=false;
  		 
  				notworking(e.toString());
  			}
			finally{
				if(didwork){
				working();
				
				}
			}

			break;
			case R.id.bt_send:
	    		  didwork=true;
	  		  	
	    		 // for(int i=0;i<count;i++)
	    		  {
	    			  	//working();
	    			
	    			  	try{
	    		  			//String mac=device[i];
	    		  			
	    		  			mac="74e543e3b007";
	    		  			//notworking(mac);
	    		  		//	int slot=1;
	    		  			
	    		  			Data entry=Entry();
	    		  			
	    		  		//	working();
	    		  			entry.open();
	    		  			entry.createntry(mac);
	    		  			
	    		  			entry.close();
	    		  			working();
	        		  		
	    			  	}
	    			  
	    		  		catch(Exception e){
	    		  			didwork=false;
	    		  		//e.getCause();
	    		  			notworking(e.toString()+e.getCause()+e.getStackTrace());
	    		  		}
	    			 
	    		  		/*finally{
	    		  			if(didwork){
	    		  				/*Dialog d = new Dialog();
	    		  				d.setTitle("hech yea!");
	    		  				TextView tv = new TextView(this);
	    		  				tv.setText("Success");
	    		  				d.setContentView(tv);
	    		  				d.show();
	    	//	  				Toast.makeText(getBaseContext(),R.id.et_user_name, Toast.LENGTH_SHORT).show();
	    		  				notworking("ok");
	    		  		}	
	    		  		}*
	    		  }
	    		  break;
		 }
	}

	 public Data Entry()
	 {
		 Data entry =new Data(context);
		 return entry;
	 }
*/
}
