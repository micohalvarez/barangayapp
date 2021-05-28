package com.release.barangayapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.release.barangayapp.R;
import com.release.barangayapp.model.SummaryReport;
import com.release.barangayapp.service.SummaryReportService;
import com.release.barangayapp.view.CreateSummary;
import com.release.barangayapp.view.UserLogin;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_ReportSummary#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_ReportSummary extends Fragment {

    private SummaryReportService summaryReportService;
    private SummaryReport summaryReport;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button Button_update;

    public Fragment_ReportSummary() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_ReportSummary.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_ReportSummary newInstance(String param1, String param2) {
        Fragment_ReportSummary fragment = new Fragment_ReportSummary();
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
        View v= inflater.inflate(R.layout.fragment__report_summary, container, false);


        Button_update = v.findViewById(R.id.Summary_button);

        Button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), CreateSummary.class));
            }
        });
        return v;

    }


}