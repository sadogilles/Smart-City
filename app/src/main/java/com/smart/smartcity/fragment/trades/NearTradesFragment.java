package com.smart.smartcity.fragment.trades;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smart.smartcity.R;
import com.smart.smartcity.activity.MainActivity;
import com.smart.smartcity.model.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NearTradesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NearTradesFragment extends Fragment {
    private User user;

    public NearTradesFragment() {
        // Required empty public constructor
    }
    
    // TODO: Rename and change types and number of parameters
    public static NearTradesFragment newInstance() {
        NearTradesFragment fragment = new NearTradesFragment();

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
        View view = inflater.inflate(R.layout.fragment_near_trades, container, false);
        user = ((MainActivity) getActivity()).getUser();



        return view;
    }
}
