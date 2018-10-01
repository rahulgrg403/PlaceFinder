package prakash.registration;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
checkConnection();
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
               startActivity(new Intent(splash.this,login.class));
               finish();
            }
        },4000);
    }
    protected boolean isOnline()
    {
        ConnectivityManager cm=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo =cm.getActiveNetworkInfo();
        if(netinfo!=null && netinfo.isConnectedOrConnecting())
        {
            return true;
        }
        else
            return false;
    }

    public void checkConnection()
    {
        if(isOnline())
        {
            Toast.makeText(this,"You Are Online ... ! ",Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(this,"You Are Offline ... ! ",Toast.LENGTH_LONG).show();
        }
    }
}
