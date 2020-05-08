package com.smart.smartcity.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.smart.smartcity.R;
import com.smart.smartcity.model.Network;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NetworkDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NetworkDetailsFragment extends Fragment {
    private static final String NETWORK_KEY = "NETWORK";
    private Network network;

    public NetworkDetailsFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static NetworkDetailsFragment newInstance(Network network) {
        NetworkDetailsFragment fragment = new NetworkDetailsFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable(NETWORK_KEY, network);
        fragment.setArguments(bundle);

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
        View v = inflater.inflate(R.layout.network_details_fragment, container, false);

        network = (Network) getArguments().getParcelable(NETWORK_KEY);



        return v;
    }
}
