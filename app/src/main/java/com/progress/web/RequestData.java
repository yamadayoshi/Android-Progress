package com.progress.web;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.progress.classes.Artifact;
import com.progress.classes.Client;
import com.progress.classes.RestRead;

import java.util.concurrent.ExecutionException;

public class RequestData extends AppCompatActivity {

    private final static int INTENT_RESULT = 1;
    private ArrayAdapter<String> adapter;
    private EditText edtTitle;
    private EditText edtClientDescription;
    private EditText edtDeveloperDescription;
    private Button btnArtifact;
    private Button btnClient;
    private Button btnApply;
    private TextView tvArtifact;
    private TextView tvClient;
    private Spinner spnStatus;
    private String client;
    private String artifact;
    private int status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_data);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        edtTitle = findViewById(R.id.edt_request_title);
        edtClientDescription = findViewById(R.id.edt_client_description);
        edtDeveloperDescription = findViewById(R.id.edt_developer_description);
        btnArtifact = findViewById(R.id.btn_artifacts);
        btnClient = findViewById(R.id.btn_clients);
        btnApply = findViewById(R.id.btn_apply_request);
        tvArtifact = findViewById(R.id.tv_artifacts);
        tvClient = findViewById(R.id.tv_clients);
        spnStatus = findViewById(R.id.spn_request_status);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.GRAY));

        // update
        final Bundle extras = getIntent().getExtras();

        if (extras != null) {
            try {
                edtTitle.setText(extras.getString("title"));
                edtClientDescription.setText(extras.getString("clientDesc"));
                edtDeveloperDescription.setText(extras.getString("devDesc"));
                tvClient.setText(((Client) new RestRead().execute(MainActivity.endpoint + "/client/api/findById/" + extras.getInt("client"), "client").get().get(0)).getName());
                tvArtifact.setText(((Artifact) new RestRead().execute(MainActivity.endpoint + "/screen/api/findById/" + extras.getInt("artifact"), "artifact").get().get(0)).getName());

                adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new String[] {"Open", "Running", "Closed"});
                status = adapter.getPosition(extras.getString("status"));

                client = String.valueOf(extras.getInt("client"));
                artifact = String.valueOf(extras.getInt("artifact"));

                btnApply.setText(getString(R.string.update));
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new String[]{"Open"});
            status = 0;
        }

        spnStatus.setAdapter(adapter);

        spnStatus.setSelection(status);

        btnArtifact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callList("artifact");
                }
        });

        btnClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callList("client");
            }
        });

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (extras == null) {
                    new RestRead().execute(MainActivity.endpoint + "/request/api/add?title=" + edtTitle.getText().toString().replace(" ", "%20") + "&clientDescription=" + edtClientDescription.getText().toString().replace(" ", "%20") + "&devDescription="
                            + edtDeveloperDescription.getText().toString().replace(" ", "%20") + "&clientId=" + client + "&screenId=" + artifact);
                 } else {
                    new RestRead().execute(MainActivity.endpoint + "/request/api/update/" + extras.getInt("id") + "?title=" + edtTitle.getText().toString().replace(" ", "%20") + "&clientDescription=" + edtClientDescription.getText().toString().replace(" ", "%20") + "&devDescription="
                            + edtDeveloperDescription.getText().toString().replace(" ", "%20") + "&clientId=" + client + "&screenId=" + artifact  + "&status=" + spnStatus.getSelectedItem().toString());
                }

                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case INTENT_RESULT:
                if (resultCode == Activity.RESULT_OK) {
                    if (data.getStringExtra("type").equals("artifact")) {
                        tvArtifact.setText(data.getStringExtra("selectedValue"));
                        artifact = data.getStringExtra("selectedId");
                    }
                    else if (data.getStringExtra("type").equals("client")) {
                        tvClient.setText(data.getStringExtra("selectedValue"));
                        client = data.getStringExtra("selectedId");
                    }
                }
        }
    }

    public void callList(String type) {
        Intent it = new Intent(getBaseContext(), SelectItem.class);

        it.putExtra("listType", type);

        it.putExtras(it);
        startActivityForResult(it, INTENT_RESULT);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
