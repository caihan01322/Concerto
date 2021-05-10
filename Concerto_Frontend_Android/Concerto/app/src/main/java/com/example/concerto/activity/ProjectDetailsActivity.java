package com.example.concerto.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

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

import java.util.ArrayList;
import java.util.List;

public class ProjectDetailsActivity extends AppCompatActivity implements View.OnClickListener{
    TabLayout tabLayout;
    ViewPager viewPager;
    TextView tv_title;
    ImageView iv_back;
    ImageView iv_select;//弹出筛选界面
    View view;
    private PagerAdapter adapter;
    private List<String> titles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_details);
        iv_back=findViewById(R.id.iv_project_details_back);
        tv_title=findViewById(R.id.tv_project_details_title);
        iv_select=findViewById(R.id.iv_project_details_select);
        viewPager=findViewById(R.id.view_pager_project_details_title);
        tabLayout=findViewById(R.id.tab_project_details_layout_content);
        iv_select.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        titles = new ArrayList<>();
        titles.add("本周");
        titles.add("全部");
        titles.add("详情");
        titles.add("人员");
        adapter=new ProjectDetailsAdapter(getSupportFragmentManager(),titles);
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_project_details_select:
                PopWindowUtil popWindow = new PopWindowUtil(this,R.layout.popwindow_select,280,0,0);
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
}