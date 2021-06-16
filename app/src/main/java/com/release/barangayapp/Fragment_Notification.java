package com.release.barangayapp;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.release.barangayapp.adapter.EmergencyRecyclerViewAdapter;
import com.release.barangayapp.model.Emergency;
import com.release.barangayapp.service.EmergencyService;

import java.util.ArrayList;
import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Notification#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Notification extends Fragment implements EmergencyRecyclerViewAdapter.OnEmergencyListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_Notification() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Notification.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Notification newInstance(String param1, String param2) {
        Fragment_Notification fragment = new Fragment_Notification();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private RecyclerView recyclerView;
    private EmergencyRecyclerViewAdapter recyclerViewAdapter;
    private ArrayList<Emergency> emergencyList;
    private EmergencyService emergencyService;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        Log.e("TESt","hi");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment__notification, container, false);

        View view= inflater.inflate(R.layout.fragment__notification, container, false);

        recyclerView = view.findViewById(R.id.notif_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity().getApplicationContext(),DividerItemDecoration.VERTICAL));
        emergencyList = new ArrayList<>();
        emergencyService = new EmergencyService();

        initAdapter();

        return view;
    }

    private boolean isLoading = false;
    private int nextLimit = 11;

    public void initAdapter(){

        emergencyService.getData(value -> {
            emergencyList = value;
            System.out.println(emergencyList.size());
            initializeAdapter();
            initScrollListener();
        },10);

    }

    private void initializeAdapter() {

        recyclerViewAdapter = new EmergencyRecyclerViewAdapter(emergencyList,this );
        recyclerView.setAdapter(recyclerViewAdapter);
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
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == emergencyList.size() - 1) {
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
            emergencyList.add(null);
            recyclerViewAdapter.notifyDataSetChanged();
        });

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            emergencyList.remove(emergencyList.size() - 1);
            int scrollPosition = emergencyList.size();
            recyclerViewAdapter.notifyItemRemoved(scrollPosition);


            emergencyService.loadMoreData(value -> {
                emergencyList = value;
                recyclerViewAdapter.notifyDataSetChanged();
                isLoading = false;
            },nextLimit ,emergencyList);

        }, 2000);
    }


    private ImageView imageView;
    @Override
    public void onEmergencyClick(int position) {
        AlertDialog.Builder imageDialog = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);
        final AlertDialog dialog = imageDialog.create();

        View layout = inflater.inflate(R.layout.notification_detail,
                getView().findViewById(R.id.layout_notifroot));

        Button mStart = layout.findViewById(R.id.confirm_notif_button);

        imageView = layout.findViewById(R.id.CNotifIconType);


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
        TextView date = mView.findViewById(R.id.Resident_date);
        TextView message = mView.findViewById(R.id.emergency_details);
        TextView title = mView.findViewById(R.id.Emergency_title);
        TextView phone = mView.findViewById(R.id.Resident_phonenumber);


        date.setText(emergencyList.get(position).getCurrentDate());
        setDetailImage(emergencyList.get(position).getType());
        title.setText(emergencyList.get(position).getTitle());
        message.setText(emergencyList.get(position).getMessage());
        phone.setText(emergencyList.get(position).getPhonenumber());

    }
    private void setDetailImage(int iconType){
        String fileName = "fire";

        //add additional conditions based on file name
        if(iconType == 1){
            fileName = "fire";
            int imageResource = getResources().getIdentifier(fileName,"drawable", getActivity().getPackageName());
            Drawable res = getResources().getDrawable(imageResource);
            imageView.setImageDrawable(res);
        }
        if(iconType == 2){
            fileName = "medical";
            int imageResource = getResources().getIdentifier(fileName,"drawable", getActivity().getPackageName());
            Drawable res = getResources().getDrawable(imageResource);
            imageView.setImageDrawable(res);
        }
        if(iconType == 3){
            fileName = "crime";
            int imageResource = getResources().getIdentifier(fileName,"drawable", getActivity().getPackageName());
            Drawable res = getResources().getDrawable(imageResource);
            imageView.setImageDrawable(res);
        }
        if(iconType == 4){
            fileName = "accident";
            int imageResource = getResources().getIdentifier(fileName,"drawable", getActivity().getPackageName());
            Drawable res = getResources().getDrawable(imageResource);
            imageView.setImageDrawable(res);
        }



    }
}