package com.release.barangayapp.adapter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.release.barangayapp.R;
import com.release.barangayapp.model.Emergency;

import java.util.List;

public class EmergencyRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements View.OnClickListener, Filterable {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    public List<Emergency> mItemList;
    private OnEmergencyListener onEmergencyListener;

    public EmergencyRecyclerViewAdapter(List<Emergency> itemList, OnEmergencyListener onEmergencyListener) {
        mItemList = itemList;

        this.onEmergencyListener = onEmergencyListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notif_row, parent, false);
            view.setOnClickListener(this);
            return new ItemViewHolder(view, this.onEmergencyListener);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notif_row, parent, false);
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


    private ImageView imageView;
    private class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvItem;

        OnEmergencyListener onEmergencyListener;

        public ItemViewHolder(@NonNull View itemView, OnEmergencyListener onEmergencyListener) {
            super(itemView);

            this.onEmergencyListener = onEmergencyListener;

            tvItem = itemView.findViewById(R.id.notifItem);
            imageView = itemView.findViewById(R.id.notifPref);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            onEmergencyListener.onEmergencyClick(getAdapterPosition());
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

        Emergency item = mItemList.get(position);

        viewHolder.tvItem.setText(item.getTitle());
        setDetailImage(mItemList.get(position). getType());
    }

    private void setDetailImage(int emergencyType) {

        //add additional conditions based on file name
        if (emergencyType == 1) {
            imageView.setImageResource(R.drawable.ann_fire);
        }
        if (emergencyType == 2) {
            imageView.setImageResource(R.drawable.ann_health_b);
        }
        if (emergencyType == 3) {
            imageView.setImageResource(R.drawable.ann_crime);
        }
        if (emergencyType == 4) {
            imageView.setImageResource(R.drawable.ann_accident_a);
        }
    }


    public interface OnEmergencyListener {
        void onEmergencyClick(int position);
    }



}