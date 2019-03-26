package com.progress.web;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.progress.network.Network;

public class NoInternetActivity extends AppCompatActivity {

    private Button btn_retry_connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet);

        btn_retry_connection = findViewById(R.id.btn_retry_connection);

        btn_retry_connection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (new Network().getConnectivityStatus(getBaseContext())) {
                    Intent it = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(it);
                    finish();
                }
            }
        });
    }
}
