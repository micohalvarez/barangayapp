package com.release.barangayapp.adapter;

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

import com.google.firebase.auth.FirebaseUser;
import com.release.barangayapp.R;
import com.release.barangayapp.model.Message;
import com.release.barangayapp.service.AuthService;

import java.util.List;

public class ChatsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements View.OnClickListener, Filterable {


    public static final int IS_SENDER = 0;
    public static final int IS_RECEIVER = 1;

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    public List<Message> mItemList;
    private OnChatListener onChatListener;

    private AuthService authService;
    public ChatsAdapter(List<Message> itemList, OnChatListener onChatListener) {
        mItemList = itemList;
        authService = new AuthService();
        this.onChatListener = onChatListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        System.out.println(viewType);
        if (viewType == IS_SENDER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item_sender, parent, false);
            view.setOnClickListener(this);
            return new ItemViewHolder(view, this.onChatListener);
        } else if( viewType == IS_RECEIVER){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item_receiver, parent, false);
            view.setOnClickListener(this);
            return new ItemViewHolder(view, this.onChatListener);
        }
        else{
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
        FirebaseUser user = authService.getAuthUser();

        if(mItemList.get(position).getSender().equals(user.getUid())){
            return IS_SENDER;
        }
        else return IS_RECEIVER;

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

        TextView showMessage;
        TextView userIcon;
        OnChatListener onChatListener;

        public ItemViewHolder(@NonNull View itemView, OnChatListener onChatListener) {
            super(itemView);


            this.onChatListener = onChatListener;

            showMessage = itemView.findViewById(R.id.show_message);
            userIcon = itemView.findViewById(R.id.user_chat_icon);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            onChatListener.onChatClick(getAdapterPosition());
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


        Message item = mItemList.get(position);

        viewHolder.showMessage.setText(item.getMessage());



    }


    public interface OnChatListener {
        void onChatClick(int position);
    }


}