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
import com.smart.smartcity.adapters.InterestSettingsAdapter;
import com.smart.smartcity.adapters.NetworkAvailableListAdapter;
import com.smart.smartcity.adapters.NetworkListAdapter;
import com.smart.smartcity.context.IDownloadImageContext;
import com.smart.smartcity.context.INetworkListContext;
import com.smart.smartcity.dao.InterestDAO;
import com.smart.smartcity.dao.NetworkDAO;
import com.smart.smartcity.model.Network;
import com.smart.smartcity.model.Service;
import com.smart.smartcity.util.DownloadImageTask;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NetworkAvailableFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NetworkAvailableFragment extends Fragment implements INetworkListContext, IDownloadImageContext {
    private TextView networkAvailableStatus;
    private ListView networkListView;
    private NetworkListAdapter networkListAdapter;
    private List<Network> networks;

    public NetworkAvailableFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static NetworkAvailableFragment newInstance() {
        NetworkAvailableFragment fragment = new NetworkAvailableFragment();

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
        View view = inflater.inflate(R.layout.fragment_network_available, container, false);

        networkAvailableStatus = view.findViewById(R.id.network_available_status);

        // Find networks from web API
        NetworkDAO dao = new NetworkDAO();
        dao.setNetworkListContext(this);
        dao.findNetworks();

        networkListView = view.findViewById(R.id.network_list);
        networkListAdapter = new NetworkListAdapter(getActivity().getApplicationContext(), networks);
        networkListView.setAdapter(networkListAdapter);

        return view;
    }

    @Override
    public void onGetNetworksSuccessful(List<Network> networks) {
        this.networks.clear();
        this.networks.addAll(networks);
        networkListAdapter.clear();
        networkListAdapter.addAll(networks);
        networkListAdapter.notifyDataSetChanged();

        for (Network network : networks) {
            new DownloadImageTask(this, network.getId()).execute(network.getImageUrl());
        }
    }

    @Override
    public void onGetNetworksFailure() {
        networkAvailableStatus.setText("Error : can't get network list");
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
