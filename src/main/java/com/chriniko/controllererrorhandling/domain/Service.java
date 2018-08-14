package com.chriniko.controllererrorhandling.domain;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Service {

    private String description;
    private String kms;
    private LocalDate date;
    private Motorcycle motorcycle;
    private Owner owner;

}
