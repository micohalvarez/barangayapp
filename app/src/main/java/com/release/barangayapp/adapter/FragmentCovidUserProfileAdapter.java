package com.release.barangayapp.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.release.barangayapp.fragment.Fragment_CovidResidentSummary;
import com.release.barangayapp.fragment.Fragment_CovidResidentUpdate;

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
                return new Fragment_CovidResidentSummary();
            case 1:
                return new Fragment_CovidResidentUpdate();
            default: return null;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

}
