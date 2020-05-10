package com.smart.smartcity.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.textview.MaterialTextView;
import com.smart.smartcity.R;
import com.smart.smartcity.context.IAcceptSubscriptionContext;
import com.smart.smartcity.context.IMainFragmentContext;
import com.smart.smartcity.dao.NetworkDAO;
import com.smart.smartcity.model.Network;
import com.smart.smartcity.model.Subscription;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SubscriptionListAdapter extends ArrayAdapter<Subscription> implements View.OnClickListener, IAcceptSubscriptionContext {
    private LayoutInflater inflater;
    private Button buttonPressed = null;

    public SubscriptionListAdapter(Context context, List<Subscription> data) {
        super(context, 0, data);

        inflater = LayoutInflater.from(context);
    }

    @Override
    public long getItemId(int position) {
        Subscription subscription = (Subscription) getItem(position);

        return subscription.getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        SubscriptionListAdapter.ViewHolder viewHolder;

        if (view == null) {
            view = inflater.inflate(R.layout.subscription_list_adapter, null);

            viewHolder = new SubscriptionListAdapter.ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (SubscriptionListAdapter.ViewHolder) view.getTag();
        }

        Subscription subscription = (Subscription) getItem(position);

        viewHolder.userName.setText(subscription.getUserName());
        viewHolder.acceptButton.setTag(position);
        viewHolder.rejectButton.setTag(position);
        viewHolder.acceptButton.setOnClickListener(this);
        viewHolder.rejectButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (buttonPressed != null) {
            return;
        }

        if (v.getId() == R.id.subscription_accept_button) {
            buttonPressed = (Button) v;
            v.setEnabled(false);

            Subscription subscription = getItem((Integer) v.getTag());
            int id = subscription.getId();

            NetworkDAO dao = new NetworkDAO();
            dao.setAcceptSubscriptionContext(this);
            subscription.setState("accepted");
            dao.acceptSubscription(subscription);
        } else if (v.getId() == R.id.subscription_reject_button) {
            v.setEnabled(false);

            Subscription subscription = getItem((Integer) v.getTag());
            int id = subscription.getId();

        }
    }


    @Override
    public void onAcceptSubscriptionSuccessful(Subscription subscription) {
        int position = (Integer) buttonPressed.getTag();
        remove(getItem(position));
        notifyDataSetChanged();

        buttonPressed = null;
    }

    @Override
    public void onAcceptSubscriptionError() {
        buttonPressed.setEnabled(true);
        buttonPressed = null;
    }

    public class ViewHolder {
        private TextView userName;
        private Button acceptButton;
        private Button rejectButton;

        public ViewHolder(View view) {
            userName = view.findViewById(R.id.subscription_user_name);
            acceptButton = view.findViewById(R.id.subscription_accept_button);
            rejectButton = view.findViewById(R.id.subscription_reject_button);
        }
    }
}
