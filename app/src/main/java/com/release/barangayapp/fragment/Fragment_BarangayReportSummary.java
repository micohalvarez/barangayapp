package com.release.barangayapp.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.release.barangayapp.R;
import com.release.barangayapp.model.SummaryReport;
import com.release.barangayapp.service.SummaryReportService;
import com.release.barangayapp.view.BarangayCreateSummaryActivity;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_BarangayReportSummary#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_BarangayReportSummary extends Fragment {

    private SummaryReportService summaryReportService;
    ArrayList<SummaryReport> reportHolder;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button Button_update;

    public Fragment_BarangayReportSummary() {
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
    public static Fragment_BarangayReportSummary newInstance(String param1, String param2) {
        Fragment_BarangayReportSummary fragment = new Fragment_BarangayReportSummary();
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
        View v= inflater.inflate(R.layout.fragment__barangay_report_summary, container, false);
        reportHolder = new ArrayList<>();
        summaryReportService = new SummaryReportService();
        summaryReportService.getData(value -> {
            reportHolder = value;
            TextView Probable = v.findViewById(R.id.barangay_summary_probable);
            TextView Confirmed = v.findViewById(R.id.barangay_summary_confirmed);
            TextView Suspect = v.findViewById(R.id.barangay_summary_suspect);
            TextView Date = v.findViewById(R.id.barangay_summary_date_layout);

            Probable.setText(reportHolder.get(0).getProbable());
            Confirmed.setText(reportHolder.get(0).getConfirmed());
            Suspect.setText(reportHolder.get(0).getSuspect());
            Date.setText(reportHolder.get(0).getSummaryDateView());


        });


        Button_update = v.findViewById(R.id.Summary_button);

        Button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), BarangayCreateSummaryActivity.class));
            }
        });
        return v;
    }


}