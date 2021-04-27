package com.example.concerto.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.concerto.adapter.MenuFragmentAdapter;
import com.example.concerto.R;
import com.google.android.material.tabs.TabLayout;

public class NavActivity extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;
    private static final String TAG = "MainActivity";
    private final int[] TAB_TITLES = new int[]{R.string.menu_task, R.string.menu_project, R.string.menu_me};
    private final int[] TAB_IMGS = new int[]{R.drawable.agenda, R.drawable.project, R.drawable.me};
    private final int[] TAB_SELECTOR = new int[]{R.drawable.agenda_selectorxml,R.drawable.project_selectorxml,R.drawable.me_selectorxml};


    private PagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);

        initPagerView();

        viewPager=findViewById(R.id.view_pager);
        tabLayout=findViewById(R.id.tab_layout);

        setTab(tabLayout, getLayoutInflater(), TAB_TITLES, TAB_IMGS,TAB_SELECTOR);
    }

    //设置下方三个menu页面
    private void setTab(TabLayout tabLayout, LayoutInflater inflater, int[] tabTitlees, int[] tabImgs,int[] tabselect) {
        for (int i = 0; i < tabImgs.length; i++) {
            TabLayout.Tab tab = tabLayout.newTab();
            View view = inflater.inflate(R.layout.menu_item, null);
            tab.setCustomView(view);
            TextView tvTitle = (TextView) view.findViewById(R.id.txt_tab);
            //Log.d(TAG,tvTitle.toString());
            tvTitle.setText(tabTitlees[i]);
            ImageView imgTab = (ImageView) view.findViewById(R.id.img_tab);
            imgTab.setImageResource(tabselect[i]);
            tabLayout.addTab(tab);

        }
    }

    private void initPagerView() {
        adapter = new MenuFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

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
    }


}
