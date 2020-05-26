package com.smart.smartcity.fragment.networks;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.smart.smartcity.R;
import com.smart.smartcity.activity.MainActivity;
import com.smart.smartcity.adapters.NetworkPageAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NetworkFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NetworkFragment extends Fragment{


    public NetworkFragment() {
        // Required empty public constructor
    }


    public static NetworkFragment newInstance() {
        NetworkFragment fragment = new NetworkFragment();

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
        View v = inflater.inflate(R.layout.fragment_network, container, false);

        return v;

    }

    @Override
    public void onResume() {
        super.onResume();

        ((MainActivity) getActivity()).updateBottomMenu(R.id.network_icon);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        TabLayout tabs = view.findViewById(R.id.network_tab);
        ViewPager pager = view.findViewById(R.id.network_pager);

        pager.setAdapter(new NetworkPageAdapter(getChildFragmentManager()));

        tabs.setupWithViewPager(pager);
        tabs.setTabMode(TabLayout.MODE_FIXED);

        super.onViewCreated(view, savedInstanceState);
    }
}
