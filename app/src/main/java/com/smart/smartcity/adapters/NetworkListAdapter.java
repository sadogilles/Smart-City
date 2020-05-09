package com.smart.smartcity.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.material.textview.MaterialTextView;
import com.smart.smartcity.R;
import com.smart.smartcity.context.IMainFragmentContext;
import com.smart.smartcity.model.Network;
import com.smart.smartcity.model.Service;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NetworkListAdapter extends ArrayAdapter<Network> implements View.OnClickListener {
    private LayoutInflater inflater;
    private IMainFragmentContext mainFragmentContext;

    public NetworkListAdapter(Context context, List<Network> data, IMainFragmentContext mainFragmentContext) {
        super(context, 0, data);

        inflater = LayoutInflater.from(context);
        this.mainFragmentContext = mainFragmentContext;
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
            view = inflater.inflate(R.layout.network_list_adapter, null);

            viewHolder = new NetworkListAdapter.ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (NetworkListAdapter.ViewHolder) view.getTag();
        }

        Network network = (Network) getItem(position);

        viewHolder.networkName.setText(network.getName());
        viewHolder.networkDescription.setText(network.getDescription());
        viewHolder.subscribeButton.setTag(network.getId());
        viewHolder.nameDescriptionLayout.setTag(network.getId());
        viewHolder.nameDescriptionLayout.setOnClickListener(this);

        if (network.getImageBitmap() != null) {
            viewHolder.networkImage.setImageBitmap(network.getImageBitmap());
        }

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.network_name_description_layout) {
            Network network = getItem((Integer) v.getTag());

            mainFragmentContext.showNetworkDetailsFragment(network);
        }
    }

    public class ViewHolder {
        private LinearLayout nameDescriptionLayout;
        private final MaterialTextView networkName;
        private final MaterialTextView networkDescription;
        private final CircleImageView networkImage;
        private final Button subscribeButton;

        public ViewHolder(View view) {
            nameDescriptionLayout = view.findViewById(R.id.network_name_description_layout);
            networkName = view.findViewById(R.id.network_item_name);
            networkDescription = view.findViewById(R.id.network_item_description);
            networkImage = view.findViewById(R.id.network_item_image);
            subscribeButton = view.findViewById(R.id.network_subscribe_button);
        }
    }
}
