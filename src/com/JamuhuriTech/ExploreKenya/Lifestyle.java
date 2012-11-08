package com.JamuhuriTech.ExploreKenya;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class Lifestyle  extends Activity {
	public static Typeface tf;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.lifestyle);
        setTitleFromActivityLabel (R.id.title_text);
        
        
        
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
		Intent intent = new Intent(Lifestyle.this, ExploreKenyaShare.class);
        startActivity(intent);
	}
	@Override
    public void onBackPressed() {

    	this.finish();
    }

}
