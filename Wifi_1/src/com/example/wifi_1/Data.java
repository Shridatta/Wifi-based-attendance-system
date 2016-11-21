package com.example.wifi_1;

import android.content.ContentValues;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Data {
	public static final String KEY_ROWID="_id";
	public static final String KEY_NAME="name";
//	public static final String KEY_ADDRESS="address";
	private static final String DATABASE_NAME="classinfo";
	private static final String DATABASE_TABLE="attendance";
	private static final int DATABASE_Version=1;
	private Dbhelper ourHelper;
	private final Context ourContext;
	private SQLiteDatabase ourDatabase;
	MainPage w =new MainPage();
	private static class Dbhelper extends SQLiteOpenHelper{

		public Dbhelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_Version);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
		db.execSQL("CREATE TABLE" + " "+DATABASE_TABLE + " ("+ KEY_ROWID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
				+KEY_NAME + " TEXT NOT NULL);");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS"+ DATABASE_TABLE);
			onCreate(db);
		}
		
	}
	public Data(Context c){
		ourContext = c;
	}
	
	public Data open(){
		
		ourHelper = new Dbhelper(ourContext);
		ourDatabase = ourHelper.getReadableDatabase();
		
		return this;
	}
	public void close()
	{
		ourHelper.close();
	}
	public long createntry(String name) {
		// TODO Auto-generated method stub
		ContentValues cv =new ContentValues();
		cv.put(KEY_NAME,name);
		//cv.put(KEY_ADDRESS, address);

		return ourDatabase.insert(DATABASE_TABLE, null, cv);
	}
	public String getData() {
		// TODO Auto-generated method stub
		String[] columns=new String[]{KEY_ROWID,KEY_NAME};
		Cursor c =ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);
		String result = "";
		int iRow= c.getColumnIndex(KEY_ROWID);
		int iName=c.getColumnIndex(KEY_NAME);
		//int iAddress=c.getColumnIndex(KEY_ADDRESS);
		for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
		{
			result = result + c.getString(iRow) + " " + c.getString(iName) + "\n";
		}
		
		return result;
	}

	   
	}
