package com.smart.smartcity.fragment.settings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smart.smartcity.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AlarmSettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlarmSettingsFragment extends Fragment {


    public AlarmSettingsFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static AlarmSettingsFragment newInstance() {
        AlarmSettingsFragment fragment = new AlarmSettingsFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_alarm_settings, container, false);
    }
}
