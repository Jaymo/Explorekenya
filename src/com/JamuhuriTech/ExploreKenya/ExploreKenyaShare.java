package com.JamuhuriTech.ExploreKenya;

import impl.android.com.twitterapime.xauth.ui.WebViewOAuthDialogWrapper;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.twitterapime.rest.Credential;
import com.twitterapime.rest.TweetER;
import com.twitterapime.rest.UserAccountManager;
import com.twitterapime.search.Tweet;
import com.twitterapime.xauth.Token;
import com.twitterapime.xauth.ui.OAuthDialogListener;

public class ExploreKenyaShare extends Activity implements OnClickListener, OAuthDialogListener {
	Facebook mfacebook = new Facebook("188092017988896");
	Button fb,Tw;
	String response;
	String FILENAME = "AndroidSSO_data";
    private SharedPreferences mPrefs;
    String access_token;
    String CONSUMER_KEY;
	
	
	  String CONSUMER_SECRET;

	
	  String CALLBACK_URL;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.share);
		fb = (Button)findViewById(R.id.facebook);
		Tw = (Button)findViewById(R.id.twitter);
		Tw.setOnClickListener(this);
		fb.setOnClickListener(this);
		mPrefs = getPreferences(MODE_PRIVATE);
		access_token = mPrefs.getString("access_token", null);
		
		
		
		
		
		
		
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		mfacebook.authorizeCallback(requestCode, resultCode, data);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.facebook:
			
			long expires = mPrefs.getLong("access_expires", 0);
			if( access_token !=null){
				mfacebook.setAccessToken(access_token);
			}
			if(expires !=0)
			{
				mfacebook.setAccessExpires(expires);
			}
			if(!mfacebook.isSessionValid()){
			mfacebook.authorize(ExploreKenyaShare.this,new String[]{"publish_stream","publish_checkins"}, new DialogListener() {
				
				
				@Override
				public void onFacebookError(FacebookError e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onError(DialogError e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onComplete(Bundle values) {
					SharedPreferences.Editor editor = mPrefs.edit();
					editor.putString("access_token",
							mfacebook.getAccessToken());
					editor.putLong("access_expires",
							mfacebook.getAccessExpires());
					editor.commit();					
				}
				
				@Override
				public void onCancel() {
					
					
				}
			});
			}
		
			try {
				response = mfacebook.request("me");
				Bundle upDets = new Bundle();
				upDets.putString("message", "Tour Kenya using ExploreKenya for Android.Available on Google Play http://tinyurl.com/a7qqzz4 and Samsung App Store");
				upDets.putString(Facebook.TOKEN, access_token);
				response = mfacebook.request("me/feed", upDets, "POST");
				Toast.makeText(ExploreKenyaShare.this, "Susessfully Shared on your TimeLine.", Toast.LENGTH_LONG).show();
				this.finish();
			} catch (Exception e) {
				
				
			} 
			
			
			break;
			
		case R.id.twitter:
			
		 	CONSUMER_KEY = "vn7B04vb7dErksuL03QE0w";
			
			
			CONSUMER_SECRET = "bawSuk5BHWsy2qMIfg10iKz03CHwM0zzg04ZaxZf2sc";

			
			CALLBACK_URL = "http://www.twitter.com";
			 
		        WebView webView = new WebView(ExploreKenyaShare.this);
		        setContentView(webView);
		        webView.requestFocus(View.FOCUS_DOWN);
		    
		        WebViewOAuthDialogWrapper pageWrapper =
		        	new WebViewOAuthDialogWrapper(webView);
	
				pageWrapper.setConsumerKey(CONSUMER_KEY);
				pageWrapper.setConsumerSecret(CONSUMER_SECRET);
				pageWrapper.setCallbackUrl(CALLBACK_URL);
				pageWrapper.setOAuthListener(this);
	
				pageWrapper.login();
			  
			break;
			
		}
		
	}

	@Override
	protected void onResume() {
		 super.onResume();
	     mfacebook.extendAccessTokenIfNeeded(ExploreKenyaShare.this, null);
	}

	@Override
	public void onAccessDenied(String message) {
		showMessage(message);
	}

	@Override
	public void onAuthorize(Token accessToken) {
		
		Credential c = new Credential(CONSUMER_KEY, CONSUMER_SECRET, accessToken);
		UserAccountManager uam = UserAccountManager.getInstance(c);
	
		try {
			if (uam.verifyCredential()) {
				TweetER.getInstance(uam).post(new Tweet("Tour Kenya using ExploreKenya for Android.Available on Google Play http://tinyurl.com/a7qqzz4 and Samsung App Store." ));
			
				showMessage("Susessfully Shared on your TimeLine.");
				
			}
		} catch (Exception e) {
			showMessage(e.toString());
		}
		
	}

	@Override
	public void onFail(String error, String message) {

		showMessage("Something went wrong");
	}
	
	private void showMessage(String msg) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(msg).setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
						ExploreKenyaShare.this.finish();
					}
				});
	
		builder.create().show();
	}

}
