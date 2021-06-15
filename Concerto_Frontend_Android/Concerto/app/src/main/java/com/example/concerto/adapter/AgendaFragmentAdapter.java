package com.example.concerto.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.concerto.fragment.TaskListFragment;

import java.util.ArrayList;
import java.util.List;

public class AgendaFragmentAdapter extends FragmentPagerAdapter {
    private List<String> titles;
    Fragment fragment0 = new TaskListFragment(0);
    Fragment fragment1 = new TaskListFragment(1);
    Fragment fragment2 = new TaskListFragment(2);
    Fragment fragment3 = new TaskListFragment(3);

    public AgendaFragmentAdapter(FragmentManager fm) {
        super(fm);
        this.titles = new ArrayList<>();
    }

    public void setList(List<String> datas) {
        this.titles.clear();
        this.titles.addAll(datas);
        notifyDataSetChanged();
    }

    public void refresh(){
        if (fragment0 instanceof TaskListFragment){
            ((TaskListFragment) fragment0).refresh();
        }
        if (fragment1 instanceof TaskListFragment){
            ((TaskListFragment) fragment1).refresh();
        }
        if (fragment2 instanceof TaskListFragment){
            ((TaskListFragment) fragment2).refresh();
        }
        if (fragment3 instanceof TaskListFragment){
            ((TaskListFragment) fragment3).refresh();
        }
    }

    @Override
    public Fragment getItem(int position) {
        //0==推荐，1==今日，2==本周，3==本月
        switch (position){
            case 0:
                fragment0 = new TaskListFragment(0);
                return fragment0;
            case 1:
                fragment1 = new TaskListFragment(1);
                return fragment1;
            case 2:
                fragment2 = new TaskListFragment(2);
                return fragment2;
            case 3:
                fragment3 = new TaskListFragment(3);
                return fragment3;
            default:
                break;
        }
        return fragment0;
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


