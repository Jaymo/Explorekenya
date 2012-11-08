
package com.JamuhuriTech.ExploreKenya;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Jaymo
 *
 * @website http://www.jamuhuritech.com
 *
 */
public class ExploreKenyaWebView  extends Activity{
	private WebView webView;
	private String parameter;
	public static Typeface tf;
	private ProgressDialog progressBar;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.webview);
		setTitleFromActivityLabel(R.id.title_text);
		parameter = getIntent().getExtras().getString("link");
 
		webView = (WebView) findViewById(R.id.webView1);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setLoadWithOverviewMode(true);
		webView.getSettings().setUseWideViewPort(true);
	
		progressBar = ProgressDialog.show(ExploreKenyaWebView.this, "", "Loading...");
		webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
            	view.loadUrl(url);
                return true;
            }
            public void onPageFinished(WebView view, String url) {
                if (progressBar.isShowing()) {
                    progressBar.dismiss();
                    Toast.makeText(ExploreKenyaWebView.this, "Double Tap Screen to Zoom.", Toast.LENGTH_LONG).show();
                }
            }
            
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                if (progressBar.isShowing()) {
                    progressBar.dismiss();
                }
                Toast.makeText(ExploreKenyaWebView.this, "Oh no! " + description, Toast.LENGTH_LONG).show();
            }
        });
		webView.loadUrl(parameter);
	}
	public void onClickHome (View v)
	{
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
	public void onClickShare (View v)
	{
		Intent intent = new Intent(ExploreKenyaWebView .this, ExploreKenyaShare.class);
        startActivity(intent);
	}
	@Override
    public void onBackPressed() {

    	this.finish();
    }

}
