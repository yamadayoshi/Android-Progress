package com.progress.web;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

import com.progress.classes.Artifact;
import com.progress.classes.Client;
import com.progress.classes.Item;
import com.progress.classes.ItemListAdapter;
import com.progress.classes.RestRead;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SelectItem extends AppCompatActivity {

    private EditText edtSearch;
    private ListView lstItem;
    private List<Item> items;
    private ItemListAdapter adapter;
    private Item selectedItem;
    private String typeReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_item);

        items = new ArrayList<>();

        edtSearch = findViewById(R.id.edt_search);
        lstItem = findViewById(R.id.lst_item);

        //get type
        Bundle parameter = getIntent().getExtras();

        if (parameter != null) {
            if (parameter.getString("listType").equals("client")) {
                try {
                    List<Client> clients = new RestRead().execute(MainActivity.endpoint+"/client/api/all", "client").get();

                    for (Client client: clients) {
                        items.add(new Item(client.getId(), client.getName()));
                    }

                    typeReturn = "client";

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else if (parameter.getString("listType").equals("artifact")) {
                try {
                    List<Artifact> artifacts = new RestRead().execute(MainActivity.endpoint+"/screen/api/all", "artifact").get();

                    for (Artifact artifact : artifacts) {
                        items.add(new Item(artifact.getId(), artifact.getName()));
                    }

                    typeReturn = "artifact";

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        adapter = new ItemListAdapter(items, this);
        lstItem.setAdapter(adapter);

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (start > 0 || count > 0) {
                    adapter.getFilter().filter(s);
                }else {
                    adapter = new ItemListAdapter(items, SelectItem.this);
                }

                lstItem.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        lstItem.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent();

                selectedItem = (Item) parent.getAdapter().getItem(position);

                it.putExtra("selectedId", String.valueOf(selectedItem.getId()));
                it.putExtra("selectedValue", selectedItem.getValue());
                it.putExtra("type", typeReturn);

                setResult(Activity.RESULT_OK, it);

                finish();
            }
        });
    }
}
