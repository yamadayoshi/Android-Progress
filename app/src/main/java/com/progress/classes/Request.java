package com.progress.classes;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class Request {
    private int id;
    private String title;
    private Date date;
    private String clientDesc;
    private String devDesc;
    private String status;
    private Client client;
    private Artifact artifact;

    public Request() {

    }

    public Request(String title, Date date) {
        setTitle(title);
        setDate(date);
    }

    public Request(String title, Client client, Date date, String status, Artifact artifact) {
        setTitle(title);
        setClient(client);
        setDate(date);
        setStatus(status);
        setArtifact(artifact);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Artifact getArtifact() {
        return artifact;
    }

    public void setArtifact(Artifact artifact) {
        this.artifact = artifact;
    }

    public String getClientDesc() {
        return clientDesc;
    }

    public void setClientDesc(String clientDesc) {
        this.clientDesc = clientDesc;
    }

    public String getDevDesc() {
        return devDesc;
    }

    public void setDevDesc(String devDesc) {
        this.devDesc = devDesc;
    }

    //    public List<Request> init() {
//        List<Request> requests = new ArrayList<>();
//
//        requests.add(new Request("Request 1", new Date(2018, 6, 02)));
//        requests.add(new Request("Request 2", new Date(2018, 7, 10)));
//        requests.add(new Request("Request 3", new Date(2018, 7, 11)));
//        requests.add(new Request("Request 4", new Date(2018, 7, 19)));
//        requests.add(new Request("Request 5", new Date(2018, 8, 25)));
//        requests.add(new Request("Request 6", new Date(2018, 12, 31)));
//
//        return requests;
//    }
//
//    public static List<Request> listInit(int n) {
//        List<Request> requests = new ArrayList<>();
//
//        for (int i = 0; i < n; i++) {
////            requests.add(new Request("Request list " + i, new Client("Client " + i, "client" + i + "@gmail.com", "12345678" + i), new Date(2018, 6, i), i%2==0?"Closed":"Open", new Artifact("Art " + i, "Desc " + i)));
//        }
//
//        return requests;
//    }
}
