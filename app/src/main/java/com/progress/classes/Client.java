package com.progress.classes;

import java.util.ArrayList;
import java.util.List;

public class Client {
    private int id;
    private String name;
    private String cnpj;
    private String email;
    private String phone;

    public Client() {

    }

    public Client( int id, String name, String cnpj, String email, String phone) {
        this.id = id;
        this.name = name;
        this.cnpj = cnpj;
        this.email = email;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public static List<Client> init() {
        List<Client> clients = new ArrayList<>();

//        clients.add(new Client("Yoshi", "yoshi@gmail.com", "1564-4485"));
//        clients.add(new Client("Maria", "maria@gmail.com", "1564-4486"));
//        clients.add(new Client("Greg", "greg@gmail.com", "1564-4487"));
//        clients.add(new Client("Yuzi", "yuzi@gmail.com", "1564-4488"));
//        clients.add(new Client("Rosa", "rosa@gmail.com", "1564-4489"));
//        clients.add(new Client("Bambini", "bambini@gmail.com", "1564-4490"));
//        clients.add(new Client("Ruffles", "ruffles@gmail.com", "1564-4491"));
//        clients.add(new Client("Doritos", "doritos@gmail.com", "1564-4492"));
//        clients.add(new Client("Fandangos", "fandangos@gmail.com", "1564-4493"));
//        clients.add(new Client("Trakinas", "trakinas@gmail.com", "1564-4494"));
//        clients.add(new Client("Dell", "dell@gmail.com", "1564-4495"));
//        clients.add(new Client("Arno", "Arno@gmail.com", "1564-4496"));

        return clients;
    }
}
