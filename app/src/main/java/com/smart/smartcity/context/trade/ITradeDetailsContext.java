package com.smart.smartcity.context.trade;

import com.smart.smartcity.model.Trade;

public interface ITradeDetailsContext {
    void onGetTradeSuccessful(Trade trade);
    void onGetTradeFailure();
}
