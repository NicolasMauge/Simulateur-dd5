package com.dd5.enumeration;

import lombok.Getter;

@Getter
public enum AvantageEnum {
    AVANTAGE("avantagé"),
    DESAVANTAGE("désavantagé"),
    NEUTRE("ni avantagé, ni désavantagé"),
    AUCUNE_ACTION("aucune action possible");

    private final String typeAvantage;

    AvantageEnum(String typeAvantage) {
        this.typeAvantage = typeAvantage;
    }

}
