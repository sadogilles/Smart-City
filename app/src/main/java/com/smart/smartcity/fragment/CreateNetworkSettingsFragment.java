package com.smart.smartcity.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smart.smartcity.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateNetworkSettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateNetworkSettingsFragment extends Fragment {


    public CreateNetworkSettingsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CreateNetworkSettingsFragment newInstance() {
        CreateNetworkSettingsFragment fragment = new CreateNetworkSettingsFragment();

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
        return inflater.inflate(R.layout.fragment_create_network_settings, container, false);
    }
}
