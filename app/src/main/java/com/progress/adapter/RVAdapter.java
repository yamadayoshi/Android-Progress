package com.progress.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.progress.classes.Request;
import com.progress.web.ClientFragment;
import com.progress.web.R;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.RequestViewHolder> {

    List<Request> requests;

    public RVAdapter() {}

    public RVAdapter(List<Request> requests) {
        this.requests = requests;
    }

    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycle_view, viewGroup, false);

        RequestViewHolder requestViewHolder = new RequestViewHolder(view);

        return requestViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RequestViewHolder requestViewHolder, final int i) {
        requestViewHolder.tvTitle.setText(requests.get(i).getTitle());
        requestViewHolder.tvDate.setText(requests.get(i).getDate().toString());

        requestViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(requestViewHolder.itemView.getContext(), ClientFragment.class);
                requestViewHolder.itemView.getContext().startActivity(it);
            }
        });
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }

    public static class RequestViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView imageView;
        TextView tvTitle;
        TextView tvDate;

        RequestViewHolder(View view) {
            super(view);
            cardView = view.findViewById(R.id.cv_request);
            imageView = view.findViewById(R.id.img_request);
            tvTitle = view.findViewById(R.id.tv_title);
            tvDate = view.findViewById(R.id.tv_date);
        }
    }

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }
}
