package com.chriniko.controllererrorhandling.domain;

import lombok.Data;

@Data
public class Status {

    private String message;

    public Status(String message) {
        this.message = message;
    }

}
