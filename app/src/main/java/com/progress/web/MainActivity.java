package com.progress.web;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import com.progress.network.Network;

public class MainActivity extends AppCompatActivity {

    private FrameLayout frameLayout;
    private HomeFragment homeActivity;
    public static String endpoint;
    BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frameLayout = findViewById(R.id.fr_layout);

  //      endpoint = "http://192.168.43.137:8080";
//        endpoint = "http://192.168.0.102:8080";
      endpoint = "https://app-progress.herokuapp.com";

        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        homeActivity = new HomeFragment();
        fragmentManager(homeActivity);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    HomeFragment shomeActivity = new HomeFragment();
                    fragmentManager(shomeActivity);

                    return true;
                case R.id.navigation_request:
                    RequestFragment requestActivity = new RequestFragment();
                    fragmentManager(requestActivity);

                    return true;
                case R.id.navigation_client:
                    ClientFragment clientFragment = new ClientFragment();
                    fragmentManager(clientFragment);

                    return true;
            }
            return false;
        }
    };

    private void fragmentManager(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fr_layout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}