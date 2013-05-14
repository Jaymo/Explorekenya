package com.JamuhuriTech.ExploreKenya;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.JamuhuriTech.ExploreKenya.functions.JSONfunctions;
import com.JamuhuriTech.ExploreKenya.functions.JSONfunctions.JSONCallback;
import com.JamuhuriTech.ExploreKenya.util.ActionItem;
import com.JamuhuriTech.ExploreKenya.util.LazyAdapter_tourism_display;
import com.JamuhuriTech.ExploreKenya.util.QuickAction;


public class LocationsDisplay extends Activity {
	private static final int WEBSITE = 1;
	private static final int PHONE = 2;
	private static final int EMAIL = 3;
	
	public static final String KEY_ID = "id";
	public static final String KEY_NAME = "name";
	public static final String KEY_DESCRIPTION = "description";
	public static final String KEY_LOCATION = "location";
	public static final String KEY_REF = "ref";
	public static final String KEY_PHONE = "phone";
	public static final String KEY_WEBSITE = "website";
	public static final String KEY_EMAIL = "email";
	public static final String KEY_IMAGE_URL = "image_url";
	static JSONObject json ;
	public static String parameter;
	//public static String URL="http://10.0.2.2/android/api_fetch.php?q=";
	public static String URL="http://akajaymo.kodingen.com/api_fetch.php?q=";
	//public static String SEARCH_URL="http://10.0.2.2/android/api_search.php";
	public static String SEARCH_URL="http://akajaymo.kodingen.com/search_api.php";
	public static Typeface tf;
	String q_search="",query;
	private Intent i;
	ListView list;
	LazyAdapter_tourism_display adapter;
	MediaPlayer mp;
	ImageView selected;
    QuickAction quickAction_list,quickAction_list_search;
    ActionItem website,telephone,emailmessage;
    ActionItem actionItem ;
    StringBuilder uriBuilder;
    static final String[] repo;
    ImageButton map;
    JSONObject Mjson;
    static
	{
	String[] arrayOfString = new String[1]; 
	
		arrayOfString[0] ="";
		repo = arrayOfString;
	}
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.tourism);
        setTitleFromActivityLabel(R.id.title_text);
        
        
        parameter = getIntent().getExtras().getString("link");
       
        
        uriBuilder = new StringBuilder(URL);
        uriBuilder.append(parameter);
        
        final ActionItem web	= new ActionItem(WEBSITE, ":More", getResources().getDrawable(R.drawable.website));
   	    final ActionItem emails	= new ActionItem(EMAIL, "Email", getResources().getDrawable(R.drawable.email_logo));
        final ActionItem tphn   = new ActionItem(PHONE, "Call", getResources().getDrawable(R.drawable.phone));
        
        website     = new ActionItem(WEBSITE, ":More", getResources().getDrawable(R.drawable.website));
        emailmessage	= new ActionItem(EMAIL, "Email", getResources().getDrawable(R.drawable.email_logo));
        telephone	= new ActionItem(PHONE, "Call", getResources().getDrawable(R.drawable.phone));
        
        String[] arrayOfString = repo;
        ArrayAdapter<String> adapter_coder = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,arrayOfString);
        AutoCompleteTextView acTextView = (AutoCompleteTextView)findViewById(R.id.query);    
        acTextView.setThreshold(2);
        acTextView.setAdapter(adapter_coder);
        
        ArrayList<HashMap<String, String>> resortslist = new ArrayList<HashMap<String, String>>(); 
        
        
        JSONfunctions.getJSONfromURL(uriBuilder.toString(), new JSONCallback() {

	        @Override
	        public void onResult(JSONObject result) {
	        	Mjson =result;
	        }
	    });
     
        try{
        	
        	JSONArray  mot = Mjson.getJSONArray("PAYLOAD");
	        for(int i=0;i<mot.length();i++){						
	        	HashMap<String, String> map = new HashMap<String, String>();		
				JSONObject e = mot.getJSONObject(i);
				map.put(KEY_ID, e.getString(KEY_ID));
				map.put(KEY_NAME, e.getString(KEY_NAME));
				map.put(KEY_DESCRIPTION, e.getString(KEY_DESCRIPTION));
				map.put(KEY_LOCATION, e.getString(KEY_LOCATION));
				map.put(KEY_REF, e.getString(KEY_REF));
				map.put(KEY_PHONE, e.getString(KEY_PHONE));
				map.put(KEY_EMAIL, e.getString(KEY_EMAIL));
				map.put(KEY_WEBSITE, e.getString(KEY_WEBSITE));
				map.put(KEY_IMAGE_URL, e.getString(KEY_IMAGE_URL));
				resortslist.add(map);
			}		
        }catch(JSONException e)      {
        	 Log.e("log_tag", "Error kupitisha data "+e.toString());
        }
        list=(ListView)findViewById(R.id.list_m);
        adapter=new LazyAdapter_tourism_display(this, resortslist);
        list.setAdapter(adapter);
        list.setTextFilterEnabled(true);
        list.setOnItemClickListener(new OnItemClickListener() {
        	public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        		
        		quickAction_list = new QuickAction(LocationsDisplay.this, QuickAction.HORIZONTAL);
        		quickAction_list.addActionItem(web);
        		quickAction_list.addActionItem(emails);
        		quickAction_list.addActionItem(tphn);
           	    
           	    quickAction_list.show(view);
        		
        		quickAction_list.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {			

        			public void onItemClick(QuickAction source, int pos, int actionId) {				
        				actionItem = quickAction_list.getActionItem(pos);
        				
        				if (actionId == WEBSITE) {
        					HashMap<String, String> o = (HashMap<String, String>) list.getAdapter().getItem(position);
        					Intent i =new Intent(LocationsDisplay.this,ExploreKenyaWebView.class);
    						String site =o.get(KEY_WEBSITE);
    						i.putExtra("link", site);
        					startActivity(i);
        					
        				}
        				if (actionId == PHONE) {        					
        					HashMap<String, String> o = (HashMap<String, String>) list.getAdapter().getItem(position);
    						String PHONENUMBER =o.get(KEY_PHONE);
    						String number ="tel:"+PHONENUMBER.toString().trim();
    						Intent call = new Intent (Intent.ACTION_DIAL,Uri.parse(number));
    		        		startActivity(call);	
        						
        				} else if (actionId == EMAIL) {
        					HashMap<String, String> o = (HashMap<String, String>) list.getAdapter().getItem(position);
    						String EventEmail =o.get(KEY_EMAIL);
    						Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
    		            	String aEmailList[] = { EventEmail};
    		            	emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, aEmailList);
    		            	emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "ExploreKenya User Request");
    		            	emailIntent.setType("plain/text");  
    		            	startActivity(Intent.createChooser(emailIntent, "Select Email Client:"));
        					
        				} 
        				else {
        				}
        			}
        		});
        		quickAction_list.setOnDismissListener(new QuickAction.OnDismissListener() {			
        			@Override
        			public void onDismiss() {}});        			
        		mp = MediaPlayer.create(getApplicationContext(), R.raw.pling); 
                mp.start();
        	}
		});
        
        
    }
    public void onClickSearch (View v)
	{

		LinearLayout search1 = (LinearLayout)findViewById(R.id.Search);
		LinearLayout top = (LinearLayout)findViewById(R.id.top);
	      
	    	  search1.setVisibility(View.VISIBLE);
	    	  top.setVisibility(View.GONE);   
	}
    public void onClickHome (View v)
	{
        this.finish();
	}
    
    public void onClickfind(View v)
	{
		final AutoCompleteTextView acTextView = (AutoCompleteTextView)findViewById(R.id.query);
		q_search =acTextView.getText().toString();
		if (acTextView.getText().toString().equals(""))
		{
			acTextView.setError(getString(R.string.empty_field));
		
			
		}
		else
		{
			LinearLayout search1 = (LinearLayout)findViewById(R.id.Search);
			LinearLayout top = (LinearLayout)findViewById(R.id.top);
			search1.setVisibility(View.GONE);
	    	top.setVisibility(View.VISIBLE);
	    	
			new fetch(){ 
				public void onPostExecute(JSONObject jArray) {
					if(jArray!=null){
						 acTextView.setText("");
						  this.progressDialog.dismiss();
						  ArrayList<HashMap<String, String>> searchist = new ArrayList<HashMap<String, String>>();   
						  json=jArray;
						  
				        try{
				        	
				           JSONArray  mot = json.getJSONArray("PAYLOAD");
				            
					        for(int i=0;i<mot.length();i++){						
					        	HashMap<String, String> map = new HashMap<String, String>();	
								JSONObject e = mot.getJSONObject(i);
								map.put(KEY_ID, e.getString(KEY_ID));
								map.put(KEY_NAME, e.getString(KEY_NAME));
								map.put(KEY_DESCRIPTION, e.getString(KEY_DESCRIPTION));
								map.put(KEY_LOCATION, e.getString(KEY_LOCATION));
								map.put(KEY_REF, e.getString(KEY_REF));
								map.put(KEY_PHONE, e.getString(KEY_PHONE));
								map.put(KEY_EMAIL, e.getString(KEY_EMAIL));
								map.put(KEY_WEBSITE, e.getString(KEY_WEBSITE));
								map.put(KEY_IMAGE_URL, e.getString(KEY_IMAGE_URL));
								searchist.add(map);
					        	
					        				
							}		
				        }catch(JSONException e)      {
				        	 Log.e("log_tag", "Error kupitisha data "+e.toString());
				        }
				        list=(ListView)findViewById(R.id.list_m);
				        adapter=new LazyAdapter_tourism_display(LocationsDisplay.this, searchist);
				        list.setAdapter(adapter);
				        list.setTextFilterEnabled(true);
				        list.setOnItemClickListener(new OnItemClickListener() {
				        	public void onItemClick(AdapterView<?> parent, View view, final int position, long id) { 
				        		
				        		quickAction_list_search = new QuickAction(LocationsDisplay.this, QuickAction.HORIZONTAL);
				        		
				        		quickAction_list_search.addActionItem(website);
				        		quickAction_list_search.addActionItem(emailmessage);
				        		quickAction_list_search.addActionItem(telephone);
				        		
				        		quickAction_list_search.show(view);
				        		quickAction_list_search.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {			
				        			public void onItemClick(QuickAction source, int pos, int actionId) {				
				        				ActionItem actionItem = quickAction_list_search.getActionItem(pos);
				        				if (actionId == WEBSITE) {
				        					HashMap<String, String> o = (HashMap<String, String>) list.getAdapter().getItem(position);
				        					Intent i =new Intent(LocationsDisplay.this,ExploreKenyaWebView.class);
				    						String site =o.get(KEY_WEBSITE);
				    						i.putExtra("link", site);
				        					startActivity(i);
				        					
				        				}
				        				if (actionId == PHONE) {        					
				        					HashMap<String, String> o = (HashMap<String, String>) list.getAdapter().getItem(position);
				    						String PHONENUMBER =o.get(KEY_PHONE);
				    						String number ="tel:"+PHONENUMBER.toString().trim();
				    						Intent call = new Intent (Intent.ACTION_DIAL,Uri.parse(number));
				    		        		startActivity(call);	
				        						
				        				} else if (actionId == EMAIL) {
				        					HashMap<String, String> o = (HashMap<String, String>) list.getAdapter().getItem(position);
				    						String EventEmail =o.get(KEY_EMAIL);
				    						Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
				    		            	String aEmailList[] = { EventEmail};
				    		            	emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, aEmailList);
				    		            	emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "ExploreKenya User Request");
				    		            	emailIntent.setType("plain/text");  
				    		            	startActivity(Intent.createChooser(emailIntent, "Select Email Client:"));
				        					
				        				} 
				        				
				        				else {
				        				}
				        			}
				        		});
				        		quickAction_list_search.setOnDismissListener(new QuickAction.OnDismissListener() {			
				        			@Override
				        			public void onDismiss() {
				        			}});
				        		mp = MediaPlayer.create(getApplicationContext(), R.raw.pling); 
				                mp.start();
							}
						});
				        
				    
				       
					}
					else
					{
					this.progressDialog.dismiss();
					new AlertDialog.Builder(LocationsDisplay.this)
			        .setTitle(R.string.notification_prompt)
			        .setMessage(R.string.notification_msg_search)
			        .setIcon(R.drawable.kenya)
			        .setNeutralButton(R.string.ok_btn,
			                new DialogInterface.OnClickListener() {
			                    @Override
			                    public void onClick(DialogInterface dialog, int which) {
			                    }
			                }).show();
					}
				}
			}
			.execute();
		}
	}
    public class fetch extends
    AsyncTask<String, Integer, JSONObject> {
		protected ProgressDialog progressDialog = new ProgressDialog(LocationsDisplay.this);
	
        protected void onPreExecute() {
		
        	progressDialog.setMessage("Downloading data....");
	         progressDialog.show();
	         
    		    }
        @Override
		protected void onProgressUpdate(final Integer... progress) {
			if(fetch.this.isCancelled())
			{
			Toast.makeText(LocationsDisplay.this, "Network Error.....Try Again Later!", Toast.LENGTH_LONG).show();	
			}
		}

		@Override
		protected JSONObject doInBackground(String... params) {
			
			InputStream is = null;
			String result = "";
			JSONObject jArray = null;
			ArrayList<NameValuePair> namevaluepairs;
		    try{
		            HttpClient httpclient = new DefaultHttpClient();
		            HttpPost httppost = new HttpPost(SEARCH_URL);
		            namevaluepairs = new ArrayList<NameValuePair>(1);
		            namevaluepairs.add (new BasicNameValuePair("table",parameter));
					namevaluepairs.add (new BasicNameValuePair("q",q_search));
					httppost.setEntity (new UrlEncodedFormEntity(namevaluepairs));
		            HttpResponse response = httpclient.execute(httppost);
		            HttpEntity entity = response.getEntity();
		            is = entity.getContent();
		            
		            

		       }catch(Exception e){
		            Log.e("log_tag", "Error in http connection "+e.toString());
		      }
		 
		      try{
		            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
		            StringBuilder sb = new StringBuilder();
		            String line = null;
		            while ((line = reader.readLine()) != null) {
		                    sb.append(line + "\n");
		            }
		            is.close();
		            result=sb.toString();
		      }
		      catch(Exception e){
		            Log.e("log_tag", "Error converting result "+e.toString());
		       }
              try{
		    	
	            jArray = new JSONObject(result);            
		      }
               catch(JSONException e){
		            Log.e("log_tag", "Error parsing data "+e.toString());
		       }
               if (isCancelled())
   			{
   				return null;
   			}
			  return jArray;
		  }
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
    public void onClickMap (View v)
	{
    	if(parameter.equals("tbl_nairobi"))
    	{
    		Intent i = new Intent(LocationsDisplay.this, MapLocations.class);
        	i.putExtra("link", parameter);
            startActivity(i);	
    	}
    	if(parameter.equals("tbl_mombasa"))
    	{
    		Intent i = new Intent(LocationsDisplay.this, MapLocationsMombasa.class);
        	i.putExtra("link", parameter);
            startActivity(i);	
    	}
    	if(parameter.equals("tbl_malindi"))
    	{
    		Intent i = new Intent(LocationsDisplay.this, MapLocationsMalindi.class);
        	i.putExtra("link", parameter);
            startActivity(i);	
    	}
    	if(parameter.equals("tbl_kisumu"))
    	{
    		Intent i = new Intent(LocationsDisplay.this, MapLocationsKisumu.class);
        	i.putExtra("link", parameter);
            startActivity(i);	
    	}
    	if(parameter.equals("tbl_nakuru"))
    	{
    		Intent i = new Intent(LocationsDisplay.this, MapLocationsNakuru.class);
        	i.putExtra("link", parameter);
            startActivity(i);	
    	}
    	if(parameter.equals("tbl_nanyuki"))
    	{
    		Intent i = new Intent(LocationsDisplay.this, MapLocationsNanyuki.class);
        	i.putExtra("link", parameter);
            startActivity(i);	
    	}
    	
	}
	public void onClicklogo (View v)
	{
		Toast.makeText(LocationsDisplay.this, "ExploreKenya", Toast.LENGTH_LONG).show();
	}
    @Override
    public void onBackPressed() {

    	this.finish();
    }
    
}

