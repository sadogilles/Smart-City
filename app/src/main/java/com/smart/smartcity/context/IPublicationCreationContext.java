package com.smart.smartcity.context;

import com.smart.smartcity.model.Publication;

public interface IPublicationCreationContext {
    void onPublicationCreationSuccessful(Publication publication);
    void onPublicationCreationFailure();
}
