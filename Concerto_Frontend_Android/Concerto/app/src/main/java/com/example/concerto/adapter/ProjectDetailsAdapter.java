package com.example.concerto.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.concerto.fragment.ParticipantsFragment;
import com.example.concerto.fragment.TasksDetailsFragement;
import com.example.concerto.fragment.MyInformationFragment;

import com.example.concerto.fragment.TaskListFragment;

import java.util.List;

public class ProjectDetailsAdapter extends FragmentPagerAdapter {
    private List<String> titles;

    public ProjectDetailsAdapter(@NonNull FragmentManager fm,List<String> datas) {
        super(fm);
        this.titles = datas;
    }

    public void setList(List<String> datas) {
        this.titles.clear();
        this.titles.addAll(datas);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new TaskListFragment(0);
                break;
            case 1:
                fragment = new TaskListFragment(1);
                break;
            case 2:
                fragment = new TasksDetailsFragement();
                break;
            case 3:
                fragment = new ParticipantsFragment();
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
