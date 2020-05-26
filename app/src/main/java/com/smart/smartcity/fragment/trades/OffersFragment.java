package com.smart.smartcity.fragment.trades;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.smart.smartcity.R;
import com.smart.smartcity.activity.MainActivity;
import com.smart.smartcity.adapters.OfferListAdapter;
import com.smart.smartcity.context.global.IDownloadImageContext;
import com.smart.smartcity.context.trade.IOfferListContext;
import com.smart.smartcity.dao.OfferDAO;
import com.smart.smartcity.model.Offer;
import com.smart.smartcity.model.User;
import com.smart.smartcity.util.DownloadImageTask;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OffersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OffersFragment extends Fragment implements IOfferListContext, IDownloadImageContext {
    private TextView selectedOffersStatus;
    private ListView offersListView;
    private ArrayAdapter<Offer> offersListAdapter;
    private List<Offer> offers = new ArrayList<>();

    public OffersFragment() {
        // Required empty public constructor
    }

    public static OffersFragment newInstance() {
        OffersFragment fragment = new OffersFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offers, container, false);

        User user = ((MainActivity) getActivity()).getUser();

        selectedOffersStatus = view.findViewById(R.id.selected_offers_status);
        offersListView = view.findViewById(R.id.selected_offers_list_view);
        offersListAdapter = new OfferListAdapter(getActivity().getApplicationContext(), offers);
        offersListView.setAdapter(offersListAdapter);

        OfferDAO offerDAO = new OfferDAO();
        offerDAO.setOfferListContext(this);
        offerDAO.findOffersByUser(user);

        return view;
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
        selectedOffersStatus.setText("Error : can't load trade details");
    }

    @Override
    public void onImageDownloaded(Bitmap bitmap, int id) {
        for (Offer offer : offers) {
            if (offer.getId() == id) {
                offer.setImageBitmap(bitmap);
                offersListAdapter.notifyDataSetChanged();
            }
        }
    }
}
