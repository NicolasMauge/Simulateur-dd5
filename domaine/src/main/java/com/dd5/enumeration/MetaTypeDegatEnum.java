package com.dd5.enumeration;

import lombok.Getter;

@Getter
public enum MetaTypeDegatEnum {
    RESISTANCE("résistance"),
    VULNERABILITE("vulnérabilité"),
    IMMUNITE("immunité"),
    EFFET_NORMAL("effet normal");

    private final String typologie;

    MetaTypeDegatEnum(String typologie) {
        this.typologie = typologie;
    }

}
