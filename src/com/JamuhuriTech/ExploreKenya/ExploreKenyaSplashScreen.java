package com.JamuhuriTech.ExploreKenya;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;

import com.JamuhuriTech.ExploreKenya.util.Util;

public class ExploreKenyaSplashScreen extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
        
        SplashHandler handle =new SplashHandler();
        
        setContentView(R.layout.splashscreen);
        Message msg =new Message();
        msg.what=0;
        handle.sendMessageDelayed (msg,2500);
        
    }
 private class SplashHandler extends Handler {
	public void handleMessage(Message msg)
	{
		switch (msg.what)
		{
		default:
		case 0:
			super.handleMessage (msg);
			Intent splashintent =new Intent();
			splashintent.setClass(ExploreKenyaSplashScreen.this, ExploreKenyaHome.class);
		    if (Util.isConnected(ExploreKenyaSplashScreen.this)) {
		     startActivity(splashintent);
		     ExploreKenyaSplashScreen.this.finish();
		}
		    else{
		    	AlertDialog.Builder clearBuilder = new AlertDialog.Builder(ExploreKenyaSplashScreen.this);
                clearBuilder.setMessage(getString(R.string.notification_msg))
                        .setCancelable(false)
                        .setPositiveButton(getString(R.string.exit_btn),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                    	 ExploreKenyaSplashScreen.this.finish();
                                        
                                    }
                                })
                        .setNegativeButton(getString(R.string.settings_btn),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                    	ExploreKenyaSplashScreen.this.finish();
                                    	startActivityForResult(
                                         new Intent(android.provider.Settings.ACTION_SETTINGS),0); 
                                    	
                                    }
                                
                                });
                AlertDialog clearDialog = clearBuilder.create();
                clearDialog.show();
		    	
		    }
		
		
		}
	}
    }	

}
