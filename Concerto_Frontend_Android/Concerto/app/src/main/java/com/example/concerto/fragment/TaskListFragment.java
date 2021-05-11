package com.example.concerto.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.concerto.R;
import com.example.concerto.bean.TagsItem;
import com.example.concerto.bean.TaskItem;
import com.example.concerto.adapter.TaskAdapter;


import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TaskListFragment extends Fragment {

    private List<TaskItem> mtasks=new ArrayList<>();
    private List<TaskItem> completedTasks=new ArrayList<>();
    private String data;
    JSONArray jsonArray;



    int type;//0==推荐，1==今日，2==本周，3==本月

    public TaskListFragment(int type) {
        this.type=type;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_task_list, container, false);
        initData();
        RecyclerView recyclerView=view.findViewById(R.id.rv_task_list);//任务列表
        RecyclerView recyclerViewComplete=view.findViewById(R.id.rv_task_complete_list);//已完成的任务列表

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this.getContext());
        LinearLayoutManager clinearLayoutManager=new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerViewComplete.setLayoutManager(clinearLayoutManager);

        TaskAdapter adapter=new TaskAdapter(mtasks,this.getContext());
        TaskAdapter cadapter=new TaskAdapter(completedTasks,this.getContext());
        adapter.setData(mtasks);
        cadapter.setData(completedTasks);
        recyclerView.setAdapter(adapter);
        recyclerViewComplete.setAdapter(cadapter);

        

        return view;
    }



    public void getData() {


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String url = "";
                    if(type==0){
                        url="http://81.69.253.27:7777/User/Schedule/Recommend";
                    }else if(type==1){
                        url="http://81.69.253.27:7777/User/Schedule/day";
                    }else if(type==2){
                        url="http://81.69.253.27:7777/User/Schedule/Week";
                    }else if(type==3){
                        url="http://81.69.253.27:7777/User/Schedule/Month";
                    }

                    String token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4In0.9i1K1-3jsGh3tbTh2eMmD64C3XOE-vX9c1JywsqSoT0";
                    OkHttpClient client=new OkHttpClient();
                    Request.Builder reqBuild = new Request.Builder();
                    reqBuild.addHeader("token",token);
                    HttpUrl.Builder urlBuilder = HttpUrl.parse(url)
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
                    jsonArray=(JSONArray)jsonObject.getJSONArray("data");
                    //Log.v("TaskListFragement",type+"--------------"+jsonArray.toString());
                    //Log.v("TaskListFragement", "----"+jsonObject.getString("message")+"---------");

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

    }


    //jsonArray=(JSONArray)jsonObject.getJSONArray("data");
    public void initData(){
        if(jsonArray==null){

            Log.v("TaskListFragement","************NULL**************");
            return;
        }

        try {
            Log.v("TaskListFragement","-------"+type+"---------"+jsonArray.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                TaskItem task=new TaskItem();

                // taskId  taskTitle  tags days  names complete
                task.setTaskId(jsonObject.getString("taskId"));
                task.setTaskTitle(jsonObject.getString("taskTitle"));
                JSONArray tags=jsonObject.getJSONArray("tags");
                List<TagsItem> tagList=new ArrayList<>();
                for(int j=0;j<tags.length();j++){
                    JSONObject tag=tags.getJSONObject(j);
                    String tagContent=tag.getString("tagContent");
                    String tagColor=tag.getString("tagColor");
                    TagsItem tagsItem=new TagsItem(tagContent,tagColor);

                    tagList.add(tagsItem);
                }
                task.setTags(tagList);


                JSONArray names=jsonObject.getJSONArray("participants");

                List<String> nameList=new ArrayList<>();
                for(int j=0;j<names.length();j++){
                    JSONObject nameobject=names.getJSONObject(j);
                    String name=nameobject.getString("userName");
                    nameList.add(name);
                }
                task.setNames(nameList);

                //计算天数
                String startTime=jsonObject.getString("taskStartTime");
                String endTime=jsonObject.getString("taskEndTime");
                SimpleDateFormat sdf1 =new SimpleDateFormat("yyyy-MM-dd");
                Date inDate=sdf1.parse(startTime);
                Date outDate=sdf1.parse(endTime);
                Calendar c1=Calendar.getInstance();
                c1.setTime(inDate);
                Calendar c2=Calendar.getInstance();
                c2.setTime(outDate);
                int days = c2.get(Calendar.DAY_OF_YEAR) - c1.get(Calendar.DAY_OF_YEAR);
                task.setDays(days);

                //0未完成 1完成
                int status=jsonObject.getInt("taskStatus");
                task.setComplete(status);
                if(status==0){
                    mtasks.add(task);
                }else if(status==1){
                    completedTasks.add(task);
                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }





}