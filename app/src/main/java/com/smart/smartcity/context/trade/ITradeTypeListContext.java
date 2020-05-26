package com.smart.smartcity.context.trade;

import com.smart.smartcity.model.TradeType;

import java.util.List;

public interface ITradeTypeListContext {
    void onGetContentSuccessful(List<TradeType> tradeTypes);
    void onGetContentFailure();
}
