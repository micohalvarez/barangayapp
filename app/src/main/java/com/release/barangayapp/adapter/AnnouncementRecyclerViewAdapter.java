package com.release.barangayapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.release.barangayapp.R;
import com.release.barangayapp.model.Announcement;

import java.util.ArrayList;
import java.util.List;

public class AnnouncementRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements View.OnClickListener, Filterable {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private List<Announcement> filteredStudents;
    public List<Announcement> mItemList;
    private OnStudentListener onStudentListener;

    public AnnouncementRecyclerViewAdapter(List<Announcement> itemList, OnStudentListener onAnnouncementListener) {
        mItemList = itemList;
        filteredStudents = mItemList;
        this.onStudentListener = onStudentListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.announcement_row, parent, false);
            view.setOnClickListener(this);
            return new ItemViewHolder(view, this.onStudentListener);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            view.setOnClickListener(this);
            return new LoadingViewHolder(view);
        }
    }

    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        if (viewHolder instanceof ItemViewHolder) {

            populateItemRows((ItemViewHolder) viewHolder, position);
        } else if (viewHolder instanceof LoadingViewHolder) {
            showLoadingView((LoadingViewHolder) viewHolder, position);
        }

    }

    @Override
    public int getItemCount() {
        return mItemList == null ? 0 : mItemList.size();
    }

    /**
     * The following method decides the type of ViewHolder to display in the
     * RecyclerView
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return mItemList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public Filter getFilter() {
        return null;
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvItem;
        TextView studentPref;

        OnStudentListener onStudentListener;

        public ItemViewHolder(@NonNull View itemView, OnStudentListener onStudentListener) {
            super(itemView);

            this.onStudentListener = onStudentListener;

            tvItem = itemView.findViewById(R.id.announcementItem);
            studentPref = itemView.findViewById(R.id.announcementPref);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            onStudentListener.onStudentClick(getAdapterPosition());
        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    private void showLoadingView(LoadingViewHolder viewHolder, int position) {
        // ProgressBar would be displayed

    }

    private void populateItemRows(ItemViewHolder viewHolder, int position) {

        Announcement item = mItemList.get(position);
        viewHolder.tvItem.setText(item.getTitle());
        // viewHolder.studentPref.setText(item.getFirstName().substring(0,1));
    }

    public interface OnStudentListener {
        void onStudentClick(int position);
    }

}