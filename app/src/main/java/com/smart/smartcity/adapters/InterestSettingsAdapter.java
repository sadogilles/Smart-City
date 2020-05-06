package com.smart.smartcity.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.smart.smartcity.R;
import com.smart.smartcity.model.Interest;

import java.util.List;

public class InterestSettingsAdapter extends ArrayAdapter<Interest> implements CompoundButton.OnCheckedChangeListener {
    private LayoutInflater inflater;
    private List<Integer> userInterests;

    public InterestSettingsAdapter(@NonNull Context context, List<Interest> interests, List<Integer> userInterests) {
        super(context, 0, interests);

        inflater = LayoutInflater.from(context);
        this.userInterests = userInterests;
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
            view = inflater.inflate(R.layout.interest_settings_adapter, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Interest interest = (Interest) getItem(position);

        viewHolder.interestSwitch.setTag(interest.getId());
        viewHolder.interestText.setText(interest.getName());
        viewHolder.interestSwitch.setChecked(userInterests.contains(interest.getId()));
        viewHolder.interestSwitch.setOnCheckedChangeListener(this);

        return view;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int id = (Integer) buttonView.getTag();

        if (isChecked) {
            if (! userInterests.contains(id)) {
                userInterests.add(id);
            }
        } else {
            userInterests.remove(new Integer(id));
        }
    }

    private class ViewHolder {

        private final TextView interestText;
        private final Switch interestSwitch;

        public ViewHolder(View view) {
            interestText = view.findViewById(R.id.interest_name);
            interestSwitch = view.findViewById(R.id.interest_switch);
        }
    }
}
