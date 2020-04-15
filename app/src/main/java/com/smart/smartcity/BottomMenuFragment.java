package com.smart.smartcity;

import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BottomMenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BottomMenuFragment extends Fragment {
    BottomNavigationView bottomMenu = null;

    public BottomMenuFragment() {
        // Required empty public constructor
    }

    public static BottomMenuFragment newInstance() {
        BottomMenuFragment fragment = new BottomMenuFragment();

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
        View view = inflater.inflate(R.layout.fragment_bottom_menu, container, false);

        bottomMenu = view.findViewById(R.id.bottom_menu);

        //set the home as default
        bottomMenu.setSelectedItemId(R.id.home_icon);

        //add action listener
        bottomMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId()){

                    case R.id.home_icon:
                        Toast.makeText(getActivity().getBaseContext(),"Home icon clicked",Toast.LENGTH_SHORT).show();
                    case R.id.news_icon:
                        Toast.makeText(getActivity().getBaseContext(),"news icon clicked",Toast.LENGTH_SHORT).show();
                    case R.id.trade_icon:
                        Toast.makeText(getActivity().getBaseContext(),"Trade icon Clicke",Toast.LENGTH_SHORT).show();
                    case R.id.network_icon:
                        Toast.makeText(getActivity().getBaseContext(),"Network Icon Clicked",Toast.LENGTH_SHORT).show();

                }

                return true;
            }
        });

        return view;
    }
}
