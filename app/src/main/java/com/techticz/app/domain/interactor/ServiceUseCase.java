package com.techticz.app.domain.interactor;


import com.techticz.app.network.Request;
import com.techticz.app.network.ServiceRequest;

/**
 * Created by gssirohi on 11/7/16.
 */
public interface ServiceUseCase extends UseCase {
    ServiceRequest buildServiceRequest();

    Request buildRequest();
}
