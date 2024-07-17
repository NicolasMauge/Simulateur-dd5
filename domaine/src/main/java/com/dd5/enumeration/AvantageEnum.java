package com.dd5.enumeration;

import lombok.Getter;

@Getter
public enum AvantageEnum {
    AVANTAGE("avantagé"),
    DESAVANTAGE("désavantagé"),
    NEUTRE("ni avantagé, ni désavantagé");

    private final String typeAvantage;

    AvantageEnum(String typeAvantage) {
        this.typeAvantage = typeAvantage;
    }

}
