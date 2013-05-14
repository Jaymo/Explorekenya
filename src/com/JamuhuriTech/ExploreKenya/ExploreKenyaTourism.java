package com.JamuhuriTech.ExploreKenya;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.JamuhuriTech.ExploreKenya.functions.JSONfunctions;
import com.JamuhuriTech.ExploreKenya.functions.JSONfunctions.JSONCallback;
import com.JamuhuriTech.ExploreKenya.util.LazyAdapter_Tourism;
import com.JamuhuriTech.ExploreKenya.util.Util;

public class ExploreKenyaTourism extends Activity {
	//public static String URL="http://10.0.2.2/android/api_fetch.php?q=";
	 public static String URL="http://akajaymo.kodingen.com/api_fetch.php?q=";
	public static String table="tbl_tourism";
	public static final String KEY_REF = "ref";
	public static final String KEY_NAME = "name";
	public static final String KEY_IMAGE_URL = "image_url";
	
	ListView list;
	StringBuilder uriBuilder;
	LazyAdapter_Tourism adapter;
	JSONObject json;
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.locations);
		
		uriBuilder = new StringBuilder(URL);
        uriBuilder.append(table);
        
		ArrayList<HashMap<String, String>> CategoryList = new ArrayList<HashMap<String, String>>();
		JSONfunctions.getJSONfromURL(uriBuilder.toString(), new JSONCallback() {

	        @Override
	        public void onResult(JSONObject result) {
	        	json =result;
	        }
	    });

         
		
        try{
        	
        	JSONArray  hoteli = json.getJSONArray("PAYLOAD");
        	for(int i=0;i<hoteli.length();i++){						
				HashMap<String, String> map = new HashMap<String, String>();	
				JSONObject e = hoteli.getJSONObject(i);
				map.put(KEY_REF, e.getString(KEY_REF));
				map.put(KEY_NAME, e.getString(KEY_NAME));
				map.put(KEY_IMAGE_URL, e.getString(KEY_IMAGE_URL));
				CategoryList.add(map);
	        	
	        				
			}		
        }
        catch(JSONException e)      {
        	 Log.e("log_tag", "Error kupitisha data "+e.toString());
        }
        list=(ListView)findViewById(R.id.list);
        adapter=new LazyAdapter_Tourism(this, CategoryList); 
        list.setAdapter(adapter);
        list.setTextFilterEnabled(true);
        list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v,int position, long id) {
				switch (position){
				case 0: 
					if (Util.isConnected(ExploreKenyaTourism.this)) {
					Intent a =new Intent(ExploreKenyaTourism.this,TourismDisplay.class);
				    a.putExtra("link", "tbl_parks");
				    startActivity(a);
					}
					else{
	            		 Toast.makeText(ExploreKenyaTourism.this, " No internet Connection Detected", Toast.LENGTH_LONG).show();
	            	}
					
				    break;
				case 1: 
					if (Util.isConnected(ExploreKenyaTourism.this)) {
					Intent b =new Intent(ExploreKenyaTourism.this,TourismDisplay.class);
					b.putExtra("link", "tbl_reserves");
				    startActivity(b);
					}
					else{
	            		 Toast.makeText(ExploreKenyaTourism.this, " No internet Connection Detected", Toast.LENGTH_LONG).show();
	            	}
					
					break;
				case 2: 
					if (Util.isConnected(ExploreKenyaTourism.this)) {
					Intent c =new Intent(ExploreKenyaTourism.this,TourismDisplay.class);
					c.putExtra("link", "tbl_marine");
				    startActivity(c);
					}
					else{
	            		 Toast.makeText(ExploreKenyaTourism.this, " No internet Connection Detected", Toast.LENGTH_LONG).show();
	            	}
					
					break;
				case 3: 
					if (Util.isConnected(ExploreKenyaTourism.this)) {
					Intent d =new Intent(ExploreKenyaTourism.this,TourismDisplay.class);
					d.putExtra("link", "tbl_museums");
				    startActivity(d);
					}
					else{
	            		 Toast.makeText(ExploreKenyaTourism.this, " No internet Connection Detected", Toast.LENGTH_LONG).show();
	            	}
					
					break;
				case 4: 
					if (Util.isConnected(ExploreKenyaTourism.this)) {
					Intent e =new Intent(ExploreKenyaTourism.this,TourismDisplay.class);
					e.putExtra("link", "tbl_hotels");
				    startActivity(e);
					}
					else{
	            		 Toast.makeText(ExploreKenyaTourism.this, " No internet Connection Detected", Toast.LENGTH_LONG).show();
	            	}
					
					break;
				case 5: 
					if (Util.isConnected(ExploreKenyaTourism.this)) {
					Intent f =new Intent(ExploreKenyaTourism.this,TourismDisplay.class);
					f.putExtra("link", "tbl_resorts");
				    startActivity(f);
					}
					else{
	            		 Toast.makeText(ExploreKenyaTourism.this, " No internet Connection Detected", Toast.LENGTH_LONG).show();
	            	}
					break;
				}
				
                   
					
			}
        });
	}
	@Override
    public void onBackPressed() {

    	makeDialog();
    }
    private void makeDialog() {
	    AlertDialog.Builder dialog = new AlertDialog.Builder(this);	    

	    dialog.setMessage("Are you sure you want to exit?");

	    dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface arg0, int arg1) {
	        	ExploreKenyaTourism.this.finish();
	        }
	    });

	    dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface arg0, int arg1) {}
	        
	    });

	    dialog.show();

	    }

}
