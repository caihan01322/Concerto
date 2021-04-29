package com.example.concerto.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.concerto.R;
import com.example.concerto.adapter.AgendaFragmentAdapter;
import com.example.concerto.util.PopWindowUtil;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class PersonalAgendaFragment extends Fragment implements View.OnClickListener {

    TabLayout tabLayout;
    ViewPager viewPager;
    ImageView iv_select;//弹出筛选界面
    View view;

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
        iv_select.setOnClickListener(this);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        // 更新适配器数据
        adapter.setList(titles);
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