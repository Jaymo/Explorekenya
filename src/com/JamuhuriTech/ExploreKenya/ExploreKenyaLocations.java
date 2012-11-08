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
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.JamuhuriTech.ExploreKenya.functions.JSONfunctions;
import com.JamuhuriTech.ExploreKenya.util.LazyAdapter;

public class ExploreKenyaLocations extends Activity {
	
	public static String URL="http://akajaymo.kodingen.com/api_fetch.php?q=";
	//public static String URL="http://10.0.2.2/android/api_fetch.php?q=";
	public static final String KEY_REF = "ref";
	public static final String KEY_NAME = "name";
	public static final String KEY_IMAGE_URL = "image_url";
	public static String table="tbl_locations";
	StringBuilder uriBuilder;
	ListView list;
	LazyAdapter adapter;
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.locations);
		
		uriBuilder = new StringBuilder(URL);
        uriBuilder.append(table);
		ArrayList<HashMap<String, String>> CategoryList = new ArrayList<HashMap<String, String>>();
	      
	       
        JSONObject json = JSONfunctions.getJSONfromURL(uriBuilder.toString());
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
        adapter=new LazyAdapter(this, CategoryList); 
        list.setAdapter(adapter);
        list.setTextFilterEnabled(true);
        list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v,int position, long id) {
				switch (position){
				case 0: 
					Intent nairobi =new Intent(ExploreKenyaLocations.this,Nairobi.class);
				    startActivity(nairobi);
				    break;
				case 1: 
					Intent mombasa =new Intent(ExploreKenyaLocations.this,Mombasa.class);
					startActivity(mombasa);
					break;
				case 2: 
					Intent malindi =new Intent(ExploreKenyaLocations.this,Malindi.class);
					startActivity(malindi);
					break;
				case 3: 
					Intent kisumu =new Intent(ExploreKenyaLocations.this,Kisumu.class);
					startActivity(kisumu);
					break;
				case 4: 
					Intent nakuru =new Intent(ExploreKenyaLocations.this,Nakuru.class);
					startActivity(nakuru);
					break;
				case 5: 
					Intent nanyuki =new Intent(ExploreKenyaLocations.this,Nanyuki.class);
					startActivity(nanyuki);
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
	        	ExploreKenyaLocations.this.finish();
	        }
	    });

	    dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface arg0, int arg1) {}
	        
	    });

	    dialog.show();

	    }

}
