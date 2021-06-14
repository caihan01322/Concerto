package com.example.concerto.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import com.example.concerto.activity.ProjectDetailsActivity;
import com.example.concerto.adapter.SubProjectAdapter;
import com.example.concerto.bean.SubProject;
import com.example.concerto.R;
import com.example.concerto.popwindow.PopWindowAddInMyProject;
import com.example.concerto.popwindow.PopWindowTest;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MyProjectFragment extends Fragment implements View.OnClickListener {

    private ImageView imageView;
    private List<SubProject> list = new ArrayList<>();
    SubProjectAdapter subProjectAdapter;
    private RecyclerView recyclerView;
    private TextView titleTextView1;
    private TextView titleTextView2;
    private TextView titleTextView3;
    private TextView titleTextView4;
    private TextView descriptonTextView1;
    private TextView descriptonTextView2;
    private TextView descriptonTextView3;
    private TextView descriptonTextView4;
    private Context context;
    private View view;

    public MyProjectFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_project, container, false);
        imageView = view.findViewById(R.id.addImageView);
        imageView.setOnClickListener(this);
        titleTextView1 = view.findViewById(R.id.titileTextView1);
        titleTextView2 = view.findViewById(R.id.titileTextView2);
        titleTextView3 = view.findViewById(R.id.titileTextView3);
        titleTextView4 = view.findViewById(R.id.titileTextView4);

        descriptonTextView1 = view.findViewById(R.id.descriptionTextView1);
        descriptonTextView2 = view.findViewById(R.id.descriptionTextView2);
        descriptonTextView3 = view.findViewById(R.id.descriptionTextView3);
        descriptonTextView4 = view.findViewById(R.id.descriptionTextView4);

        initList();
        recyclerView = (RecyclerView)view.findViewById(R.id.ProjectRecyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        return view;
    }

    private void initList(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4In0.9i1K1-3jsGh3tbTh2eMmD64C3XOE-vX9c1JywsqSoT0";
                    OkHttpClient client=new OkHttpClient();
                    Request.Builder reqBuild = new Request.Builder();
                    reqBuild.addHeader("token",token);
                    HttpUrl.Builder urlBuilder = HttpUrl.parse("http://81.69.253.27:7777//Project")
                            .newBuilder();
                    reqBuild.url(urlBuilder.build());

                    Request request = reqBuild.build();

                    Response response = client.newCall(request).execute();
                    String data=response.body().string();
                    if(data != null && data.startsWith("\ufeff"))
                    {
                        data =  data.substring(1);
                    }

                    JSONObject jsonObject=new JSONObject(data);
                    JSONArray jsonArray=(JSONArray)jsonObject.getJSONArray("data");

                    for (int j = 0; j < jsonArray.length(); j++)
                    {
                        JSONObject jsonObject2 = jsonArray.getJSONObject(j);
                        String projectName = jsonObject2.getString("projectName");
                        String projectDescription = jsonObject2.getString("projectDescription");
                        String projectStartTime = jsonObject2.getString("projectStartTime");

                        JSONObject adminJsonObject = jsonObject2.getJSONObject("admin");
                        String laucher = null;
                        laucher = adminJsonObject.getString("userName");
                        SubProject subProject = new SubProject(projectName,projectDescription,"发起人："+laucher,projectStartTime,context,jsonObject2);
                        list.add(subProject);

                    }

                    subProjectAdapter = new SubProjectAdapter(list);
                    recyclerView.setAdapter(subProjectAdapter);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (list.size()>0)
                            {
                                for (int i=0;i<list.size();i++)
                                {
                                    if (i==0)
                                    {
                                        SubProject subProject = list.get(i);
                                        String name = subProject.getName();
                                        String description = subProject.getDescription();
                                        titleTextView1.setText(name);
                                        titleTextView1.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Intent intent = new Intent(context,ProjectDetailsActivity.class);
                                                intent.putExtra("data",subProject.getJsonObject().toString());
                                                startActivity(intent);
                                            }
                                        });
                                        descriptonTextView1.setText(description);
                                    }
                                    else if (i==1)
                                    {
                                        SubProject subProject = list.get(i);
                                        String name = subProject.getName();
                                        String description = subProject.getDescription();
                                        titleTextView2.setText(name);
                                        titleTextView2.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Intent intent = new Intent(context, ProjectDetailsActivity.class);
                                                intent.putExtra("data",subProject.getJsonObject().toString());
                                                startActivity(intent);
                                            }
                                        });
                                        descriptonTextView2.setText(description);
                                    }
                                    else if (i==2)
                                    {
                                        SubProject subProject = list.get(i);
                                        String name = subProject.getName();
                                        String description = subProject.getDescription();
                                        titleTextView3.setText(name);
                                        titleTextView3.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Intent intent = new Intent(context, ProjectDetailsActivity.class);
                                                intent.putExtra("data",subProject.getJsonObject().toString());
                                                startActivity(intent);
                                            }
                                        });
                                        descriptonTextView3.setText(description);
                                    }
                                    else if (i==3)
                                    {
                                        SubProject subProject = list.get(i);
                                        String name = subProject.getName();
                                        String description = subProject.getDescription();
                                        titleTextView4.setText(name);
                                        titleTextView4.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Intent intent = new Intent(context, ProjectDetailsActivity.class);
                                                intent.putExtra("data",subProject.getJsonObject().toString());


                                                Log.v("testDataMyP","****************"+subProject.getJsonObject().toString());
                                                startActivity(intent);
                                            }
                                        });
                                        descriptonTextView4.setText(description);
                                    }
                                }
                            }
                        }
                    });

                }catch (Exception e){
                    e.printStackTrace();

                }
            }
        }).start();
    }

    @Override
    public void onClick(View view) {
        PopWindowTest popWindowAddInMyProject = new PopWindowTest(getActivity());
        popWindowAddInMyProject.setOnDismissListener(() -> {
            WindowManager.LayoutParams lp1 = getActivity().getWindow().getAttributes();
            lp1.alpha = 1f;
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            getActivity().getWindow().setAttributes(lp1);
        });
        popWindowAddInMyProject.showPopupWindow(view.findViewById(R.id.addImageView));
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = 0.4f; //设置透明度
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getActivity().getWindow().setAttributes(lp);
    }
}