package com.smart.smartcity.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.smart.smartcity.R;
import com.smart.smartcity.model.Offer;
import com.smart.smartcity.model.TradeType;

import org.w3c.dom.Text;

import java.util.List;

public class OfferListAdapter extends ArrayAdapter<Offer> implements View.OnClickListener {
    private LayoutInflater inflater;

    public OfferListAdapter(@NonNull Context context, @NonNull List<Offer> objects) {
        super(context, 0, objects);

        inflater = LayoutInflater.from(context);
    }

    @Override
    public long getItemId(int position) {
        Offer offer = (Offer) getItem(position);

        return offer.getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        OfferListAdapter.ViewHolder viewHolder;

        if (view == null) {
            view = inflater.inflate(R.layout.offer_list_adapter, null);

            viewHolder = new OfferListAdapter.ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (OfferListAdapter.ViewHolder) view.getTag();
        }

        Offer offer = (Offer) getItem(position);

        viewHolder.offerTitle.setText(offer.getTitle());
        viewHolder.offerDescription.setText(offer.getDescription());

        Bitmap imageBitmap = offer.getImageBitmap();
        if (imageBitmap != null) {
            viewHolder.offerImage.setImageBitmap(imageBitmap);
        }

        viewHolder.offerTitleLayout.setTag(false);
        viewHolder.offerTitleLayout.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        boolean shown = (Boolean) v.getTag();
        LinearLayout offerDetailsLayout =  ((ViewGroup) v.getParent()).findViewById(R.id.offer_details_layout);

        if (! shown) {
            offerDetailsLayout.setVisibility(View.VISIBLE);
            v.setTag(true);
        } else {
            offerDetailsLayout.setVisibility(View.GONE);
            v.setTag(false);
        }
    }


    public class ViewHolder {
        private LinearLayout offerTitleLayout;
        private TextView offerTitle;
        private TextView offerDescription;
        private ImageView offerImage;

        public ViewHolder(View view) {
            offerTitleLayout = view.findViewById(R.id.offer_title_layout);
            offerTitle = view.findViewById(R.id.offer_title);
            offerDescription = view.findViewById(R.id.offer_description);
            offerImage = view.findViewById(R.id.offer_image);
        }
    }
}
