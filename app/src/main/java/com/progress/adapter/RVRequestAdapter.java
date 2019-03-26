package com.progress.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.progress.classes.Client;
import com.progress.classes.Request;
import com.progress.classes.RestRead;
import com.progress.web.MainActivity;
import com.progress.web.R;
import com.progress.web.RequestData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class RVRequestAdapter extends RecyclerView.Adapter<RVRequestAdapter.RequestViewHolder> {

    private List<Request> requests;
    private ProgressBar progressBar;

    public RVRequestAdapter(List<Request> requests) {
        this.requests = requests;
    }

    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.request_view, viewGroup, false);

        RequestViewHolder requestViewHolder = new RequestViewHolder(view);



        return requestViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RequestViewHolder requestViewHolder, final int i) {
        requestViewHolder.tvRequest.setText(requests.get(i).getTitle());
        requestViewHolder.tvClient.setText(requests.get(i).getClient().getName());
        requestViewHolder.tvDate.setText(requests.get(i).getDate().toString());
        requestViewHolder.tvStatus.setText(requests.get(i).getStatus());

        requestViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    List<Request> request = new RestRead().execute(MainActivity.endpoint + "/request/api/findById/" + requests.get(i).getId(), "request").get();

                    if (request != null) {
                        Intent it = new Intent(v.getContext(), RequestData.class);

                        it.putExtra("id", request.get(0).getId());
                        it.putExtra("status", request.get(0).getStatus());
                        it.putExtra("title", request.get(0).getTitle());
                        it.putExtra("clientDesc", request.get(0).getClientDesc());
                        it.putExtra("devDesc", request.get(0).getDevDesc());
                        it.putExtra("artifact", request.get(0).getArtifact().getId());
                        it.putExtra("client", request.get(0).getClient().getId());

                        v.getContext().startActivity(it);
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }

    public static class RequestViewHolder extends RecyclerView.ViewHolder {
        CardView cvRequest;
        TextView tvRequest;
        TextView tvClient;
        TextView tvDate;
        TextView tvStatus;

        RequestViewHolder(View view) {
            super(view);
            cvRequest = view.findViewById(R.id.cv_request);
            tvRequest = view.findViewById(R.id.tv_request_title);
            tvClient = view.findViewById(R.id.tv_request_client);
            tvDate = view.findViewById(R.id.tv_request_date);
            tvStatus = view.findViewById(R.id.tv_status);
        }
    }
}
