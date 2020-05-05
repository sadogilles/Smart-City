package com.smart.smartcity.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.smart.smartcity.R;
import com.smart.smartcity.model.Interest;

import java.util.List;

public class InterestSettingsAdapter extends ArrayAdapter<Interest>{

    private LayoutInflater inflater;

    public InterestSettingsAdapter(@NonNull Context context, List<Interest> interests) {
        super(context, 0, interests);

        inflater = LayoutInflater.from(context);
    }

    @Override
    public long getItemId(int position) {
        Interest interest= (Interest) getItem(position);

        return interest.getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder;
        if (view == null) {
            view = inflater.inflate(R.layout.interest_settings_list, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Interest interest = (Interest) getItem(position);

        viewHolder.interestText.setText(interest.getName());

        return view;
    }

    private class ViewHolder {

        private final TextView interestText;
        private final CheckBox interestCheckBox;

        public ViewHolder(View view) {
            interestCheckBox= view.findViewById(R.id.interestText);
            interestText = view.findViewById(R.id.interestCheckBox);
        }
    }
}
