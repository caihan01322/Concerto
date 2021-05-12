package com.example.concerto.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.concerto.fragment.ParticipantsFragment;
import com.example.concerto.fragment.TasksDetailsFragement;
import com.example.concerto.fragment.MyInformationFragment;

import com.example.concerto.fragment.TaskListFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ProjectDetailsAdapter extends FragmentPagerAdapter {
    private List<String> titles;
    JSONObject object;
    String projectId;

    public ProjectDetailsAdapter(@NonNull FragmentManager fm, List<String> datas, JSONObject jsonObject) {
        super(fm);
        this.titles = datas;
        object=jsonObject;
    }

    public void setList(List<String> datas) throws JSONException {
        this.titles.clear();
        this.titles.addAll(datas);
        projectId=object.getString("projectId");
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new TaskListFragment(4,2);
                break;
            case 1:
                fragment = new TaskListFragment(5,2);
                break;
            case 2:
                fragment = new TasksDetailsFragement(object);
                break;
            case 3:
                fragment = new ParticipantsFragment("2");
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
