package com.smart.smartcity.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smart.smartcity.R;
import com.smart.smartcity.activity.LoginActivity;
import com.smart.smartcity.model.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TradeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TradeFragment extends Fragment {


    public TradeFragment() {
        // Required empty public constructor
    }


    public static TradeFragment newInstance(User user) {
        TradeFragment fragment = new TradeFragment();
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
        return inflater.inflate(R.layout.fragment_trade, container, false);
    }
}
