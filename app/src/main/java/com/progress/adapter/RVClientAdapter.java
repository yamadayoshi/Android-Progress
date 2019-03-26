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
import android.widget.TextView;

import com.progress.classes.Client;
import com.progress.classes.RestRead;
import com.progress.web.ClientData;
import com.progress.web.MainActivity;
import com.progress.web.R;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class RVClientAdapter extends RecyclerView.Adapter<RVClientAdapter.ClientViewHolder> {

    List<Client> clients;

    public RVClientAdapter(List<Client> clients) {
        this.clients = clients;
    }

    @NonNull
    @Override
    public ClientViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.client_view, viewGroup, false);

        ClientViewHolder clientViewHolder = new ClientViewHolder(view);

        return clientViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ClientViewHolder requestViewHolder, final int i) {
        requestViewHolder.tvName.setText(clients.get(i).getName());
        requestViewHolder.tvEmail.setText(clients.get(i).getEmail());

        requestViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    List<Client> clientEdit = new RestRead().execute(MainActivity.endpoint + "/client/api/findById/" + clients.get(i).getId(), "client").get();

                    if (clientEdit != null) {
                        Intent it = new Intent(v.getContext(), ClientData.class);

                        Bundle parameters = new Bundle();

                        parameters.putInt("id", clientEdit.get(0).getId());
                        parameters.putString("name", clientEdit.get(0).getName());
                        parameters.putString("cnpj", clientEdit.get(0).getCnpj());
                        parameters.putString("email", clientEdit.get(0).getEmail());
                        parameters.putString("phone", clientEdit.get(0).getPhone());

                        it.putExtras(parameters);

                        v.getContext().startActivity(it);
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Log.d("Click item viewHolder", "Item " + i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return clients.size();
    }

    public static class ClientViewHolder extends RecyclerView.ViewHolder {
        CardView cvClient;
        TextView tvName;
        TextView tvEmail;

        ClientViewHolder(View view) {
            super(view);
            cvClient = view.findViewById(R.id.cv_client);
            tvName = view.findViewById(R.id.tv_client_name_view);
            tvEmail = view.findViewById(R.id.tv_client_email_view);
        }
    }
}
