package id.dekz.code.firebaseexample.util;


import android.content.Context;
import android.content.SharedPreferences;

public class Session {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;

    private static final String PREF_NAME = "FirebaseExamplePref";
    private static final String KEY_EMAIL = "email";

    private static final String KEY_ISLOGGEDIN = "isLoggedIn";

    public Session(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, 0);
        editor = sharedPreferences.edit();
    }

    public String getEmailSession(){
        return sharedPreferences.getString(KEY_EMAIL,"unknown");
    }

    public void saveLoginSession(String email){
        editor.putString(KEY_EMAIL,email);
        editor.putBoolean(KEY_ISLOGGEDIN,true);
        editor.commit();
    }

    public boolean isLoggedIn(){
        return sharedPreferences.getBoolean(KEY_ISLOGGEDIN,false);
    }

    public void logoutSession(){
        editor.putString(KEY_EMAIL,"");
        editor.putBoolean(KEY_ISLOGGEDIN,false);
        editor.commit();
    }
}
