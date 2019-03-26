package com.progress.web;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;
import com.progress.adapter.RVAdapter;
import com.progress.classes.RestRead;
import com.progress.network.Network;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.progress.web.MainActivity.endpoint;

public class HomeFragment extends Fragment {

    private TextView tv_open;
    private TextView tv_running;
    private TextView tv_total;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private GraphView graphView;
    private DisplayMetrics metrics;
    private ProgressBar progressBar;

    public HomeFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        tv_open = view.findViewById(R.id.tv_request_open);
        tv_running = view.findViewById(R.id.tv_request_running);
        tv_total = view.findViewById(R.id.tv_request_total);
        recyclerView = view.findViewById(R.id.rv_requests);
        swipeRefreshLayout = view.findViewById(R.id.swp_main);
        graphView = view.findViewById(R.id.graph);
        progressBar = view.findViewById(R.id.pb_home);

        progressBar.setVisibility(View.VISIBLE);

        metrics = view.getResources().getDisplayMetrics();

        swipeRefreshLayout.setDistanceToTriggerSync(900);

        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent), getResources().getColor(R.color.colorEdit), getResources().getColor(R.color.colorPrimary));

        LinearLayoutManager linearLayout = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(linearLayout);

        loadData();

        //
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(
                new DataPoint[] {
                        new DataPoint(1, 5),
                        new DataPoint(2, 9),
                        new DataPoint(3, 7),
                        new DataPoint(4, 10),
                        new DataPoint(5, 0),
                        new DataPoint(6, 0),
                        new DataPoint(7, 0),
                        new DataPoint(8, 0),
                        new DataPoint(9, 0),
                        new DataPoint(10, 0),
                        new DataPoint(11, 0),
                        new DataPoint(12, 0)
                }
        );

        LineGraphSeries<DataPoint> series2018 = new LineGraphSeries<>(
                new DataPoint[] {
                        new DataPoint(1, 9),
                        new DataPoint(2, 7),
                        new DataPoint(3, 5),
                        new DataPoint(4, 20),
                        new DataPoint(5, 0),
                        new DataPoint(6, 0),
                        new DataPoint(7, 0),
                        new DataPoint(8, 0),
                        new DataPoint(9, 0),
                        new DataPoint(10, 0),
                        new DataPoint(11, 0),
                        new DataPoint(12, 0)
                }
        );

        series.setTitle("2019");
        series.setColor(Color.YELLOW);
        series.setBackgroundColor(Color.argb(100, 255, 255, 0));
        series.setDrawBackground(true);
        series.setDrawDataPoints(true);

        series2018.setTitle("2018");
        series2018.setDrawDataPoints(true);

        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
//                Toast.makeText(getBaseContext(), "Series1: On Data Point clicked: "+dataPoint, Toast.LENGTH_SHORT).show();
            }
        });

        graphView.setBackgroundColor(Color.WHITE);

        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setMinX(1);
        graphView.getViewport().setMaxX(12);

        graphView.getViewport().setScalable(true);
        graphView.getViewport().setScalableY(true);

        graphView.addSeries(series);
        graphView.addSeries(series2018);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });

        return view;
    }

    private void loadData() {
        // get data from rest
        try {
            if (new Network().getConnectivityStatus(getContext())) {
                // total open
                tv_open.setText(new RestRead().execute(endpoint+"/request/api/countByStatus?status=open", "count").get().get(0).toString());

                // total running
                tv_running.setText(new RestRead().execute(endpoint+"/request/api/countByStatus?status=running", "count").get().get(0).toString());

                // total requests
                tv_total.setText(new RestRead().execute(endpoint+"/request/api/totalCount", "count").get().get(0).toString());

                // last requests
                RVAdapter adapter = new RVAdapter(new RestRead().execute(endpoint+"/request/api/all", "request").get());
                recyclerView.setAdapter(adapter);

                // graph request by month
                loadGraph();

                progressBar.setVisibility(View.GONE);
            }else {
                Intent it = new Intent(getContext(), NoInternetActivity.class);
                startActivity(it);
            }

            swipeRefreshLayout.setRefreshing(false);
        } catch (ExecutionException e) {
            e.printStackTrace();
            Intent it = new Intent(getContext(), NoInternetActivity.class);
            startActivity(it);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Intent it = new Intent(getContext(), NoInternetActivity.class);
            startActivity(it);
        }
    }

    private void loadGraph() {

    }
}
