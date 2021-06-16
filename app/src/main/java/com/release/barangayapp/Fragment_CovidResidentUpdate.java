package com.release.barangayapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.release.barangayapp.model.UpdateReport;
import com.release.barangayapp.service.UpdateReportService;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_CovidResidentUpdate#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_CovidResidentUpdate extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private UpdateReportService updateReportService;
    ArrayList<UpdateReport> reportHolder;

    public Fragment_CovidResidentUpdate() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_CovidUserUpdate.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_CovidResidentUpdate newInstance(String param1, String param2) {
        Fragment_CovidResidentUpdate fragment = new Fragment_CovidResidentUpdate();
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
        View u= inflater.inflate(R.layout.fragment__covid_resident_update, container, false);

        reportHolder = new ArrayList<>();
        updateReportService = new UpdateReportService();
        updateReportService.getData(value -> {
            reportHolder = value;
            TextView Confirmed = u.findViewById(R.id.resident_updates_confirmed);
            TextView Recovered = u.findViewById(R.id.resident_updates_recovered);
            TextView Death = u.findViewById(R.id.resident_updates_deaths);
            TextView Date = u.findViewById(R.id.resident_update_date_layout);

            Recovered.setText(reportHolder.get(0).getRecovered());
            Confirmed.setText(reportHolder.get(0).getConfirmed());
            Death.setText(reportHolder.get(0).getDeaths());
            Date.setText(reportHolder.get(0).getUpdateDateView());


        });

        return u;
    }
}