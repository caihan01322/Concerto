package com.example.concerto.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.concerto.fragment.MyInformationFragment;
import com.example.concerto.fragment.MyProjectFragment;
import com.example.concerto.fragment.PersonalAgendaFragment;

public class MenuFragmentAdapter extends FragmentPagerAdapter {
    public MenuFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;
        switch (i) {
            case 0:
                fragment = new PersonalAgendaFragment();
                break;
            case 1:
                fragment = new MyProjectFragment();
                break;
            case 2:
                fragment = new MyInformationFragment();
                break;
            default:
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

}

