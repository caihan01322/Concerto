package com.example.concerto.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.concerto.R;
import com.example.concerto.bean.TagsItem;
import com.example.concerto.bean.TaskItem;
import com.example.concerto.adapter.TaskAdapter;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TaskListFragment extends Fragment {

    private List<TaskItem> mtasks=new ArrayList<>();
    private List<TaskItem> completedTasks=new ArrayList<>();
    private String data;



    int type;//0==推荐，1==今日，2==本周，3==本月

    public TaskListFragment(int type) {
        this.type=type;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        testUrl();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_task_list, container, false);

        initDatas();
        RecyclerView recyclerView=view.findViewById(R.id.rv_task_list);//任务列表
        RecyclerView recyclerViewComplete=view.findViewById(R.id.rv_task_complete_list);//已完成的任务列表

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this.getContext());
        LinearLayoutManager clinearLayoutManager=new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerViewComplete.setLayoutManager(clinearLayoutManager);

        TaskAdapter adapter=new TaskAdapter(mtasks,this.getContext());
        TaskAdapter cadapter=new TaskAdapter(completedTasks,this.getContext());

        recyclerView.setAdapter(adapter);
        recyclerViewComplete.setAdapter(cadapter);

        

        return view;
    }

    public void initDatas() {

        TagsItem tag=new TagsItem("计算机","#0022ff");
        TagsItem tag2=new TagsItem("机器人","#22ff00");
        TagsItem tag3=new TagsItem("脑机接口","#ff0022");
        List<TagsItem> tags=new ArrayList<>();
        tags.add(tag);
        tags.add(tag2);
        tags.add(tag3);
        String[] names={"大胖","二胖","小胖"};
        for(int i=0;i<5;i++){
            TaskItem task=new TaskItem();
            task.setTags(tags);
            task.setNames(names);
            task.setTaskTitle(type+"测试"+i);
            task.setDays(i);
            mtasks.add(task);
        }
        for(int i=0;i<5;i++){
            TaskItem task=new TaskItem();
            task.setTags(tags);
            task.setNames(names);
            task.setTaskTitle(type+"测试"+i);
            task.setDays(i);
            task.setComplete(1);
            completedTasks.add(task);
        }

    }

    public void testUrl() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4In0.9i1K1-3jsGh3tbTh2eMmD64C3XOE-vX9c1JywsqSoT0";
                    OkHttpClient client=new OkHttpClient();
                    Request.Builder reqBuild = new Request.Builder();
                    reqBuild.addHeader("token",token);
                    HttpUrl.Builder urlBuilder = HttpUrl.parse("http://81.69.253.27:7777//User/Schedule/Month")
                            .newBuilder();
                    reqBuild.url(urlBuilder.build());
                    Request request = reqBuild.build();
                    Response response = client.newCall(request).execute();
                    data=response.body().string();
                    //Log.v("TaskListFragement",data);
                    if(data != null && data.startsWith("\ufeff"))
                    {
                        data =  data.substring(1);
                    }


                    JSONObject jsonObject=new JSONObject(data);
                    JSONArray jsonArray=(JSONArray)jsonObject.getJSONArray("data");
                    Log.v("TaskListFragement","--------------"+jsonArray.toString());
                    //Log.v("TaskListFragement", "----"+jsonObject.getString("message")+"---------");

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

    }





}