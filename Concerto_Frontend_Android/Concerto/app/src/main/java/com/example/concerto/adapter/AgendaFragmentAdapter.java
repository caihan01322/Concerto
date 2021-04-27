package com.example.concerto.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.concerto.fragment.TaskListFragment;

import java.util.ArrayList;
import java.util.List;

public class AgendaFragmentAdapter extends FragmentPagerAdapter {
    private List<String> titles;

    public AgendaFragmentAdapter(FragmentManager fm) {
        super(fm);
        this.titles = new ArrayList<>();
    }

    public void setList(List<String> datas) {
        this.titles.clear();
        this.titles.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment=new TaskListFragment();
        return fragment;
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String plateName = titles.get(position);
        if (plateName == null) {
            plateName = "";
        } else if (plateName.length() > 8) {
            plateName = plateName.substring(0, 8) ;
        }
        return plateName;
    }
}


