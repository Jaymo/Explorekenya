package com.JamuhuriTech.ExploreKenya;

/**
 * @Author Mwenda Wahome
 * @Web http://www.jamuhuritech.com
 */


import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

import com.JamuhuriTech.ExploreKenya.util.pref;

public class ExploreKenyaHome extends TabActivity {
	
	private TabHost tabHost;
    private TextView activityTitle;
    public static Typeface tf;
 
  
    
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        
        if (pref.AutoFetch==true) {
        startService(new Intent(ExploreKenyaHome.this, ExploreKenyaService.class));
        //Toast.makeText(ExploreKenyaHome.this, " Events Service Started", Toast.LENGTH_LONG).show();
        }
        else if (pref.AutoFetch==false){
        	stopService(new Intent(ExploreKenyaHome.this, ExploreKenyaService.class));
        //Toast.makeText(ExploreKenyaHome.this, "Events Service was stopped", Toast.LENGTH_LONG).show();
        }
    
        activityTitle = (TextView)findViewById(R.id.title_text);
        tf = Typeface.createFromAsset(getAssets(),"data/fonts/BROKEN_GHOST.ttf");
        activityTitle.setTextSize(24);
        activityTitle.setTypeface(tf);
        
        tabHost = getTabHost();
        PopulateTab();
        tabHost.setCurrentTab(0);
        setTabColor(tabHost);
        tabHost.setOnTabChangedListener(new OnTabChangeListener() {

            public void onTabChanged(String arg0) {
                setTabColor(tabHost);
            }

        });

        
        
	}
	public void settings (View v)
    {
		Intent intent = new Intent(ExploreKenyaHome.this, ExploreKenyaSettings.class);
        startActivity(intent);
    }
	
		
	public static void setTabColor(TabHost tabhost) {
        for (int i = 0; i < tabhost.getTabWidget().getChildCount(); i++) {
            // unselected
            tabhost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#606060"));

            TextView tv = (TextView)tabhost.getTabWidget().getChildAt(i)
                    .findViewById(android.R.id.title); // Unselected Tabs
            tv.setTextColor(Color.parseColor("#ffffff"));

        }
        
        // selected
        tabhost.getTabWidget().getChildAt(tabhost.getCurrentTab())
        
                .setBackgroundColor(Color.parseColor("#3D3D3D"));
        TextView tv = (TextView)tabhost.getCurrentTabView().findViewById(android.R.id.title);
        tv.setTextColor(Color.parseColor("#ffffff"));
    }
	public void PopulateTab() {
		if (activityTitle != null)
            activityTitle.setText(getTitle());
        //Locations
        tabHost.addTab(tabHost
                .newTabSpec("Locations")
                .setIndicator(getString(R.string.tab_cities),
                        getResources().getDrawable(R.drawable.locations))
                .setContent(new Intent(ExploreKenyaHome.this, ExploreKenyaLocations.class)));

        // Tourism
        tabHost.addTab(tabHost
                .newTabSpec("Tourism")
                .setIndicator(getString(R.string.tab_tourism),
                        getResources().getDrawable(R.drawable.tourism))
                .setContent(new Intent(ExploreKenyaHome.this, ExploreKenyaTourism.class)));
     // Culture
        tabHost.addTab(tabHost
                .newTabSpec("Culture")
                .setIndicator(getString(R.string.tab_culture),
                        getResources().getDrawable(R.drawable.culture))
                .setContent(new Intent(ExploreKenyaHome.this, ExploreKenyaCulture.class)));
     // Events
        tabHost.addTab(tabHost
                .newTabSpec("Events")
                .setIndicator(getString(R.string.tab_events),
                        getResources().getDrawable(R.drawable.events))
                .setContent(new Intent(ExploreKenyaHome.this, ExploreKenyaEvents.class)));
     
		
    }
		
	}

