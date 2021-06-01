package com.release.barangayapp.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.release.barangayapp.Fragment_BarangayReportSummary;
import com.release.barangayapp.Fragment_BarangayReportUpdates;

public class FragmentReportAdapter extends FragmentStateAdapter {


    public FragmentReportAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 0:
                return new Fragment_BarangayReportSummary();
            case 1:
                return new Fragment_BarangayReportUpdates();
            default: return null;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
