package com.smart.smartcity.fragment;

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
import com.smart.smartcity.activity.LoginActivity;
import com.smart.smartcity.adapters.SettingsPageAdapter;
import com.smart.smartcity.model.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {

    public static SettingsFragment newInstance(User user) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(LoginActivity.USER_KEY, user);
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
        return inflater.inflate(R.layout.fragment_settings, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        TabLayout tabs = view.findViewById(R.id.settings_tab);
        ViewPager pager = view.findViewById(R.id.settings_pager);

        User user = getArguments().getParcelable(LoginActivity.USER_KEY);

        pager.setAdapter(new SettingsPageAdapter(getChildFragmentManager(), user));

        tabs.setupWithViewPager(pager);
        tabs.setTabMode(TabLayout.MODE_FIXED);

        super.onViewCreated(view, savedInstanceState);
    }
}
