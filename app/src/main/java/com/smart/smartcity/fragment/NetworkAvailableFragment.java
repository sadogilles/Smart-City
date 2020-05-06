package com.smart.smartcity.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smart.smartcity.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NetworkAvailableFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NetworkAvailableFragment extends Fragment {


    public NetworkAvailableFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static NetworkAvailableFragment newInstance() {
        NetworkAvailableFragment fragment = new NetworkAvailableFragment();

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
        return inflater.inflate(R.layout.fragment_network_available, container, false);
    }
}
