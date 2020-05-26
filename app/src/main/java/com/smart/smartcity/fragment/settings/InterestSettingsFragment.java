package com.smart.smartcity.fragment.settings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.smart.smartcity.R;
import com.smart.smartcity.activity.MainActivity;
import com.smart.smartcity.adapters.InterestSettingsAdapter;
import com.smart.smartcity.context.settings.IInterestListContext;
import com.smart.smartcity.context.settings.IProfileUpdateContext;
import com.smart.smartcity.dao.InterestDAO;
import com.smart.smartcity.dao.UserDAO;
import com.smart.smartcity.model.Interest;
import com.smart.smartcity.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class InterestSettingsFragment extends Fragment implements IInterestListContext, View.OnClickListener, IProfileUpdateContext {
    private User user;
    private InterestSettingsAdapter interestSettingsAdapter = null;
    private ListView interestListView = null;
    private List<Interest> interests = new ArrayList<>();
    private TextView interestSettingsStatus;
    private ArrayList<Integer> userInterests;
    private Button updateInterestButton;

    public InterestSettingsFragment() {
        // Required empty public constructor
    }

    public static InterestSettingsFragment  newInstance() {
        return (new InterestSettingsFragment());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_interest_settings, container, false);
        user = ((MainActivity) getActivity()).getUser();
        userInterests = new ArrayList<>(user.getInterests());

        interestSettingsStatus = view.findViewById(R.id.interest_settings_status);

        // Find interest from web API
        InterestDAO dao = new InterestDAO();
        dao.setInterestListContext(this);
        dao.findInterests();

        // Initialize listView
        interestListView = view.findViewById(R.id.interest_settings_list);
        interestSettingsAdapter = new InterestSettingsAdapter(getActivity().getApplicationContext(), interests, userInterests);
        interestListView.setAdapter(interestSettingsAdapter);

        updateInterestButton = view.findViewById(R.id.update_interest_btn);
        updateInterestButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onGetInterestsSuccessful(List<Interest> interests) {
        this.interests.clear();
        this.interests.addAll(interests);
        interestSettingsAdapter.clear();
        interestSettingsAdapter.addAll(interests);
        interestSettingsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onGetInterestsFailure() {
        Log.e("interests", "Failed to get interests");
        interestSettingsStatus.setText("Error : Can't get list of interests");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.update_interest_btn) {
            user.setInterests(userInterests);

            UserDAO dao = new UserDAO();
            dao.setProfileUpdateContextContext(this);
            dao.update(user);
            System.out.println(((MainActivity) getActivity()).getUser().getInterests().size());
        }
    }

    @Override
    public void onUpdateSuccessful(User user) {
        interestSettingsStatus.setText("Update successful !");
    }

    @Override
    public void onUpdateFailure() {
        Log.e("interests", "Failed to update user interests");
        interestSettingsStatus.setText("Error : Can't update user interests");
    }
}
