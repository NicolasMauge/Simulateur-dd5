package com.dd5.enumeration;

import lombok.Getter;

@Getter
public enum ResultatTestDDEnum {
    REUSSITE_CRITIQUE("réussite critique"),
    REUSSITE("réussite"),
    ECHEC("échec"),
    ECHEC_CRITIQUE("échec critique");

    private final String resultatTest;

    ResultatTestDDEnum(String resultatTest) {
        this.resultatTest = resultatTest;
    }

}
