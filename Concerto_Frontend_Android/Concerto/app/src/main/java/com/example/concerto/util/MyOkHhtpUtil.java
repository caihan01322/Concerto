package com.example.concerto.util;

import org.json.JSONArray;

import java.io.IOException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyOkHhtpUtil {
    URL url;
    String method;
    String data="";

    public MyOkHhtpUtil(URL GETUrl){
        url=GETUrl;
        method="GET";
    }

    public MyOkHhtpUtil(URL POSTURL,Object object){
        url=POSTURL;
        method="POST";
    }

    public String getData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client=new OkHttpClient();
                if(method.equals("Get")|method.equals("get")){
                    Request request=new Request.Builder().url(url).build();
                    try {
                        Response response=client.newCall(request).execute();
                        data=response.body().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        return data;
    }
}
