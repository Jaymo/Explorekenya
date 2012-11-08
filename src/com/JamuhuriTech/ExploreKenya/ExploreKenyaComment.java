package com.JamuhuriTech.ExploreKenya;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.JamuhuriTech.ExploreKenya.util.Util;

/**
 * @Author Mwenda Wahome
 * @Web http://www.jamuhuritech.com
 */


public class ExploreKenyaComment extends Activity {
	private EditText name,comment;
	private Button ok;
	String user="",comm="",result,phone;
	HttpPost httpPost;
	StringBuffer buffer;
	HttpResponse response;
	HttpClient httpclient;
	InputStream inputstream;
	ArrayList<NameValuePair> namevaluepairs;
	private String mErrorMessage = "";
	private boolean mError = false;
	public static Typeface tf;
	
	
	public class PostTask extends
    AsyncTask<Void,  Void,Integer> {
		protected Integer stat;
        int myProgress;
  private ProgressDialog progressDialog = new ProgressDialog(ExploreKenyaComment.this);
  @Override
  protected void onPreExecute() {
	  progressDialog.setMessage("Submitting comment....");
      progressDialog.show();
	  TelephonyManager mTelephonyMgr;  
	  mTelephonyMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);  
	  phone = mTelephonyMgr.getLine1Number(); 
	  
  }
  
  @Override
  protected void onPostExecute(Integer result) {
	if (result == 1) {
   startActivity (new Intent(getApplicationContext(), ExploreKenyaHome.class));
   this.progressDialog.dismiss();
   ExploreKenyaComment.this.finish();
   Toast.makeText(ExploreKenyaComment.this, "Comment sucessfully Posted", Toast.LENGTH_LONG).show();
  }
	else if (result == 2) {
		this.progressDialog.dismiss();
		Toast.makeText(ExploreKenyaComment.this, "No internet Connection Detected", Toast.LENGTH_LONG).show();
	}
  }

  

  @Override
  protected Integer doInBackground(Void... params) {
	  if (Util.isConnected(ExploreKenyaComment.this)) {
	  try{
			httpclient =new DefaultHttpClient();
			httpPost = new HttpPost("http://10.0.2.2/android/api_comment.php");
			namevaluepairs = new ArrayList<NameValuePair>(3);
			namevaluepairs.add (new BasicNameValuePair("name",user));
			namevaluepairs.add (new BasicNameValuePair("comment",comm));
			namevaluepairs.add (new BasicNameValuePair("phone",phone));
			httpPost.setEntity (new UrlEncodedFormEntity(namevaluepairs));
			response = httpclient.execute(httpPost);
			inputstream=response.getEntity().getContent();
			inputstream.close();
			
		 }
				
			
			catch (Exception e)
			{
				Log.e("log_tag", "Error converting result "+e.toString());
			}
		   stat = 1;//sent to online DB
	  }
	    else
		{
			stat =2;//Could not submit online
		}
	  
   return stat;
  }

  

 }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.comment);
        setTitleFromActivityLabel(R.id.title_text);
        name=(EditText)findViewById(R.id.name);
        comment=(EditText)findViewById(R.id.comment);
        ok =(Button) findViewById (R.id.ok);
        
        
        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (TextUtils.isEmpty(name.getText())) {
                        name.setError(getString(R.string.empty_name));
                    }
                }

            }

        });
        comment.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (TextUtils.isEmpty(comment.getText())) {
                        comment.setError(getString(R.string.empty_comment));
                    }
                }

            }

        });
        
        
         ok.setOnClickListener(new View.OnClickListener(){

	@Override
	public void onClick(View v) {
		user =name.getText().toString();
		comm =comment.getText().toString();
		mError = false;
    	
        if (TextUtils.isEmpty(name.getText())) {
            mErrorMessage = getString(R.string.empty_name)+"\n";
            mError = true;
        }
        if (TextUtils.isEmpty(comment.getText())) {
            mErrorMessage += getString(R.string.empty_comment)+"\n";
            mError = true;
        }
        if (!mError) {
        	 new PostTask().execute();
 		     ok.setClickable(false);
        }
        else {
            final Toast t = Toast.makeText(ExploreKenyaComment.this, "Error!\n" + mErrorMessage,
                    Toast.LENGTH_LONG);
            t.show();
            mErrorMessage = "";
          
        }
	} 
 });
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
    @Override
    public void onBackPressed() {

    	this.finish();
    }
    public void onClickHome (View v)
	{
    	this.finish();
	}
}
   
   
    
    
    
    
    
