package com.smart.smartcity.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smart.smartcity.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NetworkSubscriptionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NetworkSubscriptionFragment extends Fragment {

    public NetworkSubscriptionFragment() {
        // Required empty public constructor
    }

    public static NetworkSubscriptionFragment newInstance() {
        NetworkSubscriptionFragment fragment = new NetworkSubscriptionFragment();

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
        return inflater.inflate(R.layout.fragment_network_subscription, container, false);
    }
}
