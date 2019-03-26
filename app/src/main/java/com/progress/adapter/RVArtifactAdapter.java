package com.progress.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.progress.classes.Artifact;
import com.progress.classes.Client;
import com.progress.web.R;

import java.util.List;

public class RVArtifactAdapter extends RecyclerView.Adapter<RVArtifactAdapter.ArtifactViewHolder> {

    List<Artifact> artifacts;

    public RVArtifactAdapter(List<Artifact> artifacts) {
        this.artifacts = artifacts;
    }

    @NonNull
    @Override
    public ArtifactViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.artifact_view, viewGroup, false);

        ArtifactViewHolder artifactViewHolder = new ArtifactViewHolder(view);

        return artifactViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ArtifactViewHolder requestViewHolder, final int i) {
        requestViewHolder.tvName.setText(artifacts.get(i).getName());
        requestViewHolder.tvDescription.setText(artifacts.get(i).getDescription());

        requestViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Click item viewHolder", "Item " + i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return artifacts.size();
    }

    public static class ArtifactViewHolder extends RecyclerView.ViewHolder {
        CardView cvArtifact;
        TextView tvName;
        TextView tvDescription;

        ArtifactViewHolder(View view) {
            super(view);
            cvArtifact = view.findViewById(R.id.cv_artifact);
            tvName = view.findViewById(R.id.tv_artifact_name);
            tvDescription = view.findViewById(R.id.tv_artifact_description);
        }
    }
}
