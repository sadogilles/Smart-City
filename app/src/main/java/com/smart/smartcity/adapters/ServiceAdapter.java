package com.smart.smartcity.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.smart.smartcity.R;
import com.smart.smartcity.model.Service;
import com.smart.smartcity.util.DownloadImageTask;

import java.util.List;

public class ServiceAdapter extends ArrayAdapter<Service> {
    private LayoutInflater inflater;

    public ServiceAdapter(Context context, List<Service> data) {
        super(context, 0, data);

        inflater = LayoutInflater.from(context);
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
            view = inflater.inflate(R.layout.service_adapter, null);

            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Service data = (Service) getItem(position);

        viewHolder.serviceNameText.setText(data.getName());

        if (data.getImageBitmap() != null) {
            viewHolder.serviceImage.setImageBitmap(data.getImageBitmap());
        }

        return view;
    }

    public class ViewHolder {
        private final TextView serviceNameText;
        private final ImageView serviceImage;

        public ViewHolder(View view) {
            serviceNameText = view.findViewById(R.id.serviceNameText);
            serviceImage = view.findViewById(R.id.serviceImage);
        }
    }
}
