package com.progress.web;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.Toast;

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
import java.text.DateFormatSymbols;
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
    private LineGraphSeries<DataPoint> series;
    private int year;

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

        year = 2019;

        metrics = view.getResources().getDisplayMetrics();

        swipeRefreshLayout.setDistanceToTriggerSync(700);

        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent), getResources().getColor(R.color.colorEdit), getResources().getColor(R.color.colorPrimary));

        LinearLayoutManager linearLayout = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(linearLayout);

        loadFromRest();

        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(getContext(), new DateFormatSymbols().getMonths()[(int) dataPoint.getX() - 1] + ": " + (int) dataPoint.getY() + " chamados", Toast.LENGTH_SHORT).show();
            }
        });

        graphView.setBackgroundColor(Color.WHITE);

        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setMinX(1);
        graphView.getViewport().setMaxX(12);

        graphView.getLegendRenderer().setVisible(true);

        graphView.getViewport().setScalable(true);
        graphView.getViewport().setScalableY(true);

        graphView.addSeries(series);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadFromRest();
            }
        });

        return view;
    }

    private void loadFromRest() {
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

            }else {
                Intent it = new Intent(getContext(), NoInternetActivity.class);
                startActivity(it);
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
            Intent it = new Intent(getContext(), NoInternetActivity.class);
            startActivity(it);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Intent it = new Intent(getContext(), NoInternetActivity.class);
            startActivity(it);
        }

        swipeRefreshLayout.setRefreshing(false);
    }

    private void loadGraph() {
        long[] requests = new long[12];

        try {
            for (int i = 0; i < 12; i++) {
                requests[i] = (long) new RestRead().execute(endpoint + "/request/api/countByMonth?month=" + String.valueOf(i+1) + "&year=" + year, "count").get().get(0);
            }

            series = new LineGraphSeries<>(
                    new DataPoint[] {
                        new DataPoint(1, requests[0]),
                        new DataPoint(2, requests[1]),
                        new DataPoint(3, requests[2]),
                        new DataPoint(4, requests[3]),
                        new DataPoint(5, requests[4]),
                        new DataPoint(6, requests[5]),
                        new DataPoint(7, requests[6]),
                        new DataPoint(8, requests[7]),
                        new DataPoint(9, requests[8]),
                        new DataPoint(10, requests[9]),
                        new DataPoint(11, requests[10]),
                        new DataPoint(12, requests[11])
                    }
            );

            series.setTitle("2019");
            series.setColor(Color.BLUE);
            series.setBackgroundColor(Color.argb(70, 85, 138, 181));
            series.setDrawBackground(true);
            series.setDrawDataPoints(true);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadFromRest();
    }
}
