package com.progress.classes;

import java.util.ArrayList;
import java.util.List;

public class Artifact {
    private int id;
    private String name;
    private String description;

    public Artifact() {

    }

    public Artifact(int id, String name, String description) {
        setId(id);
        setName(name);
        setDescription(description);
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static List<Artifact> init(int n) {
        List<Artifact> artifacts = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            artifacts.add(new Artifact(i, "Screen " + i, "Description" + i));
        }

        return artifacts;
    }
}
