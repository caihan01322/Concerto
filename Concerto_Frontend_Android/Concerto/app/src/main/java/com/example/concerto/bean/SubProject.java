package com.example.concerto.bean;

public class SubProject {
    private String name;
    private String description;
    private String laucher;
    private String time;

    public SubProject(String name, String description, String laucher, String time) {
        this.name = name;
        this.description = description;
        this.laucher = laucher;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getLaucher() {
        return laucher;
    }

    public String getTime() {
        return time;
    }
}
