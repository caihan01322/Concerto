package com.example.concerto.fragment;

import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;


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
    TaskAdapter adapter;
    TaskAdapter cadapter;
    private String data;
    JSONArray jsonArray;
    String token;

    long projectId=2;
    public void setProjectId(long id){
        projectId=id;
    }

    public  long getProjectId(){
        return projectId;
    }




    int type;//0==推荐，1==今日，2==本周，3==本月  4==项目详情页本周  5==项目详情页全部

    public TaskListFragment(int type) {
        this.type=type;
    }

    public TaskListFragment(int type,long id) {
        projectId=id;
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
        RecyclerView recyclerView=view.findViewById(R.id.rv_task_list);//任务列表
        RecyclerView recyclerViewComplete=view.findViewById(R.id.rv_task_complete_list);//已完成的任务列表

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this.getContext());
        LinearLayoutManager clinearLayoutManager=new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerViewComplete.setLayoutManager(clinearLayoutManager);
        initData();
        adapter=new TaskAdapter(mtasks,this.getContext(),this);
        cadapter=new TaskAdapter(completedTasks,this.getContext(),this);
        adapter.setData(mtasks);
        cadapter.setData(completedTasks);
        recyclerView.setAdapter(adapter);
        recyclerViewComplete.setAdapter(cadapter);

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
        return view;
    }



    public void getData() {


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String url = "";
                    switch (type){
                        case 0:
                            url="http://81.69.253.27:7777/User/Schedule/Recommend";
                            break;
                        case 1:
                            url="http://81.69.253.27:7777/User/Schedule/day";
                            break;
                        case 2:
                            url="http://81.69.253.27:7777/User/Schedule/Week";
                            break;
                        case 3:
                            url="http://81.69.253.27:7777/User/Schedule/Month";
                            break;
                        case 4:
                            url="http://81.69.253.27:7777/project/task/week";
                            break;
                        case 5:
                            url="http://81.69.253.27:7777/project/task/all";
                            break;
                    }


                    token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4In0.9i1K1-3jsGh3tbTh2eMmD64C3XOE-vX9c1JywsqSoT0";
                    OkHttpClient client=new OkHttpClient();
                    Request.Builder reqBuild = new Request.Builder();
                    if(type==0||type==1||type==2||type==3)
                        reqBuild.addHeader("token",token);
                    if(type==4||type==5) {
                        reqBuild.addHeader("projectId", 2 + "");
                    }
                    HttpUrl.Builder urlBuilder = HttpUrl.parse(url)
                            .newBuilder();
                    urlBuilder.addQueryParameter("projectId", projectId+"");
                    reqBuild.url(urlBuilder.build());
                    Request request = reqBuild.build();
                    Response response = client.newCall(request).execute();
                    data=response.body().string();
                    Log.v("TaskListFragement",data);
                    if(data != null && data.startsWith("\ufeff"))
                    {
                        data =  data.substring(1);
                    }


                    JSONObject jsonObject=new JSONObject(data);
                    jsonArray=(JSONArray)jsonObject.getJSONArray("data");


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


    public void completeTask(TaskItem task,int position){
        mtasks.remove(position);
        completedTasks.add(task);
        adapter.setData(mtasks);
        cadapter.setData(completedTasks);
    }
}