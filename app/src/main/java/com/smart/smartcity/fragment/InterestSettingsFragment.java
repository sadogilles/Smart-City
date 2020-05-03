package com.smart.smartcity.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smart.smartcity.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InterestSettingsFragment extends Fragment {

    public InterestSettingsFragment() {
        // Required empty public constructor
    }

    public static InterestSettingsFragment  newInstance() {
        return (new InterestSettingsFragment());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_interest_settings, container, false);
    }
}
