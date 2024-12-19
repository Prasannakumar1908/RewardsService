package com.prodify.apigateway.util;

public enum ServiceName {

    REWARD_SERVICE_URL("lb://reward-service/rewards/");

    public final String serviceUrl;

    private ServiceName(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }
}
