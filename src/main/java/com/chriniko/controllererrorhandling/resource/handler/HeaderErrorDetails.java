package com.chriniko.controllererrorhandling.resource.handler;

import lombok.Getter;

@Getter
final class HeaderErrorDetails {

    private final String errorMessage;

    HeaderErrorDetails(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
