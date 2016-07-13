package id.dekz.code.firebaseexample;

import android.app.Application;

import com.google.firebase.analytics.FirebaseAnalytics;


public class FirebaseExample extends Application {

    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    public void onCreate(){
        super.onCreate();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
    }
}
