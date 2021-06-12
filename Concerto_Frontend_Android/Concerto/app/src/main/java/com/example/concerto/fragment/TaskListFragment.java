package com.example.concerto.fragment;

import android.app.Notification;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;


import com.example.concerto.R;
import com.example.concerto.bean.Condition;
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
import java.util.Set;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TaskListFragment extends Fragment {

    private List<TaskItem> mtasks=new ArrayList<>();
    private List<TaskItem> completedTasks=new ArrayList<>();
    private TaskAdapter adapter;
    private TaskAdapter cadapter;
    private String data;
    JSONArray jsonArray;
    String token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4In0.9i1K1-3jsGh3tbTh2eMmD64C3XOE-vX9c1JywsqSoT0";;
    String projectId;




    public void setProjectId(String id){
        projectId=id;
    }

    public  String getProjectId(){
        return projectId;
    }




    int type;//0==推荐，1==今日，2==本周，3==本月  4==项目详情页本周  5==项目详情页全部

    public TaskListFragment(int type) {
        this.type=type;
    }

    public TaskListFragment(int type,String id) {
        this.type=type;
        projectId=id;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void refresh(){
        removeLimit();
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


                    OkHttpClient client=new OkHttpClient();
                    Request.Builder reqBuild = new Request.Builder();
                    if(type==0||type==1||type==2||type==3)
                        reqBuild.addHeader("token",token);
                    if(type==4||type==5) {
                        reqBuild.addHeader("projectId", projectId);
                    }
                    HttpUrl.Builder urlBuilder = HttpUrl.parse(url)
                            .newBuilder();
                    urlBuilder.addQueryParameter("projectId", projectId);
                    reqBuild.url(urlBuilder.build());
                    Request request = reqBuild.build();
                    Response response = client.newCall(request).execute();
                    String temp=response.body().string();
                    data=temp.replaceAll("null","{}");
                    Log.v("TaskListFragement",data);
                    if(data != null && data.startsWith("\ufeff"))
                    {
                        data =  data.substring(1);
                    }


                    JSONObject jsonObject=new JSONObject(data);
                    jsonArray=(JSONArray)jsonObject.getJSONArray("data");
                    Log.v("TaskListFragement","-------"+type+"---------"+jsonArray.toString());

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

    }



    //jsonArray=(JSONArray)jsonObject.getJSONArray("data");
    public void initData(){
        mtasks.clear();
        completedTasks.clear();
        if(jsonArray==null){

            Log.v("TaskListFragement",type+"************NULL**************");
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
                if(tags!=null){
                    for(int j=0;j<tags.length();j++){
                        JSONObject tag=tags.getJSONObject(j);
                        String tagContent=tag.getString("tagContent");
                        String tagColor=tag.getString("tagColor");
                        TagsItem tagsItem=new TagsItem(tagContent,tagColor);

                        tagList.add(tagsItem);
                    }
                }
                task.setTags(tagList);


                JSONArray names=jsonObject.getJSONArray("participants");

                Log.v("names",type+"************"+names+"**************");
                List<String> nameList=new ArrayList<>();

                if(names.length()>0&&names!=null && !names.toString().equals("")){
                    for(int j=0;j<names.length();j++){
                        if(names.getJSONObject(j)!=null) {
                            JSONObject nameobject = names.getJSONObject(j);
                            if(!nameobject.isNull("userName")) {
                                String name = nameobject.getString("userName");
                                nameList.add(name);
                            }
                        }
                    }
                }
                task.setNames(nameList);

                String subTaskNum=jsonObject.getString("subTaskNum");
                String subTaskCompletedNum=jsonObject.getString("subTaskCompletedNum");
                task.setSubTaskCompletedNum(subTaskCompletedNum);
                task.setSubTaskNum(subTaskNum);


                /*
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
                */

                /*
                SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
                String endTime=jsonObject.getString("taskEndTime");
                Date endDate=formatter.parse(endTime);
                Date nowDate = new Date(System.currentTimeMillis());
                Calendar c1=Calendar.getInstance();
                c1.setTime(nowDate);
                Calendar c2=Calendar.getInstance();
                c2.setTime(endDate);
                int days = c2.get(Calendar.DAY_OF_YEAR) - c1.get(Calendar.DAY_OF_YEAR);

                 */

                int days=jsonObject.getInt("taskDays");
                task.setDays(days);

                //0未完成 1完成
                int status=jsonObject.getInt("taskStatus");
                task.setComplete(status);
                //筛选
                if(status==0){
                    //if (task.getTaskTitle().contains(titleLimit))
                        mtasks.add(task);
                }else if(status==1){
                    //if (task.getTaskTitle().contains(titleLimit))
                        completedTasks.add(task);
                }

            }

            Log.v("test",type+"/////88888"+mtasks);
            Log.v("test",type+"////88888"+completedTasks);


        }catch (Exception e){
            e.printStackTrace();
        }finally {

        }
    }

    public void completeTask(TaskItem task,int position){
        mtasks.remove(position);
        task.setComplete(1);
        completedTasks.add(task);
        adapter.setData(mtasks);
        cadapter.setData(completedTasks);
    }

    private void removeLimit(){
        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        String titleLimit = sharedPreferences.getString("titleLimit","");
        Log.d("limit",titleLimit);
        for (int i = mtasks.size()-1;i >= 0;i--) {
            TaskItem mtask = mtasks.get(i);
            if (!mtask.getTaskTitle().contains(titleLimit)){
                mtasks.remove(i);
            }
        }
        for (int i = completedTasks.size()-1;i >= 0;i--) {
            TaskItem mtask = completedTasks.get(i);
            if (!mtask.getTaskTitle().contains(titleLimit)){
                completedTasks.remove(i);
            }
        }


        Set<String>tags=sharedPreferences.getStringSet("tags",null);
        Set<String>names=sharedPreferences.getStringSet("names",null);

        if(tags!=null &&tags.size()>0){
            for (int i=0;i<mtasks.size();i++) {
                TaskItem mtask = mtasks.get(i);
                int num=mtask.getTags().size();

                int flag=0;

                for(int j=0;j<num;j++){
                    for(String tag:tags){
                        if(mtask.getTags().get(j).tagContent.contains(tag)){
                            flag=1;
                        }
                    }
                }

                if(flag==0)
                    mtasks.remove(i);
            }


            for (int i=0;i<completedTasks.size();i++) {
                TaskItem mtask = completedTasks.get(i);
                int num=mtask.getTags().size();
                int flag=0;
                for(int j=0;j<num;j++){
                    for(String tag:tags){
                        if(mtask.getTags().get(j).tagContent!=""&&mtask.getTags().get(j).tagContent.contains(tag)){
                            flag=1;
                        }
                    }
                }

                if(flag==0)
                    completedTasks.remove(i);
            }
        }

        if(names!=null&&names.size()>0){
            for (int i = mtasks.size()-1;i >= 0;i--) {
                TaskItem mtask = mtasks.get(i);
                int num=mtask.getNames().size();
                int flag=0;
                for(int j=0;j<num;j++){
                    for(String name:names){
                        if(mtask.getNames().get(j).contains(name)){
                            flag=1;
                        }
                    }
                }

                if(flag==0)
                    mtasks.remove(i);
            }

            for (int i = completedTasks.size()-1;i >= 0;i--) {
                TaskItem mtask = completedTasks.get(i);
                int num=mtask.getNames().size();
                int flag=0;
                for(int j=0;j<num;j++){
                    for(String name:names){
                        if(mtask.getNames().get(j).contains(name)){
                            flag=1;
                        }
                    }
                }

                if(flag==0)
                    completedTasks.remove(i);
            }
        }


        adapter.notifyDataSetChanged();
        cadapter.notifyDataSetChanged();


        Log.d("limittitle",titleLimit);
        //Log.d("limittag",tags.toString());
        //Log.d("limitname",names.toString());

    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
        removeLimit();
    }
}