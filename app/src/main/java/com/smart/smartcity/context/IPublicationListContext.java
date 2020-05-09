package com.smart.smartcity.context;

import com.smart.smartcity.model.Publication;

import java.util.List;

public interface IPublicationListContext {
    void onGetPublicationsSuccessful(List<Publication> publications);
    void onGetPublicationsFailure();
}
