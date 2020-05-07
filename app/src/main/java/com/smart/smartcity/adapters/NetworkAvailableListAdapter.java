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

public class NetworkAvailableListAdapter extends ArrayAdapter<Service> {
    private LayoutInflater inflater;

    public NetworkAvailableListAdapter(Context context, List<com.smart.smartcity.model.Service> data) {
        super(context, 0, data);

        inflater = LayoutInflater.from(context);
    }


}

