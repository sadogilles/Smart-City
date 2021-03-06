package com.smart.smartcity.context.settings;

import com.smart.smartcity.model.Interest;

import java.util.List;

public interface IInterestListContext {
    void onGetInterestsSuccessful(List<Interest> interests);
    void onGetInterestsFailure();
}
