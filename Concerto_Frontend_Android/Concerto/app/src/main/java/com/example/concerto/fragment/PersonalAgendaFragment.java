package com.example.concerto.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.example.concerto.R;
import com.example.concerto.activity.ProjectDetailsActivity;
import com.example.concerto.adapter.AgendaFragmentAdapter;
import com.example.concerto.adapter.ProjectDetailsAdapter;
import com.example.concerto.util.PopWindowUtil;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class PersonalAgendaFragment extends Fragment implements View.OnClickListener {

    TabLayout tabLayout;
    ViewPager viewPager;
    ImageView iv_select;//弹出筛选界面
    View view;
    Button test;

    private AgendaFragmentAdapter adapter;
    private List<String> titles;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_personal_agenda, container, false);

        adapter = new AgendaFragmentAdapter(getChildFragmentManager());
        tabLayout=view.findViewById(R.id.tab_layout_content);
        viewPager=view.findViewById(R.id.view_pager_title);
        iv_select=view.findViewById(R.id.iv_select);

        test=view.findViewById(R.id.test);

        
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), ProjectDetailsActivity.class);
                startActivity(intent);
            }
        });

        iv_select.setOnClickListener(this);
        adapter.setList(titles);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // 设置平滑切换
                viewPager.setCurrentItem(tab.getPosition(), true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;
    }

    private void initData() {
        titles = new ArrayList<>();
        titles.add("推荐");
        titles.add("今日");
        titles.add("本周");
        titles.add("本月");
    }


    @Override
    public void onClick(View v) {
        PopWindowUtil popWindow = new PopWindowUtil(this.getActivity(),R.layout.activity_select,180,0,0);
        popWindow.setOnDismissListener(() -> {
            WindowManager.LayoutParams lp1 = getActivity().getWindow().getAttributes();
            lp1.alpha = 1f;
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            getActivity().getWindow().setAttributes(lp1);
        });
        popWindow.showPopupWindow(view.findViewById(R.id.iv_select));
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = 0.8f; //设置透明度
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getActivity().getWindow().setAttributes(lp);

    }
}