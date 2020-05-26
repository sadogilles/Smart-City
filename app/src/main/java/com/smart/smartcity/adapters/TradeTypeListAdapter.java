package com.smart.smartcity.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.smart.smartcity.R;
import com.smart.smartcity.model.Network;
import com.smart.smartcity.model.TradeType;

import java.util.List;

public class TradeTypeListAdapter extends ArrayAdapter<TradeType> {
    private Context context;
    private LayoutInflater inflater;

    public TradeTypeListAdapter(@NonNull Context context, @NonNull List<TradeType> tradeTypes) {
        super(context, 0, tradeTypes);

        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public long getItemId(int position) {
        TradeType tradeType = (TradeType) getItem(position);

        return tradeType.getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        TradeTypeListAdapter.ViewHolder viewHolder;

        if (view == null) {
            view = inflater.inflate(R.layout.trade_type_layout, null);

            viewHolder = new TradeTypeListAdapter.ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (TradeTypeListAdapter.ViewHolder) view.getTag();
        }

        TradeType tradeType = (TradeType) getItem(position);

        viewHolder.tradeTypeName.setText(tradeType.getName());

        if (tradeType.isCancel()) {
            viewHolder.linearLayout.setBackgroundColor(context.getColor(R.color.colorPrimaryDark));
            viewHolder.tradeTypeName.setTextColor(context.getColor(R.color.white));
        } else if (tradeType.getTradeId() != -1) {
            viewHolder.linearLayout.setBackground(context.getDrawable(R.drawable.trade_background));
            viewHolder.tradeTypeName.setTextColor(context.getColor(R.color.colorPrimary));
        } else {
            viewHolder.linearLayout.setBackgroundColor(context.getColor(R.color.colorPrimary));
            viewHolder.tradeTypeName.setTextColor(context.getColor(R.color.white));
        }

        return view;
    }

    public class ViewHolder {
        private LinearLayout linearLayout;
        private TextView tradeTypeName;

        public ViewHolder(View view) {
            linearLayout = view.findViewById(R.id.trade_type_linear_layout);
            tradeTypeName = view.findViewById(R.id.trade_type_name);
        }
    }
}
