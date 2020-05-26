package com.smart.smartcity.context.trade;

import com.smart.smartcity.model.Offer;

import java.util.List;

public interface IOfferListContext {
    List<Offer> onGetOffersSuccessful(List<Offer> offers);
    void onGetOffersFailure();
}
