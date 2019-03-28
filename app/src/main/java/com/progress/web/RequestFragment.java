package com.progress.web;

import android.content.Intent;
import android.os.AsyncTask;
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
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.progress.adapter.RVRequestAdapter;
import com.progress.classes.ItemListAdapter;
import com.progress.classes.Request;
import com.progress.classes.RestRead;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class RequestFragment extends Fragment {

    private RVRequestAdapter requestAdapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRequest;
    private TextView tvRequestCount;
    private AutoCompleteTextView autoSearch;
    private ProgressBar progressBar;

    public RequestFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_request, container, false);

        recyclerView = view.findViewById(R.id.rv_requests_list);
        swipeRequest = view.findViewById(R.id.swp_request);
        tvRequestCount = view.findViewById(R.id.tv_request_count);
        autoSearch = view.findViewById(R.id.edt_search_request);
        progressBar = view.findViewById(R.id.pb_request);

        progressBar.setVisibility(View.VISIBLE);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(linearLayoutManager);

        loadRequest();

        autoSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (start > 0 || count > 0) {
                    requestAdapter.getFilter().filter(s);
                }else {
                    try {
                        requestAdapter = new RVRequestAdapter(new RestRead().execute(MainActivity.endpoint+"/request/api/all", "request").get());
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                recyclerView.setAdapter(requestAdapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        swipeRequest.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadRequest();
                swipeRequest.setRefreshing(false);
            }
        });

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getContext(), RequestData.class);
                startActivity(it);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        autoSearch.getText().clear();
        loadRequest();
    }

    private void loadRequest() {
        try {
            tvRequestCount.setText(new RestRead().execute(MainActivity.endpoint+"/request/api/totalCount", "count").get().get(0).toString() + "\nRequests");

            requestAdapter = new RVRequestAdapter(new RestRead().execute(MainActivity.endpoint+"/request/api/all", "request").get());
            recyclerView.setAdapter(requestAdapter);

            progressBar.setVisibility(View.INVISIBLE);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

//    public class onLoad extends AsyncTask<String, Integer, String> {
//
//        @Override
//        protected void onPreExecute() {
//            progressBar.setVisibility(View.VISIBLE);
//        }
//
//        @Override
//        protected void onPostExecute(String aVoid) {
//            progressBar.setVisibility(View.INVISIBLE);
//        }
//
//        @Override
//        protected String doInBackground(String... strings) {
//
//
//            return "";
//        }
//    }
}


