package com.release.barangayapp;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.release.barangayapp.adapter.AnnouncementRecyclerViewAdapter;
import com.release.barangayapp.model.Announcement;
import com.release.barangayapp.service.AnnouncementService;

import java.util.ArrayList;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * A simple {@link Fragment} subclass. Use the
 * {@link Fragment_Announcement#newInstance} factory method to create an
 * instance of this fragment.
 */
public class Fragment_Announcement extends Fragment implements AnnouncementRecyclerViewAdapter.OnAnnouncementListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView;
    private AnnouncementRecyclerViewAdapter recyclerViewAdapter;
    ArrayList<Announcement> announcementholder;
    private AnnouncementService announcementService;

    public Fragment_Announcement() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of this fragment using the
     * provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Announcement.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Announcement newInstance(String param1, String param2) {
        Fragment_Announcement fragment = new Fragment_Announcement();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment__announcement_recyclerview, container, false);
        recyclerView = view.findViewById(R.id.announcement_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity().getApplicationContext(),DividerItemDecoration.VERTICAL));
        announcementholder = new ArrayList<>();
        announcementService = new AnnouncementService();

        initAdapter();
        return view;
    }

    private boolean isLoading = false;
    private int nextLimit = 11;

    private void initAdapter() {

        announcementService.getSomeData(value -> {
            announcementholder = value;

            initializeAdapter();
            initScrollListener();
        },10);

    }

    private void initializeAdapter() {

        recyclerViewAdapter = new AnnouncementRecyclerViewAdapter(announcementholder,this );
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    private ImageView imageView;
    @Override
    public void onAnnouncementClick(int position) {


        AlertDialog.Builder imageDialog = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);
        final AlertDialog dialog = imageDialog.create();

        View layout = inflater.inflate(R.layout.announcement_detail,
              getView().findViewById(R.id.layout_announcementroot));

        Button mStart = layout.findViewById(R.id.confirm_announcement_button);

        imageView = layout.findViewById(R.id.CAnnounceIconType);


        mStart.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                recyclerViewAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        setAnnouncementDetails(layout,position);
        dialog.setView(layout);
        dialog.show();
    }

    private void setAnnouncementDetails(View mView,int position){
        TextView title = mView.findViewById(R.id.announcement_subject);
        TextView content = mView.findViewById(R.id.announcement_details);
        TextView date = mView.findViewById(R.id.announcement_date_layout);

        date.setText(announcementholder.get(position).getCurrentDate());
        setDetailImage(announcementholder.get(position).getIconValue());
        title.setText(announcementholder.get(position).getTitle());
        content.setText(announcementholder.get(position).getContent());

    }



    private void setDetailImage(int iconType){
        String fileName = "fire";

        //add additional conditions based on file name
        if(iconType == 0){
            fileName = "ann_fire";
            int imageResource = getResources().getIdentifier(fileName,"drawable", getActivity().getPackageName());
            Drawable res = getResources().getDrawable(imageResource);
            imageView.setImageDrawable(res);
        }
        if(iconType == 1){
            fileName = "ann_weather";
            int imageResource = getResources().getIdentifier(fileName,"drawable", getActivity().getPackageName());
            Drawable res = getResources().getDrawable(imageResource);
            imageView.setImageDrawable(res);
        }
        if(iconType == 2){
            fileName = "ann_covid";
            int imageResource = getResources().getIdentifier(fileName,"drawable", getActivity().getPackageName());
            Drawable res = getResources().getDrawable(imageResource);
            imageView.setImageDrawable(res);
        }
        if(iconType == 3){
            fileName = "ann_crime";
            int imageResource = getResources().getIdentifier(fileName,"drawable", getActivity().getPackageName());
            Drawable res = getResources().getDrawable(imageResource);
            imageView.setImageDrawable(res);
        }
        if(iconType == 4){
            fileName = "ann_news_a";
            int imageResource = getResources().getIdentifier(fileName,"drawable", getActivity().getPackageName());
            Drawable res = getResources().getDrawable(imageResource);
            imageView.setImageDrawable(res);
        }
        if(iconType == 5){
            fileName = "ann_health_b";
            int imageResource = getResources().getIdentifier(fileName,"drawable", getActivity().getPackageName());
            Drawable res = getResources().getDrawable(imageResource);
            imageView.setImageDrawable(res);
        }
        if(iconType == 6){
            fileName = "ann_accident_a";
            int imageResource = getResources().getIdentifier(fileName,"drawable", getActivity().getPackageName());
            Drawable res = getResources().getDrawable(imageResource);
            imageView.setImageDrawable(res);
        }


    }

    private void initScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == announcementholder.size() - 1) {
                        //bottom of list!
                        loadMore();
                        isLoading = true;
                    }
                }
            }
        });
    }


    private void loadMore() {
        recyclerView.post(() -> {
            announcementholder.add(null);
            recyclerViewAdapter.notifyDataSetChanged();
        });

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            announcementholder.remove(announcementholder.size() - 1);
            int scrollPosition = announcementholder.size();
            recyclerViewAdapter.notifyItemRemoved(scrollPosition);


            announcementService.loadMoreData(value -> {
                announcementholder = value;
                recyclerViewAdapter.notifyDataSetChanged();
                isLoading = false;
            },nextLimit ,announcementholder);

        }, 2000);
    }
}