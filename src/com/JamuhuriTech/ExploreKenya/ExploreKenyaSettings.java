package com.JamuhuriTech.ExploreKenya;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;
import android.view.Window;
import android.widget.Toast;

import com.JamuhuriTech.ExploreKenya.util.pref;



public class ExploreKenyaSettings  extends PreferenceActivity implements OnSharedPreferenceChangeListener{
	
	private CheckBoxPreference autoFetchCheckBoxPref;

    private CheckBoxPreference vibrateCheckBoxPref;
    
    private CheckBoxPreference flashLedCheckBoxPref;
    
    public static final String AUTO_FETCH_PREFERENCE = "auto_fetch_preference";

    public static final String VIBRATE_PREFERENCE = "vibrate_preference";

    public static final String FLASH_LED_PREFERENCE = "flash_led_preference";
    
	protected void onCreate(Bundle savedInstanceState){
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		
		addPreferencesFromResource(R.xml.prefs);
		
         autoFetchCheckBoxPref = new CheckBoxPreference(ExploreKenyaSettings.this);
        
         vibrateCheckBoxPref = new CheckBoxPreference(ExploreKenyaSettings.this);

         flashLedCheckBoxPref = new CheckBoxPreference(ExploreKenyaSettings.this);
         
         setPreferenceScreen(createPreferenceHierarchy());

		
		
		
	}
	 private PreferenceScreen createPreferenceHierarchy() {

	        PreferenceScreen root = getPreferenceManager().createPreferenceScreen(this);

	        PreferenceCategory basicPrefCat = new PreferenceCategory(this);
	        basicPrefCat.setTitle(R.string.setting_title);
	        root.addPreference(basicPrefCat);
	        
	        autoFetchCheckBoxPref.setKey("auto_fetch_preference");
	        autoFetchCheckBoxPref.setTitle(R.string.chk_auto_fetch);
	        autoFetchCheckBoxPref.setSummary(R.string.hint_auto_fetch);
	        autoFetchCheckBoxPref.setChecked(true);
	     	basicPrefCat.addPreference(autoFetchCheckBoxPref);
	        
	        vibrateCheckBoxPref.setKey("vibrate_preference");
	        vibrateCheckBoxPref.setTitle(R.string.vibrate);
	        vibrateCheckBoxPref.setSummary(R.string.hint_vibrate);
	        vibrateCheckBoxPref.setChecked(true);
	        basicPrefCat.addPreference(vibrateCheckBoxPref);
	        
	        flashLedCheckBoxPref.setKey("flash_led_preference");
	        flashLedCheckBoxPref.setTitle(R.string.flash_led);
	        flashLedCheckBoxPref.setSummary(R.string.hint_flash_led);
	        flashLedCheckBoxPref.setChecked(true);
	        basicPrefCat.addPreference(flashLedCheckBoxPref);
	        
		return root;
	 }
	
	 @Override
	    protected void onResume() {
	        super.onResume();
	        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

	    }

	    @Override
	    protected void onPause() {
	        super.onPause();
	        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(
	                this);

	    }
	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,String key) {
			
		if (sharedPreferences.getBoolean(AUTO_FETCH_PREFERENCE, pref.AutoFetch)) {
			pref.AutoFetch = true;
			startService(new Intent(ExploreKenyaSettings.this, ExploreKenyaService.class));

        } else {
        	pref.AutoFetch = false;
        	stopService(new Intent(ExploreKenyaSettings.this, ExploreKenyaService.class));
        	
        	
        }
		
		
        if (sharedPreferences.getBoolean(VIBRATE_PREFERENCE, pref.AutoFetch)) {
        	pref.vibrate = true;
        } else {
        	pref.vibrate = false;
            
        }
 
        if (sharedPreferences.getBoolean(FLASH_LED_PREFERENCE, pref.AutoFetch)) {

        	pref.flashLed = true;
        } else {
        	pref.flashLed = false ;
        }
        
	}

	

}
