package com.miracle.webmobile;

import android.app.Application;

import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.onesignal.OneSignal;

public class MyApplication extends Application {

    public static String ONESIGNAL_APP_ID = "########-####-####-####-############";

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
        FirebaseApp.initializeApp(this);
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);
        OneSignal.promptForPushNotifications();
    }
}
