package com.dd5.enumeration;

import lombok.Getter;

@Getter
public enum StatutEquipeEnum {
    VIVANTE("vivante"),
    NEUTRALISEE("neutralisée");

    private final String statut;

    StatutEquipeEnum(String statut) {
        this.statut = statut;
    }

}
