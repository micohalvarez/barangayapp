package com.release.barangayapp.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.release.barangayapp.fragment.Fragment_BarangayChat_Chats;
import com.release.barangayapp.fragment.Fragment_BarangayChat_Users;


public class FragmentBarangayChatAdapter extends FragmentStateAdapter {
    public FragmentBarangayChatAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new Fragment_BarangayChat_Chats();
            case 1:
                return new Fragment_BarangayChat_Users();
            default:return null;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
