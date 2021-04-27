package com.example.concerto.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.concerto.R;
import com.example.concerto.adapter.AgendaFragmentAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class PersonalAgendaFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager viewPager;

    private AgendaFragmentAdapter adapter;
    private List<String> names;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_agenda, container, false);

        adapter = new AgendaFragmentAdapter(getChildFragmentManager());
        tabLayout=view.findViewById(R.id.tab_layout);
        viewPager=view.findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        // 更新适配器数据
        adapter.setList(names);
        return view;
    }

    private void initData() {
        names = new ArrayList<>();
        names.add("推荐");
        names.add("今日");
        names.add("本周");
        names.add("本月");
    }

}