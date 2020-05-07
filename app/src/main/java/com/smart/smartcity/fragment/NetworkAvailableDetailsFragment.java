package com.smart.smartcity.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.smart.smartcity.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NetworkAvailableDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NetworkAvailableDetailsFragment extends Fragment {


    public NetworkAvailableDetailsFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static NetworkAvailableDetailsFragment newInstance() {
        NetworkAvailableDetailsFragment fragment = new NetworkAvailableDetailsFragment();

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
        View v = inflater.inflate(R.layout.fragment_network_available_details, container, false);

        LinearLayout linearForm = (LinearLayout)v.findViewById(R.id.publication_form);

            v.findViewById(R.id.toggle_publish_form).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //logic to show the form



                }
            });

    return v;
    }
}
