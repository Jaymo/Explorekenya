package com.JamuhuriTech.ExploreKenya;

import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.JamuhuriTech.ExploreKenya.functions.JSONfunctions;
import com.JamuhuriTech.ExploreKenya.util.Util;
import com.JamuhuriTech.ExploreKenya.util.pref;



public class ExploreKenyaService  extends Service {

    public static int update = 0;
   
    public static String URL="http://akajaymo.kodingen.com/api_update.php";
    
    
    private TimerTask mDoTask;

    private Timer mT = new Timer();

    private Handler handler = new Handler();

    private Context mContext;

    private static final String TAG = "Explore Kenya Notification";
    
    private static final String PROMPT ="New Events are available.";
    
    public static boolean vibrate = true;

    public static boolean flashLed = true;

    public static final int NOTIFICATION_ID = 1;

    public static final String PREFS_NAME = "ExploreKenyaService";
    
    private Notification ExploreKenyaReportNotification;

    private NotificationManager mNotificationManager;
    
    @Override
    public void onCreate() {

        mContext = getApplicationContext();

        pref.loadSettings(mContext);

        mNotificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        if (Util.isConnected(ExploreKenyaService.this)) { 
        this.startService();
        }

    } 

private void startService() {
	pref.loadSettings(mContext);
    long delay = 60000;
    long period = 60000;
    
  if (pref.AutoFetch) {
   
    mDoTask = new TimerTask() {
        @Override
        public void run() {
            handler.post(new Runnable() {
                  
                public void run() {  
                
                	JSONObject json = JSONfunctions.getJSONfromURL(URL);
                	try{
                    	JSONArray  updates = json.getJSONArray("UPDATES");
                    	for(int i=0;i<1;i++){
            				JSONObject e = updates.getJSONObject(i);
            				update= e.getInt("MAX(id)");
            				
            	        	
                    	}
                	}catch(JSONException e)      {
                   	 Log.e("log_tag", "Error kupitisha data "+e.toString());
                   }
                	pref.loadSettings(mContext);
                	if(update>pref.events){
                		showNotification(PROMPT);
                		
                	}
                	
                	
                	
                }

            });
        }

    };
    mT.scheduleAtFixedRate(mDoTask, delay, period);
}
    
}


@Override
public IBinder onBind(Intent intent) {
	return null;
}

private void stopService() {
    if (mDoTask != null) {
        mDoTask.cancel();
        mT.cancel();
        mT.purge();
    }
}

@Override
public void onDestroy() {
    super.onDestroy();
    this.stopService();
}
private void showNotification(String tickerText) {

    Intent baseIntent = new Intent(this, ExploreKenyaEvents.class);
    baseIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    PendingIntent contentIntent = PendingIntent.getActivity(this, 0, baseIntent, 0);

    ExploreKenyaReportNotification = new Notification(R.drawable.events, tickerText,
            System.currentTimeMillis());
    ExploreKenyaReportNotification.contentIntent = contentIntent;
    ExploreKenyaReportNotification.flags = Notification.FLAG_AUTO_CANCEL;
    ExploreKenyaReportNotification.defaults = Notification.DEFAULT_ALL;
    ExploreKenyaReportNotification.setLatestEventInfo(this, TAG, tickerText, contentIntent);

    if (pref.vibrate) {
        double vibrateLength = 100 * Math.exp(0.53 * 20);
        long[] vibrate = new long[] {
                100, 100, (long)vibrateLength
        };
        ExploreKenyaReportNotification.vibrate = vibrate;

    if (pref.flashLed) {
            int color = Color.BLUE;
            ExploreKenyaReportNotification.ledARGB = color;
        }

        ExploreKenyaReportNotification.ledOffMS = (int)vibrateLength;
        ExploreKenyaReportNotification.ledOnMS = (int)vibrateLength;
        ExploreKenyaReportNotification.flags = ExploreKenyaReportNotification.flags
                | Notification.FLAG_SHOW_LIGHTS;
    }

    mNotificationManager.notify(pref.NOTIFICATION_ID, ExploreKenyaReportNotification);
}

  
    


}
