package com.example.concerto.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.concerto.R;
import com.example.concerto.TaskItem;
import com.example.concerto.util.MyOkHhtpUtil;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TaskListFragment extends Fragment {

    private List<TaskItem> tasks=new ArrayList<>();

    public TaskListFragment() {

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
        return view;
    }

    public void initDatas(){
        URL url = null;
        MyOkHhtpUtil myOkHhtpUtil=new MyOkHhtpUtil(url);
        String data=myOkHhtpUtil.getData();
    }
}