package com.dd5.enumeration;

import lombok.Getter;

@Getter
public enum TypeDegatEnum {
    POISON("poison"),
    CONTONDANT("contondant"),
    TRANCHANT("tranchant"),
    PERFORANT("perforant"),
    NECROTIQUE("necrotique"),
    MAGIQUE("magique");

    private final String type;

    TypeDegatEnum(String type) {
        this.type = type;
    }

}
