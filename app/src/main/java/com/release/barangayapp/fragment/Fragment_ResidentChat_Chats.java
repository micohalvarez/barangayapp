package com.release.barangayapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.release.barangayapp.R;
import com.release.barangayapp.adapter.UsersAdapter;
import com.release.barangayapp.model.UserObject;
import com.release.barangayapp.service.AuthService;
import com.release.barangayapp.service.MessageService;
import com.release.barangayapp.service.UserService;
import com.release.barangayapp.view.MessageActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_BarangayChat_Chats#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_ResidentChat_Chats extends Fragment  implements UsersAdapter.OnUserListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_ResidentChat_Chats() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_BarangayChat_Chats.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_BarangayChat_Chats newInstance(String param1, String param2) {
        Fragment_BarangayChat_Chats fragment = new Fragment_BarangayChat_Chats();
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

    private RecyclerView recyclerView;
    private UsersAdapter recyclerViewAdapter;

    private ArrayList<UserObject> userList;
    private ArrayList<String> userIds;

    private MessageService messageService;
    private UserService userService;
    private AuthService authService;

    private String userId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment__barangay_chat__chats, container, false);

        authService = new AuthService();
        authService.getUserDetails(value ->  {
            userId = value.getUserId();

            recyclerView = view.findViewById(R.id.users_recycler_view);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.addItemDecoration(new DividerItemDecoration(getActivity().getApplicationContext(),DividerItemDecoration.VERTICAL));

            userList = new ArrayList<>();
            userIds = new ArrayList<>();
            messageService = new MessageService();
            userService = new UserService();

            initializeAdapter();
        });




        return view;
    }

    private boolean isLoading = false;
    private int nextLimit = 11;

//    private void initAdapter() {
//
//        messageService.getReceiverUsers(value -> {
//            userIds = value;
//
//            initializeAdapter();
//
//        },userId);
//
//    }

    private void initializeAdapter() {
        userService.getAdminUsers(value ->{
            userList = value;
            recyclerViewAdapter = new UsersAdapter(this.getContext(),userList,this );
            recyclerView.setAdapter(recyclerViewAdapter);
            initScrollListener();

        });


    }



    @Override
    public void onUserClick(int position) {

        Intent intent = new Intent(this.getContext(), MessageActivity.class);
        intent.putExtra("userId",userList.get(position).getUserId());
        this.getContext().startActivity(intent);
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
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == userList.size() - 1) {
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
            userList.add(null);
            recyclerViewAdapter.notifyDataSetChanged();
        });

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            userList.remove(userList.size() - 1);
            int scrollPosition = userList.size();
            recyclerViewAdapter.notifyItemRemoved(scrollPosition);

            userService.loadMoreAdminUsers(value -> {
                userList = value;
                recyclerViewAdapter.notifyDataSetChanged();
                isLoading = false;


            },nextLimit ,userList);

        }, 2000);
    }


}