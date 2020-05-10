package com.smart.smartcity.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.material.textfield.TextInputEditText;
import com.smart.smartcity.R;
import com.smart.smartcity.activity.MainActivity;
import com.smart.smartcity.adapters.NetworkListAdapter;
import com.smart.smartcity.adapters.SubscriptionListAdapter;
import com.smart.smartcity.model.Network;
import com.smart.smartcity.model.Subscription;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NetworkAdministrationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NetworkAdministrationFragment extends Fragment implements View.OnClickListener {
    private static final String NETWORK_KEY = "NETWORK_KEY";
    private Network network;
    private TextInputEditText nameField;
    private TextInputEditText descriptionField;
    private Button validateButton;
    private ListView subscriptionListView;
    private SubscriptionListAdapter subscriptionListAdapter;
    private List<Integer> acceptList = new ArrayList<>();
    private List<Integer> rejectList = new ArrayList<>();

    public NetworkAdministrationFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static NetworkAdministrationFragment newInstance(Network network) {
        NetworkAdministrationFragment fragment = new NetworkAdministrationFragment();
        Bundle args = new Bundle();
        args.putParcelable(NETWORK_KEY, network);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            network = getArguments().getParcelable(NETWORK_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_network_administration, container, false);

        nameField = view.findViewById(R.id.network_name_edit_field);
        descriptionField = view.findViewById(R.id.network_description_edit_field);
        validateButton = view.findViewById(R.id.validate_network_edition_btn);
        subscriptionListView = view.findViewById(R.id.subscription_list_view);
        validateButton = view.findViewById(R.id.validate_network_edition_btn);

        validateButton.setOnClickListener(this);

        List<Subscription> subscriptions = network.getSubscriptions();
        List<Subscription> pending = new ArrayList<>();

        for (Subscription subscription : subscriptions) {
            // TODO : use enumeration
            if (subscription.getState().equals("pending")) {
                pending.add(subscription);
            }
        }
        System.out.println("size2 : " + pending.size());
        subscriptionListAdapter = new SubscriptionListAdapter(getActivity().getApplicationContext(), pending);
        subscriptionListView.setAdapter(subscriptionListAdapter);

        nameField.setText(network.getName());
        descriptionField.setText(network.getDescription());

        return view;
    }

    @Override
    public void onClick(View v) {

    }
}
