package com.release.barangayapp.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.release.barangayapp.model.Announcement;

import java.util.ArrayList;
import java.util.List;

public class AnnouncementRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener, Filterable {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private List<Announcement> filteredAnnouncement;
    public List<Announcement> mItemList;
    private OnAnnouncementListener onAnnouncementListener;


    @Override
    public void onClick(View v) {

    }

    @Override
    public Filter getFilter() {
        return null;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public interface OnAnnouncementListener{
        void onAnnouncementClick(int position);
    }
}
