package com.chriniko.controllererrorhandling.resource.handler;

import lombok.Getter;

@Getter
final class PayloadErrorDetails {

    private final String payloadErrorPath;
    private final String requiredTargetType;
    private final String errorMessage;

    PayloadErrorDetails(String payloadErrorPath, String requiredTargetType, String errorMessage) {
        this.payloadErrorPath = payloadErrorPath;
        this.requiredTargetType = requiredTargetType;
        this.errorMessage = errorMessage;
    }

}