package com.example.concerto.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.concerto.R;
import com.example.concerto.adapter.ProjectDetailsAdapter;
import com.example.concerto.util.PopWindowUtil;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.ArrayList;
import java.util.List;

public class ProjectDetailsActivity extends AppCompatActivity implements View.OnClickListener{
    TabLayout tabLayout;
    ViewPager viewPager;
    TextView tv_title;
    TextView tv_project_details_title;
    ImageView iv_back;
    ImageView iv_select;//弹出筛选界面
    View view;
    public String projectId="2";
    private PagerAdapter adapter;
    private List<String> titles;
    JSONObject jsonObject;



    public JSONObject getJsonObject() {
        return jsonObject;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_details);

        SharedPreferences sharedPreferences= getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("titleLimit", "");
        editor.commit();




        String jsonData=getIntent().getStringExtra("project");


        //测试
        jsonData="{" +
                "            \"projectId\": 2,\n" +
                "            \"projectName\": \"Edmundd\",\n" +
                "            \"projectDescription\": \"xxxxxxxxxxxx\",\n" +
                "            \"projectStartTime\": \"2021-05-06\",\n" +
                "            \"projectEndTime\": \"2021-05-07\",\n" +
                "            \"admin\": {\n" +
                "                \"userPhone\": null,\n" +
                "                \"userName\": \"Edmund\",\n" +
                "                \"userEmail\": \"1661135388@qq.com\",\n" +
                "                \"userIntroducton\": null\n" +
                "            }\n" +
                "        }";

        try {
            jsonObject=new JSONObject(jsonData);
            projectId=jsonObject.getString("projectId");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        iv_back=findViewById(R.id.iv_project_details_back);
        tv_title=findViewById(R.id.tv_project_details_title);
        iv_select=findViewById(R.id.iv_project_details_select);
        viewPager=findViewById(R.id.view_pager_project_details_title);
        tabLayout=findViewById(R.id.tab_project_details_layout_content);
        tv_project_details_title=findViewById(R.id.tv_project_details_title);
        try {
            tv_project_details_title.setText(jsonObject.getString("projectName"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        iv_select.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        titles = new ArrayList<>();
        titles.add("本周");
        titles.add("全部");
        titles.add("详情");
        titles.add("人员");
        try {
            adapter=new ProjectDetailsAdapter(getSupportFragmentManager(),titles,jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_project_details_select:
                PopWindowUtil popWindow = null;
                try {
                    popWindow = new PopWindowUtil(1,this,R.layout.popwindow_select,280,0,0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                popWindow.setOnDismissListener(() -> {
                    WindowManager.LayoutParams lp1 =getWindow().getAttributes();
                    lp1.alpha = 1f;
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                    getWindow().setAttributes(lp1);
                });
                popWindow.showPopupWindow(findViewById(R.id.iv_project_details_select));
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 0.8f; //设置透明度
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                getWindow().setAttributes(lp);
                break;
            case R.id.iv_project_details_back:
                Log.v("ProjectDetails","----------返回--------");
                this.finish();
                break;
            default:
                break;
        }

    }



    @Override
    protected void onStop() {
        SharedPreferences sharedPreferences= getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("titleLimit", "");
        editor.commit();
        super.onStop();
    }

    @Override
    protected void onResume() {
        SharedPreferences sharedPreferences= getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("titleLimit", "");
        editor.commit();
        super.onResume();
    }

    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences sharedPreferences= getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("titleLimit", "");
        editor.commit();
    }

}