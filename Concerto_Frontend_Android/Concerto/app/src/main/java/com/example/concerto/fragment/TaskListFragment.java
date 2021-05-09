package com.example.concerto.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.concerto.R;
import com.example.concerto.bean.TaskItem;
import com.example.concerto.adapter.TaskAdapter;

import java.util.ArrayList;
import java.util.List;

public class TaskListFragment extends Fragment {

    private List<TaskItem> mtasks=new ArrayList<>();
    private List<TaskItem> completedTasks=new ArrayList<>();



    int type;//0==推荐，1==今日，2==本周，3==本月

    public TaskListFragment(int type) {
        this.type=type;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDatas();

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

        TaskAdapter adapter=new TaskAdapter(mtasks,this.getContext());
        TaskAdapter cadapter=new TaskAdapter(completedTasks,this.getContext());

        recyclerView.setAdapter(adapter);
        recyclerViewComplete.setAdapter(cadapter);

        

        return view;
    }

    public void initDatas(){
        String[] tags={"计算机","云计算","服务器"};
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
}