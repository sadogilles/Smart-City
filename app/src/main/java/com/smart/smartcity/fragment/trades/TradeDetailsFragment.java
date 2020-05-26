package com.smart.smartcity.fragment.trades;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.smart.smartcity.R;
import com.smart.smartcity.adapters.OfferListAdapter;
import com.smart.smartcity.context.global.IDownloadImageContext;
import com.smart.smartcity.context.trade.IOfferListContext;
import com.smart.smartcity.context.trade.ITradeDetailsContext;
import com.smart.smartcity.dao.OfferDAO;
import com.smart.smartcity.dao.TradeDAO;
import com.smart.smartcity.model.Offer;
import com.smart.smartcity.model.Trade;
import com.smart.smartcity.util.DownloadImageTask;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TradeDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TradeDetailsFragment extends Fragment implements ITradeDetailsContext, IDownloadImageContext, IOfferListContext {
    private static final String KEY_TRADE_ID = "TRADE_ID";
    private ImageView tradeDetailsImage;
    private TextView tradeDetailsName;
    private TextView tradeDetailsDescription;
    private TextView tradeDetailsStatus;
    private ListView offersListView;
    private ArrayAdapter<Offer> offersListAdapter;
    private List<Offer> offers = new ArrayList<>();

    private int tradeId;

    public TradeDetailsFragment() {
        // Required empty public constructor
    }

    public static TradeDetailsFragment newInstance(int tradeId) {
        TradeDetailsFragment fragment = new TradeDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_TRADE_ID, tradeId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tradeId = getArguments().getInt(KEY_TRADE_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trade_details, container, false);

        tradeDetailsImage = view.findViewById(R.id.trade_details_image);
        tradeDetailsName = view.findViewById(R.id.trade_details_name);
        tradeDetailsDescription = view.findViewById(R.id.trade_details_description);
        tradeDetailsStatus = view.findViewById(R.id.trade_details_status);
        offersListView = view.findViewById(R.id.trade_details_offers_list_view);
        offersListAdapter = new OfferListAdapter(getActivity().getApplicationContext(), offers);
        offersListView.setAdapter(offersListAdapter);

        TradeDAO tradeDAO = new TradeDAO();
        tradeDAO.setTradeDetailsContext(this);
        tradeDAO.findTrade(tradeId);

        return view;
    }

    @Override
    public void onGetTradeSuccessful(Trade trade) {
        tradeDetailsName.setText(trade.getName());
        tradeDetailsDescription.setText(trade.getDescription());

        new DownloadImageTask(this, -1).execute(trade.getImageUrl());

        OfferDAO offerDAO = new OfferDAO();
        offerDAO.setOfferListContext(this);
        offerDAO.findOffersByTrade(trade);
    }

    @Override
    public void onGetTradeFailure() {
        tradeDetailsStatus.setText("Error : can't load trade details");
    }

    @Override
    public void onImageDownloaded(Bitmap bitmap, int id) {
        if (id == -1) {
            tradeDetailsImage.setImageBitmap(bitmap);
            offersListAdapter.notifyDataSetChanged();
        } else {
            for (Offer offer : offers) {
                if (offer.getId() == id) {
                    offer.setImageBitmap(bitmap);
                    offersListAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public List<Offer> onGetOffersSuccessful(List<Offer> offers) {
        this.offers.clear();
        this.offers.addAll(offers);
        offersListAdapter.clear();
        offersListAdapter.addAll(offers);
        offersListAdapter.notifyDataSetChanged();

        for (Offer offer : offers) {
            new DownloadImageTask(this, offer.getId()).execute(offer.getImageUrl());
        }

        return null;
    }

    @Override
    public void onGetOffersFailure() {
        tradeDetailsStatus.setText("Error : can't load trade offers");
    }
}
