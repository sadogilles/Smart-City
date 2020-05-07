package com.smart.smartcity.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.smart.smartcity.R;
import com.smart.smartcity.model.Service;
import com.smart.smartcity.model.User;

import java.util.ArrayList;
import java.util.List;

public class ServiceSettingsAdapter extends ArrayAdapter<Service> implements CompoundButton.OnCheckedChangeListener {
    private LayoutInflater inflater;
    private ArrayList<Integer> userServices;

    public ServiceSettingsAdapter(Context context, List<Service> data, ArrayList<Integer> userServices) {
        super(context, 0, data);

        inflater = LayoutInflater.from(context);
        this.userServices = userServices;
    }

    @Override
    public long getItemId(int position) {
        Service data = (Service) getItem(position);

        return data.getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder;

        if (view == null) {
            view = inflater.inflate(R.layout.service_settings_adapter, null);

            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Service service = (Service) getItem(position);

        viewHolder.serviceName.setText(service.getName());
        viewHolder.serviceDescription.setText(service.getDescription());
        viewHolder.switchButton.setTag(service.getId());
        viewHolder.switchButton.setOnCheckedChangeListener(this);
        viewHolder.switchButton.setChecked(userServices.contains(service.getId()));

        if (service.getImageBitmap() != null) {
            viewHolder.serviceImage.setImageBitmap(service.getImageBitmap());
        }

        return view;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int id = (Integer) buttonView.getTag();

        if (isChecked) {
            if (! userServices.contains(id)) {
                userServices.add(id);
            }
        } else {
            userServices.remove(new Integer(id));
        }
    }

    public class ViewHolder {
        private final TextView serviceName;
        private final TextView serviceDescription;
        private final ImageView serviceImage;
        private final Switch switchButton;

        public ViewHolder(View view) {
            serviceName = view.findViewById(R.id.serviceSettingsTitle);
            serviceDescription = view.findViewById(R.id.serviceSettingsDescription);
            serviceImage = view.findViewById(R.id.serviceSettingsImage);
            switchButton = view.findViewById(R.id.serviceSettingsSwitch);
        }
    }
}
