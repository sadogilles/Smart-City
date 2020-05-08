package com.smart.smartcity.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.smart.smartcity.R;
import com.smart.smartcity.model.Network;
import com.smart.smartcity.model.Service;

import java.util.ArrayList;
import java.util.List;

public class NetworkListAdapter extends ArrayAdapter<Network> {
    private LayoutInflater inflater;
    private ArrayList<Integer> userServices;

    public NetworkListAdapter(Context context, List<Network> data) {
        super(context, 0, data);

        inflater = LayoutInflater.from(context);
        this.userServices = userServices;
    }

    @Override
    public long getItemId(int position) {
        Network network = (Network) getItem(position);

        return network.getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        NetworkListAdapter.ViewHolder viewHolder;

        if (view == null) {
            view = inflater.inflate(R.layout.service_settings_adapter, null);

            viewHolder = new NetworkListAdapter.ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (NetworkListAdapter.ViewHolder) view.getTag();
        }

        Network network = (Network) getItem(position);

        viewHolder.networkName.setText(network.getName());
        viewHolder.networkDescription.setText(network.getDescription());
        viewHolder.subscribeButton.setTag(network.getId());

        if (network.getImageBitmap() != null) {
            viewHolder.networkImage.setImageBitmap(network.getImageBitmap());
        }

        return view;
    }

    public class ViewHolder {
        private final TextView networkName;
        private final TextView networkDescription;
        private final ImageView networkImage;
        private final Switch subscribeButton;

        public ViewHolder(View view) {
            networkName = view.findViewById(R.id.network_item_name);
            networkDescription = view.findViewById(R.id.network_item_description);
            networkImage = view.findViewById(R.id.network_item_image);
            subscribeButton = view.findViewById(R.id.network_subscribe_button);
        }
    }
}
