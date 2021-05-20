package com.release.barangayapp.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.release.barangayapp.Fragment_CovidUserSummary;
import com.release.barangayapp.Fragment_CovidUserUpdate;
import com.release.barangayapp.Fragment_ReportSummary;
import com.release.barangayapp.Fragment_ReportUpdates;

public class FragmentCovidUserProfileAdapter extends FragmentStateAdapter {

    public FragmentCovidUserProfileAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 0:
                return new Fragment_CovidUserSummary();
            case 1:
                return new Fragment_CovidUserUpdate();
            default: return null;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

}
