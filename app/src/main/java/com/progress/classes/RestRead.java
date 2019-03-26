package com.progress.classes;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class RestRead extends AsyncTask<String, Integer, List>{

    private List list;
    private JSONArray jsonArray;

    @Override
    protected List doInBackground(String... strings) {
        try {
            URL url = new URL(strings[0]);

            HttpURLConnection httpURL = (HttpURLConnection) url.openConnection();

            if (httpURL.getResponseCode() == 200) {
                InputStream inputStream = httpURL.getInputStream();

                jsonArray = new JSONArray(convertStreamToString(inputStream));

                if (strings.length > 1) {
                    if (strings[1].equals("count"))
                        list = count(jsonArray);

                    if (strings[1].equals("request"))
                        list = toRequestList(jsonArray);

                    if (strings[1].equals("client"))
                        list = toClientList(jsonArray);

                    if (strings[1].equals("artifact"))
                        list = toArtifactList(jsonArray);
                }
            }else {

                Log.d("ReadRead", "Error code " + String.valueOf(httpURL.getResponseCode()));
                return null;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }

    private List<Request> toRequestList(JSONArray jsonArray) {
        List<Request> requestList = new ArrayList<>();
        String date[];

        if(jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    Request request = new Request();

                    JSONObject json = jsonArray.getJSONObject(i);

                    request.setId(json.getInt("requestId"));

                    request.setTitle(json.getString("requestTitle"));

                    request.setClientDesc(json.getString("requestClientDescription"));

                    request.setDevDesc(json.getString("requestDevDescription"));

                    date = json.getString("requestEntryDate").replace("[", "").replace("]", "").split(",");
                    request.setDate(new Date(Integer.valueOf(date[0]), Integer.valueOf(date[1]), Integer.valueOf(date[2])));

                    request.setStatus(json.getString("status"));

                    if (json.has("requestClient")) {
                        Client client = new Client(json.getJSONObject("requestClient").getInt("clientId"), json.getJSONObject("requestClient").getString("clientName"), json.getJSONObject("requestClient").getString("clientCnpjCpf"), json.getJSONObject("requestClient").getString("clientEmail"), json.getJSONObject("requestClient").getString("clientPhone"));
                        request.setClient(client);
                    }

                    if (json.has("requestScreen")) {
                        Artifact artifact = new Artifact(json.getJSONObject("requestScreen").getInt("screenId"), json.getJSONObject("requestScreen").getString("screenName"), json.getJSONObject("requestScreen").getString("screenDescription"));
                        request.setArtifact(artifact);
                    }

                    requestList.add(request);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        return requestList;
    }

    private List<Client> toClientList(JSONArray jsonArray) {
        List<Client> clientList = new ArrayList<>();

        if (jsonArray != null) {
            try {
                for (int i = 0;i < jsonArray.length();i++) {
                    Client client = new Client();

                    JSONObject json = jsonArray.getJSONObject(i);

                    client.setId(json.getInt("clientId"));
                    client.setName(json.getString("clientName"));
                    client.setCnpj(json.getString("clientCnpjCpf"));
                    client.setEmail(json.getString("clientEmail"));
                    client.setPhone(json.getString("clientPhone"));

                    clientList.add(client);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return clientList;
    }

    private List<Artifact> toArtifactList(JSONArray jsonArray) {
        List<Artifact> artifactList = new ArrayList<>();

        if (jsonArray != null) {
            try {
                for (int i = 0;i < jsonArray.length();i++) {
                    Artifact artifact = new Artifact();

                    JSONObject json = jsonArray.getJSONObject(i);

                    artifact.setId(json.getInt("screenId"));
                    artifact.setName(json.getString("screenName"));
                    artifact.setDescription(json.getString("screenDescription"));

                    artifactList.add(artifact);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return artifactList;
    }

    private List<Long> count(JSONArray jsonArray) {
        List<Long> count = new ArrayList<>();

        if(jsonArray != null) {
            try {
                count.add(new Long(jsonArray.get(0).toString()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return count;
    }

    private String convertStreamToString(InputStream is) {
        /*
         * To convert the InputStream to String we use the
         * BufferedReader.readLine() method. We iterate until the
         * BufferedReader return null which means there's no more data to
         * read. Each line will appended to a StringBuilder and returned as
         * String.
         */
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }
}
