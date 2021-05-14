package com.release.barangayapp.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.release.barangayapp.announcement;
import com.release.barangayapp.notif;

public class PagerAdapter extends FragmentPagerAdapter {
    private int tabsNumbers;
    public PagerAdapter(@NonNull FragmentManager fm, int behavior,int tabs) {
        super(fm, behavior);
        this.tabsNumbers = tabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new notif();
            case 1:
                return new announcement();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return 0;
    }
}
