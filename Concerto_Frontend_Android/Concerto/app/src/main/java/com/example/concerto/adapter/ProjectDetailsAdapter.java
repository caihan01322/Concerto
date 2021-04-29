package com.example.concerto.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.concerto.fragment.MyInformationFragment;
import com.example.concerto.fragment.MyProjectFragment;
import com.example.concerto.fragment.PersonalAgendaFragment;
import com.example.concerto.fragment.TaskListFragment;

import java.util.ArrayList;
import java.util.List;

public class ProjectDetailsAdapter extends FragmentPagerAdapter {
    private List<String> titles;

    public ProjectDetailsAdapter(@NonNull FragmentManager fm) {
        super(fm);
        this.titles = new ArrayList<>();
    }

    public void setList(List<String> datas) {
        this.titles.clear();
        this.titles.addAll(datas);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new TaskListFragment();
                break;
            case 1:
                fragment = new TaskListFragment();
                break;
            case 2:
                fragment = new MyInformationFragment();
                break;
            case 3:
                fragment = new MyInformationFragment();
                break;
            default:
                break;
        }
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
