package com.release.barangayapp.adapter;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.release.barangayapp.Fragment_Announcement;
import com.release.barangayapp.Fragment_Notification;

public class FragmentAdapter extends FragmentStateAdapter {
    public FragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position)
        {
            case 0:
                return new Fragment_Notification();
            case 1:
                return new Fragment_Announcement();
            default: return null;
        }

    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
