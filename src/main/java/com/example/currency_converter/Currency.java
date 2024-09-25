package com.example.currency_converter;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Currency {

    @Id
    private String currencyCode;
    private double rate;

    //
    public Currency() {}

    public Currency(String currencyCode, double rate) {
        this.currencyCode = currencyCode;
        this.rate = rate;
    }
}