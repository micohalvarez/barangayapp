package com.release.barangayapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.release.barangayapp.R;
import com.release.barangayapp.model.UpdateReport;
import com.release.barangayapp.service.UpdateReportService;
import com.release.barangayapp.view.BarangayCreateUpdatesActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_BarangayReportUpdates#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_BarangayReportUpdates extends Fragment {

    private UpdateReportService updateReportService;
    ArrayList<UpdateReport> reportHolder;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button Button_update;

    public Fragment_BarangayReportUpdates() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_ReportUpdates.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_BarangayReportUpdates newInstance(String param1, String param2) {
        Fragment_BarangayReportUpdates fragment = new Fragment_BarangayReportUpdates();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment__report_updates, container, false);


        //Error if this part is included
        //Comment for now. its causing an error. 
        View u= inflater.inflate(R.layout.fragment__barangay_report_updates, container, false);
        reportHolder = new ArrayList<>();
        updateReportService = new UpdateReportService();
        updateReportService.getData(value -> {
            reportHolder = value;
            TextView Confirmed = u.findViewById(R.id.barangay_updates_confirmed);
            TextView Recovered = u.findViewById(R.id.barangay_updates_recovered);
            TextView Death = u.findViewById(R.id.barangay_updates_deaths);
            TextView Date = u.findViewById(R.id.barangay_update_date_layout);

           Recovered.setText(reportHolder.get(0).getRecovered());
            Confirmed.setText(reportHolder.get(0).getConfirmed());
            Death.setText(reportHolder.get(0).getDeaths());
            Date.setText(reportHolder.get(0).getUpdateDateView());


        });

        Button_update = u.findViewById(R.id.Updates_button);

        Button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View u) {

                startActivity(new Intent(getActivity(), BarangayCreateUpdatesActivity.class));
            }
        });
        return u;
    }
}