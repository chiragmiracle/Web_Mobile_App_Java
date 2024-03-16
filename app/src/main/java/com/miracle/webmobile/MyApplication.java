package com.miracle.webmobile;

import android.app.Application;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.onesignal.OneSignal;

public class MyApplication extends Application {

    public static String ONESIGNAL_APP_ID = "########-####-####-####-############";
    public static String fcm_token;

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
        FirebaseApp.initializeApp(this);
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);
        OneSignal.promptForPushNotifications();
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(Task<String> task) {
                        if (!task.isSuccessful()) {
                            fcm_token = task.getException().getMessage();
                            Log.e("CHIRAG_FCM", "task.isSuccessful(): " + task.getException());
                            return;
                        }
                        fcm_token = task.getResult();
                        Log.e("CHIRAG_FCM", "FCM_token: " + fcm_token);
                    }
                });
    }
}
