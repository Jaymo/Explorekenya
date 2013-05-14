package com.JamuhuriTech.ExploreKenya.functions;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class JSONfunctions {
	    static InputStream is = null;
		static String res = "";
	    static JSONObject jArray = null;
	    
   public static interface JSONCallback {
	        public void onResult(JSONObject result);
	    }
	    
    public static void getJSONfromURL(final String url,final JSONCallback callback){
    	
    	 
        new AsyncTask<String, Void, JSONObject>() {
        	
            @Override
            protected JSONObject doInBackground(String... params) {
                
	    try{
	            HttpClient httpclient = new DefaultHttpClient();
	            HttpPost httppost = new HttpPost(url);
	            HttpResponse response = httpclient.execute(httppost);
	            HttpEntity entity = response.getEntity();
	            is = entity.getContent();
	            
	            

	    }
	    catch(Exception e){
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
	            res=sb.toString();
	    }
	    catch(Exception e){
	            Log.e("log_tag", "Error converting result "+e.toString());
	    }
	    Log.i("Result BG ", res);
	    try{
        	
            jArray = new JSONObject(res);            
   	    }catch(JSONException e){
   	            Log.e("log_tag", "Error parsing data "+e.toString());
   	    }
   	    callback.onResult(jArray);
   	    
	    return jArray;
	   
         }
          @Override
          public void onPostExecute(JSONObject jArray) {
        	  Log.e("OnPost Execute", "............. ");	
         }
      }.execute(); 
      
     }
}