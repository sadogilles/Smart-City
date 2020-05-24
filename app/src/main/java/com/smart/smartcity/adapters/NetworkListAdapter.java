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
import com.smart.smartcity.context.ISubscribeContext;
import com.smart.smartcity.dao.NetworkDAO;
import com.smart.smartcity.model.Network;
import com.smart.smartcity.model.Service;
import com.smart.smartcity.model.Subscription;
import com.smart.smartcity.model.User;
import com.smart.smartcity.util.SubscriptionState;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NetworkListAdapter extends ArrayAdapter<Network> implements View.OnClickListener, ISubscribeContext {
    private LayoutInflater inflater;
    private IMainFragmentContext mainFragmentContext;
    private User user;
    private Button pressedButton = null;

    public NetworkListAdapter(Context context, List<Network> data, IMainFragmentContext mainFragmentContext, User user) {
        super(context, 0, data);

        inflater = LayoutInflater.from(context);
        this.mainFragmentContext = mainFragmentContext;
        this.user = user;
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
        viewHolder.subscribeButton.setTag(position);
        viewHolder.nameDescriptionLayout.setTag(position);
        viewHolder.nameDescriptionLayout.setOnClickListener(this);

        if (network.getImageBitmap() != null) {
            viewHolder.networkImage.setImageBitmap(network.getImageBitmap());
        }

        if (network.isPrivateAccess()) {
            viewHolder.lockImage.setVisibility(View.VISIBLE);
        } else {
            viewHolder.lockImage.setVisibility(View.INVISIBLE);
        }

        boolean pending = false;
        boolean accepted = false;

        SubscriptionState state = network.subscription(user.getId());

        if (state == SubscriptionState.PENDING) {
            viewHolder.subscribeButton.setText("Pending ...");
            viewHolder.subscribeButton.setEnabled(false);
            viewHolder.subscribeButton.setVisibility(View.VISIBLE);
        } else if (state == SubscriptionState.ACCEPTED || network.getAuthorId() == user.getId()) {
            viewHolder.subscribeButton.setVisibility(View.INVISIBLE);
        }

        viewHolder.subscribeButton.setTag(position);
        viewHolder.subscribeButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (pressedButton != null) {
            return;
        }

        if (v.getId() == R.id.network_name_description_layout) {
            Network network = getItem((Integer) v.getTag());

            SubscriptionState state = network.subscription(user.getId());

            if (! network.isPrivateAccess() || state == SubscriptionState.ACCEPTED) {
                mainFragmentContext.showNetworkDetailsFragment(network);
            }
        } else if (v.getId() == R.id.network_subscribe_button) {
            Network network = getItem((Integer) v.getTag());
            NetworkDAO dao = new NetworkDAO();
            dao.setSubscribeContext(this);
            pressedButton = (Button) v;
            v.setEnabled(false);
            Subscription subscription = new Subscription(user.getId(), network.getId(), "pending");
            dao.insertSubscription(subscription);
        }
    }

    @Override
    public void onSubscribeSuccessful(Subscription subscription) {
        Network network = getItem((Integer) pressedButton.getTag());

        network.getSubscriptions().add(subscription);
        notifyDataSetChanged();

        pressedButton.setEnabled(true);
        pressedButton = null;
    }

    @Override
    public void onSubscribeFailure() {
        pressedButton.setEnabled(true);
        pressedButton = null;
    }

    public class ViewHolder {
        private final LinearLayout nameDescriptionLayout;
        private final MaterialTextView networkName;
        private final MaterialTextView networkDescription;
        private final CircleImageView networkImage;
        private final Button subscribeButton;
        private final ImageView lockImage;

        public ViewHolder(View view) {
            nameDescriptionLayout = view.findViewById(R.id.network_name_description_layout);
            networkName = view.findViewById(R.id.network_item_name);
            networkDescription = view.findViewById(R.id.network_item_description);
            networkImage = view.findViewById(R.id.network_item_image);
            subscribeButton = view.findViewById(R.id.network_subscribe_button);
            lockImage = view.findViewById(R.id.network_lock_image);
        }
    }
}
