package com.progress.web;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.progress.classes.RestRead;

public class ClientData extends AppCompatActivity {

    private EditText edtName;
    private EditText edtCNPJ;
    private EditText edtEmail;
    private EditText edtPhone;
    private Button btnApply;

    private boolean error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_data);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.GRAY));


        edtName = findViewById(R.id.edt_client_name);
        edtCNPJ = findViewById(R.id.edt_client_cnpj);
        edtEmail = findViewById(R.id.edt_client_email);
        edtPhone = findViewById(R.id.edt_client_phone);

        btnApply = findViewById(R.id.btn_apply_client);

        final Bundle extras = getIntent().getExtras();

        if (extras != null) {
            edtName.setText(extras.getString("name"));
            edtCNPJ.setText(extras.getString("cnpj"));
            edtPhone.setText(extras.getString("phone"));
            edtEmail.setText(extras.getString("email"));

            btnApply.setText(getString(R.string.update));
        }

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                error = false;

                if (edtName.getText().length() < 3) {
                    Toast.makeText(getBaseContext(), "Nome menor que 3 caracteres", Toast.LENGTH_SHORT).show();
                    error = true;
                } else if (edtCNPJ.getText().length() < 12) {
                    Toast.makeText(getBaseContext(), "Documento digitado não é válido", Toast.LENGTH_SHORT).show();
                    error = true;
                } else if (edtEmail.getText().length() < 3 || !edtEmail.getText().toString().contains("@")) {
                    Toast.makeText(getBaseContext(), "Email digitado não é valido", Toast.LENGTH_SHORT).show();
                    error = true;
                } else if (edtPhone.getText().length() < 8) {
                    Toast.makeText(getBaseContext(), "Phone invalido", Toast.LENGTH_SHORT).show();
                    error = true;
                }

                if (!error) {
                    if (extras != null) {
                        // update
                        new RestRead().execute(MainActivity.endpoint + "/client/api/update/" + extras.getInt("id") + "?name=" + edtName.getText().toString().replace(" ", "%20") + "&cpf=" + edtCNPJ.getText().toString() + "&email=" + edtEmail.getText().toString() + "&phone=" + edtPhone.getText().toString());
                    } else
                        // add
                        new RestRead().execute(MainActivity.endpoint + "/client/api/add?name=" + edtName.getText().toString() + "&cpf=" + edtCNPJ.getText() + "&email=" + edtEmail.getText().toString() + "&phone=" + edtPhone.getText().toString());

                        finish();
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
