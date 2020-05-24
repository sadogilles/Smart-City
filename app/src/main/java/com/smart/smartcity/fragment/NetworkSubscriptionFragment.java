package com.smart.smartcity.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.smart.smartcity.R;
import com.smart.smartcity.activity.MainActivity;
import com.smart.smartcity.adapters.NetworkListAdapter;
import com.smart.smartcity.context.IDownloadImageContext;
import com.smart.smartcity.context.INetworkListContext;
import com.smart.smartcity.dao.NetworkDAO;
import com.smart.smartcity.model.Network;
import com.smart.smartcity.model.User;
import com.smart.smartcity.util.DownloadImageTask;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NetworkSubscriptionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NetworkSubscriptionFragment extends Fragment implements INetworkListContext, IDownloadImageContext {
    private ListView networksListView;
    private NetworkListAdapter networkListAdapter;
    private List<Network> networks = new ArrayList<>();
    private TextView networkSubscribedStatus;

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
        View view = inflater.inflate(R.layout.fragment_network_subscription, container, false);

        User user = ((MainActivity) getActivity()).getUser();

        NetworkDAO dao = new NetworkDAO();
        dao.setNetworkListContext(this);
        dao.findSubscribedNetwork(user);

        networkSubscribedStatus = view.findViewById(R.id.network_subscribed_status);
        networksListView = view.findViewById(R.id.subscribed_networks_list_view);
        networkListAdapter = new NetworkListAdapter(getActivity().getApplicationContext(), networks, (MainActivity) getActivity(), user);
        networksListView.setAdapter(networkListAdapter);


        return view;
    }

    @Override
    public void onGetNetworksSuccessful(List<Network> networks) {
        this.networks.clear();
        this.networks.addAll(networks);
        networkListAdapter.clear();
        networkListAdapter.addAll(networks);
        networkListAdapter.notifyDataSetChanged();

        System.out.println("size : " + networks.size());

        for (Network network : networks) {
            new DownloadImageTask(this, network.getId()).execute(network.getImageUrl());
        }
    }

    @Override
    public void onGetNetworksFailure() {
        networkSubscribedStatus.setText("Error : can't get subscribed network list");
    }

    @Override
    public void onImageDownloaded(Bitmap bitmap, int id) {
        Network network = null;

        for (Network n : networks) {
            if (n.getId() == id) {
                network = n;
            }
        }

        if (network != null) {
            network.setImageBitmap(bitmap);
            networkListAdapter.notifyDataSetChanged();
        }
    }
}
