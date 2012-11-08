package com.JamuhuriTech.ExploreKenya;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.JamuhuriTech.ExploreKenya.util.Util;

public class Nakuru extends Activity {
	
	public static String KEY_WEBSITE="http://en.wikipedia.org/wiki/Nakuru";
	
	Button more,attractions,comment;

	private Intent a;
	
	public static Typeface tf;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.nakuru);
        setTitleFromActivityLabel (R.id.title_text);
        
        more = (Button)findViewById(R.id.more_button);
        attractions = (Button)findViewById(R.id.attractions_button);
        comment = (Button)findViewById(R.id.map_comment);
        
        more.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	if (Util.isConnected(Nakuru.this)) {
            		a = new Intent(Nakuru.this, ExploreKenyaWebView.class);
                	a.putExtra("link", KEY_WEBSITE);
    				startActivity(a);  
            	}
            	else{
              		 Toast.makeText(Nakuru.this, " No internet Connection Detected", Toast.LENGTH_LONG).show();
              	}
            	
            }
        });
        attractions.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	if (Util.isConnected(Nakuru.this)) {
            	Intent i =new Intent(Nakuru.this,LocationsDisplay.class);
            	i.putExtra("link", "tbl_nakuru");
				startActivity(i);
            	}
            	else{
              		 Toast.makeText(Nakuru.this, " No internet Connection Detected", Toast.LENGTH_LONG).show();
              	}
                
            }
        });
        comment.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	if (Util.isConnected(Nakuru.this)) {
            	Intent intent = new Intent(Nakuru.this, ExploreKenyaComment.class);
                startActivity(intent);
            	}
            	else{
           		 Toast.makeText(Nakuru.this, " No internet Connection Detected", Toast.LENGTH_LONG).show();
           	}
                
            }
        });
        
        
        
        
	}
	public void onClickHome (View v)
	{
    	this.finish();
	}
	@Override
    public void onBackPressed() {

    	this.finish();
    }
	public void onClickShare (View v)
	{
		if (Util.isConnected(Nakuru.this)) {
		Intent intent = new Intent(Nakuru.this, ExploreKenyaShare.class);
        startActivity(intent);
		}
		else{
      		 Toast.makeText(Nakuru.this, " No internet Connection Detected", Toast.LENGTH_LONG).show();
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

}
