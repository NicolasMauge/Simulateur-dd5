package com.dd5.enumeration;

import lombok.Getter;

@Getter
public enum FinDEtatEnum {
    UN_ROUND("dure pendant un round"),
    UNE_MINUTE("dure pendant une minute"),
    NOUVEAU_TEST("nécessite un nouveau test");

    private final String description;

    FinDEtatEnum(String description) {
        this.description = description;
    }
}
