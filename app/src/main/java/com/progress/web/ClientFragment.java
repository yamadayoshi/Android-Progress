package com.progress.web;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.progress.adapter.RVClientAdapter;
import com.progress.adapter.RVRequestAdapter;
import com.progress.classes.Client;
import com.progress.classes.RestRead;

import java.util.concurrent.ExecutionException;

public class ClientFragment extends Fragment {

    private RecyclerView recyclerViewClient;
    private RVClientAdapter clientAdapter;
    private SwipeRefreshLayout swipeClient;
    private TextView tvClientCount;
    private EditText edtClientSearch;

    public ClientFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_client, container, false);

        recyclerViewClient = view.findViewById(R.id.rv_clients);
        swipeClient = view.findViewById(R.id.swp_client);
        tvClientCount = view.findViewById(R.id.tv_client_count);
        edtClientSearch = view.findViewById(R.id.edt_client_search);

        LinearLayoutManager linearLayout = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);

        recyclerViewClient.setLayoutManager(linearLayout);

        loadClient();

        swipeClient.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadClient();
                swipeClient.setRefreshing(false);
            }
        });

        edtClientSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (start > 0 || count > 0) {
                    clientAdapter.getFilter().filter(s);
                }else {
                    try {
                        clientAdapter = new RVClientAdapter(new RestRead().execute(MainActivity.endpoint + "/client/api/all", "client").get());
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                recyclerViewClient.setAdapter(clientAdapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getContext(), ClientData.class);
                startActivity(it);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        edtClientSearch.getText().clear();
        loadClient();
    }

    private void loadClient() {
        try {
            tvClientCount.setText(new RestRead().execute(MainActivity.endpoint+"/client/api/totalCount", "count").get().get(0).toString() + "\nClients");

            clientAdapter = new RVClientAdapter(new RestRead().execute(MainActivity.endpoint + "/client/api/all", "client").get());
            recyclerViewClient.setAdapter(clientAdapter);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
