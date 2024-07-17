package com.dd5.enumeration;

import lombok.Getter;

@Getter
public enum StatutProtagonisteEnum {
    VIVANT("vivante"),
    NEUTRALISE("neutralisée");

    private final String statut;

    StatutProtagonisteEnum(String statut) {
        this.statut = statut;
    }

}
