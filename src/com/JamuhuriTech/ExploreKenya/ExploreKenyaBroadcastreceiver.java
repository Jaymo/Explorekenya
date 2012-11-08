package com.JamuhuriTech.ExploreKenya;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.JamuhuriTech.ExploreKenya.util.Util;


public class ExploreKenyaBroadcastreceiver  extends BroadcastReceiver{
	@Override
	public void onReceive(Context context, Intent intent) {
		if(Util.isConnected(context)){
		Intent startServiceIntent = new Intent(context, ExploreKenyaService.class);
        context.startService(startServiceIntent);
		}
        
		
	}

}
