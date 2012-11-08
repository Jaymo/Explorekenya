package com.JamuhuriTech.ExploreKenya;

/**
 * @Author Mwenda Wahome
 * @Web http://www.jamuhuritech.com
 */

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.JamuhuriTech.ExploreKenya.functions.JSONfunctions;
import com.JamuhuriTech.ExploreKenya.util.ActionItem;
import com.JamuhuriTech.ExploreKenya.util.LazyAdapter_Events;
import com.JamuhuriTech.ExploreKenya.util.QuickAction;
import com.JamuhuriTech.ExploreKenya.util.pref;

public class ExploreKenyaEvents extends Activity{
	private static final int WEBSITE = 1;
	private static final int PHONE = 2;
	private static final int EMAIL = 3;
	private static final int DRIVE = 4;
	public static int updt = 0;

	public static final String KEY_ID = "id";
	public static final String KEY_EVENT_NAME = "event_name";
	public static final String KEY_CATEGORY = "category";
	public static final String KEY_DATE = "date";
	public static final String KEY_REF = "ref";
	public static final String KEY_PHONE = "phone";
	public static final String KEY_WEBSITE = "website";
	public static final String KEY_EMAIL = "email";
	public static final String KEY_IMAGE_URL = "image_url";
	public static String table="tbl_events";
	
    //public static String URL="http://10.0.2.2/android/api_fetch.php?q=";
    public static String URL="http://akajaymo.kodingen.com/api_fetch.php?q=";
    //public static String LINK="http://10.0.2.2/android/api_update.php";
    public static String LINK="http://akajaymo.kodingen.com/api_update.php";
    private Intent i;
	ListView list;
	LazyAdapter_Events adapter;
	MediaPlayer mp;
	ImageView selected;
    QuickAction quickAction_event_list;
    ActionItem website,telephone,emailmessage,drive;
    ActionItem actionItem ;
    StringBuilder uriBuilder;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.locations);
       
        uriBuilder = new StringBuilder(URL);
        uriBuilder.append(table);
        
        website     = new ActionItem(WEBSITE, ":More", getResources().getDrawable(R.drawable.website));
        emailmessage	= new ActionItem(EMAIL, "Email", getResources().getDrawable(R.drawable.email_logo));
        telephone	= new ActionItem(PHONE, "Call", getResources().getDrawable(R.drawable.phone));
        drive	= new ActionItem(DRIVE, "Drive", getResources().getDrawable(R.drawable.drive));

        ArrayList<HashMap<String, String>> eventslist = new ArrayList<HashMap<String, String>>(); 
        JSONObject json = JSONfunctions.getJSONfromURL(uriBuilder.toString());
     
        
        try{
        	
        	JSONArray  mot = json.getJSONArray("PAYLOAD");
	        for(int i=0;i<mot.length();i++){						
	        	HashMap<String, String> map = new HashMap<String, String>();		
				JSONObject e = mot.getJSONObject(i);
				map.put(KEY_ID, e.getString(KEY_ID));
				map.put(KEY_EVENT_NAME, e.getString(KEY_EVENT_NAME));
				map.put(KEY_CATEGORY, e.getString(KEY_CATEGORY));
				map.put(KEY_DATE, e.getString(KEY_DATE));
				map.put(KEY_REF, e.getString(KEY_REF));
				map.put(KEY_PHONE, e.getString(KEY_PHONE));
				map.put(KEY_EMAIL, e.getString(KEY_EMAIL));
				map.put(KEY_WEBSITE, e.getString(KEY_WEBSITE));
				map.put(KEY_IMAGE_URL, e.getString(KEY_IMAGE_URL));
				eventslist.add(map);
			}		
        }catch(JSONException e)      {
        	 Log.e("log_tag", "Error kupitisha data "+e.toString());
        }
        list=(ListView)findViewById(R.id.list);
        adapter=new LazyAdapter_Events(this, eventslist);
        list.setAdapter(adapter);
        list.setTextFilterEnabled(true);
        list.setOnItemClickListener(new OnItemClickListener() {
        	public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        		
        		quickAction_event_list = new QuickAction(ExploreKenyaEvents.this, QuickAction.HORIZONTAL);
        		//quickAction_event_list.addActionItem(drive);->To add in the next version
        		
        		quickAction_event_list.addActionItem(website);
        		quickAction_event_list.addActionItem(telephone);
        		quickAction_event_list.addActionItem(emailmessage);

           	    
           	    quickAction_event_list.show(view);
        		
        		quickAction_event_list.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {			

        			public void onItemClick(QuickAction source, int pos, int actionId) {				
        				actionItem = quickAction_event_list.getActionItem(pos);
        				
        				if (actionId == WEBSITE) {
        					HashMap<String, String> o = (HashMap<String, String>) list.getAdapter().getItem(position);
    						String site =o.get(KEY_WEBSITE);
        					i = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(site));
        					startActivity(i);
        					
        				}
        				if (actionId == PHONE) {        					
        					HashMap<String, String> o = (HashMap<String, String>) list.getAdapter().getItem(position);
    						String PHONENUMBER =o.get(KEY_PHONE);
    						String number ="tel:"+PHONENUMBER.toString().trim();
    						Intent call = new Intent (Intent.ACTION_DIAL,Uri.parse(number));
    		        		startActivity(call);	
        						
        				}  if (actionId == EMAIL) {
        					HashMap<String, String> o = (HashMap<String, String>) list.getAdapter().getItem(position);
    						String EventEmail =o.get(KEY_EMAIL);
    						Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
    		            	String aEmailList[] = { EventEmail};
    		            	emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, aEmailList);
    		            	emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "ExploreKenya User Request");
    		            	emailIntent.setType("plain/text");  
    		            	startActivity(Intent.createChooser(emailIntent, "Select Email Client:"));
        					
        				} 
        				else if (actionId == DRIVE) { 
        					
        					
        				}
        				else {
        				}
        			}
        		});
        		quickAction_event_list.setOnDismissListener(new QuickAction.OnDismissListener() {			
        			@Override
        			public void onDismiss() {}});        			
        		mp = MediaPlayer.create(getApplicationContext(), R.raw.pling); 
                mp.start();
        	}
		});
        JSONObject json_updt = JSONfunctions.getJSONfromURL(LINK);
    	try{
        	JSONArray  updates = json_updt.getJSONArray("UPDATES");
        	for(int i=0;i<1;i++){
    			JSONObject e = updates.getJSONObject(i);
    			updt= e.getInt("MAX(id)");
    			
            	
        	}
    	}catch(JSONException e)      {
       	 Log.e("log_tag", "Error kupitisha data ya updates "+e.toString());
       }
    	pref.loadSettings(this);
    	pref.events=updt;
    	pref.saveSettings(this);
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
	        	ExploreKenyaEvents.this.finish();
	        }
	    });

	    dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface arg0, int arg1) {}
	        
	    });

	    dialog.show();

	    }
    
}

