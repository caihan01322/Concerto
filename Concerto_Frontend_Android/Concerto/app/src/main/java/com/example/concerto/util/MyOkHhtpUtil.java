package com.example.concerto.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MyOkHhtpUtil {
    String url;
    String method;
    String data="";
    String token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI2In0.BWVpUdSvtWB4DKwLpMcYiuxUBmAKBC1kfLvvEmuS61E";

    public void setToken(String token) {
        this.token = token;
    }

    public MyOkHhtpUtil(String GETUrl){
        url=GETUrl;
        method="GET";
    }


    public String getData() throws JSONException {

        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client=new OkHttpClient();
                Request.Builder reqBuild = new Request.Builder();
                HttpUrl.Builder urlBuilder = HttpUrl.parse(url)
                        .newBuilder();
                urlBuilder.addQueryParameter("token", token);
                reqBuild.url(urlBuilder.build());
                Request request = reqBuild.build();
                try {
                    Response response = client.newCall(request).execute();
                    data=response.body().toString();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return data;
    }
}
