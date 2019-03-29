package com.progress.web;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.progress.adapter.RVAdapter;
import com.progress.classes.RestRead;
import com.progress.network.Network;

import static com.progress.web.MainActivity.endpoint;

public class Splash extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (!new Network().getConnectivityStatus(getBaseContext())) {
            Intent it = new Intent(getBaseContext(), NoInternetActivity.class);
            startActivity(it);
            finish();
        }else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent it = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(it);
                    finish();
                }
            }, 700);


        }

    }
}
