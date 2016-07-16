package id.dekz.code.firebaseexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.dekz.code.firebaseexample.activity.LoginActivity;
import id.dekz.code.firebaseexample.util.Session;

public class MainActivity extends AppCompatActivity {

    private Session session;
    @BindView(R.id.adView)AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-3940256099942544/6300978111");
        session = new Session(MainActivity.this);

    }

    @Override
    protected void onResume(){
        super.onResume();
        if(session.isLoggedIn()){
            getSupportActionBar().setSubtitle(session.getEmailSession());
            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice("36D3E65086487184A8FD1C7F00B1C77A")
                    .build();
            mAdView.loadAd(adRequest);
        }else{
            Intent login = new Intent(MainActivity.this, LoginActivity.class);
            login.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(login);
            MainActivity.this.finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_logout) {
            session.logoutSession();
            Intent login = new Intent(MainActivity.this, LoginActivity.class);
            login.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(login);
            MainActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        MainActivity.this.moveTaskToBack(true);
    }
}
