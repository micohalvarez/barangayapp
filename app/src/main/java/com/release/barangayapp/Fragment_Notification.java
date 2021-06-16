package com.release.barangayapp;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.release.barangayapp.adapter.EmergencyRecyclerViewAdapter;
import com.release.barangayapp.model.Emergency;
import com.release.barangayapp.service.EmergencyService;

import java.util.ArrayList;

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



    @Override
    public void onEmergencyClick(int position) {

    }
}