package com.JamuhuriTech.ExploreKenya;

/**
 * @author Jaymo
 *
 * @website http://www.jamuhuritech.com
 *
 */
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.JamuhuriTech.ExploreKenya.functions.CustomItemizedOverlay;
import com.JamuhuriTech.ExploreKenya.functions.CustomOverlayItem;
import com.JamuhuriTech.ExploreKenya.functions.JSONfunctions;
import com.JamuhuriTech.ExploreKenya.functions.JSONfunctions.JSONCallback;
import com.JamuhuriTech.ExploreKenya.util.Util;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class MapLocationsNanyuki extends UserLocationMap {
	
	public static String URL="http://akajaymo.kodingen.com/api_map.php?q=";
	//public static String URL="http://10.0.2.2/android/api_map.php?q=";
	Drawable drawable;
	String motel1="",query;
	CustomItemizedOverlay<CustomOverlayItem> itemizedOverlay;
	List<Overlay> mapOverlays;	
	static JSONObject json ;
	GeoPoint nanyuki;
	StringBuilder uriBuilder;
	public static String parameter;
	String tbl_nairobi,tbl_mombasa,tbl_malindi,tbl_kisumu,tbl_nakuru,tbl_nanyuki;
	JSONObject mjson;
	public static Typeface tf;

	
	@Override
	 public void onCreate(Bundle savedInstanceState)
	 {
	 super.onCreate(savedInstanceState);
	 requestWindowFeature(Window.FEATURE_NO_TITLE);
	 setContentView(R.layout.map);
	 setTitleFromActivityLabel(R.id.title_text);
	 
	 mapView = (MapView)findViewById(R.id.mapView);
	 mapView.setBuiltInZoomControls(true);
	 mapController = mapView.getController();
 	 
	 
	 nanyuki = new GeoPoint((int)(0.016667*1E6),(int)(37.066667*1E6));
	 mapController.animateTo(nanyuki);
	 
	 
	 
	 parameter = getIntent().getExtras().getString("link");
     uriBuilder = new StringBuilder(URL);
     uriBuilder.append(parameter);
     
       
     
     
	   initializeMap();	
	 }
	 
		 
		 
		 
		 private void initializeMap() {
			 
			 JSONfunctions.getJSONfromURL(uriBuilder.toString(), new JSONCallback() {

			        @Override
			        public void onResult(JSONObject result) {
			        	mjson =result;
			        }
			    });
		   
			 try{
				 List<Overlay> mapOverlays = mapView.getOverlays();
		     	 JSONArray  places = mjson.getJSONArray("PAYLOAD");
		     	
			        for(int i=0;i<places.length();i++){							
						JSONObject e =  places.getJSONObject(i);
			    	    String name=    e.getString("name");
			    	    String email=    e.getString("email");
			    	    String description=    e.getString("description");
			    	    drawable = this.getResources().getDrawable(R.drawable.blue);
			    	    itemizedOverlay = new CustomItemizedOverlay<CustomOverlayItem>(drawable, mapView);
			    	    GeoPoint point = new GeoPoint((int)(e.getDouble("lat")*1E6),(int) (e.getDouble("lon")*1E6));			    	    
			    	    CustomOverlayItem overlayItem = new CustomOverlayItem(point,"Name: "+name ,"Info: "+description+"\nEmail: "+email+"\n\n");			    	      	    
			    	    itemizedOverlay.addOverlay((CustomOverlayItem) overlayItem);			    	    
			    	    mapOverlays.add(itemizedOverlay);
			        }		
		     }
		     catch(JSONException e)        {
		     	 Log.e("log_tag", "Error kupitisha JSON "+e.toString());
		     }
		     
		 }
		
	
	
		
		public void onClickHome (View v)
		{
			this.finish();
		}
		
			
		
		
	 @Override
	 protected boolean isRouteDisplayed()
	 {
	 return false;
	 
	 } 
	  
	 @Override
	 protected void onResume() {
	        super.onResume();
	        initializeMap();
	    }
	 @Override
	 public void onPause() {
		    super.onPause();
	    }
	 public void onClickShare (View v)
		{
			if (Util.isConnected(MapLocationsNanyuki.this)) {
			Intent intent = new Intent(MapLocationsNanyuki.this, ExploreKenyaShare.class);
	        startActivity(intent);
			}
			else{
	      		 Toast.makeText(MapLocationsNanyuki.this, " No internet Connection Detected", Toast.LENGTH_LONG).show();
	      	}
		}
	 @Override
	    public void onBackPressed() {

	    	this.finish();
	    }
	    
	 public void setTitleFromActivityLabel (int textViewId)
	    {
	        TextView tv = (TextView) findViewById (textViewId);
	        if (tv != null) 
	        tv.setText (getTitle ());
	        tf = Typeface.createFromAsset(getAssets(),"data/fonts/BROKEN_GHOST.ttf");
	        tv.setTextSize(24);
	        tv.setTypeface(tf);
	    }
	}
	    
	    
	 


