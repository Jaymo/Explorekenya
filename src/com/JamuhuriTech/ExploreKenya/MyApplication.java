package com.JamuhuriTech.ExploreKenya;

import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

import android.app.Application;

@ReportsCrashes(formKey = "dGlJWWRfRUJxYmF2M1B4SmpaMTFPckE6MQ", 
                mode = ReportingInteractionMode.TOAST,
                forceCloseDialogAfterToast = false, // optional, default false
                resToastText = R.string.crash_toast_text)
public class MyApplication extends Application {
	@Override
    public void onCreate() {
        // The following line triggers the initialization of ACRA
        ACRA.init(this);
        super.onCreate();
    }

}
