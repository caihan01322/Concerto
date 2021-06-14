package com.example.concerto.bean;

import android.content.Context;

import org.json.JSONObject;

public class SubProject {
    private String name;
    private String description;
    private String laucher;
    private String time;
    private Context context;
    private JSONObject jsonObject;

    public SubProject(String name, String description, String laucher, String time,Context context,JSONObject jsonObject) {
        this.name = name;
        this.description = description;
        this.laucher = laucher;
        this.time = time;
        this.context = context;
        this.jsonObject = jsonObject;
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

    public Context getContext() {
        return context;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }
}
