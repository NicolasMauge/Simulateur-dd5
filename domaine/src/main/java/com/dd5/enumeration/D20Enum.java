package com.dd5.enumeration;

import lombok.Getter;

@Getter
public enum D20Enum {
    ECHEC_CRITIQUE("échec critique", 1),
    DEUX("2", 2),
    TROIS("3", 3),
    QUATRE("4", 4),
    CINQ("5", 5),
    SIX("6", 6),
    SEPT("7", 7),;


    private final String description;
    private final int valeur;

    D20Enum(String description, int valeur) {
        this.description = description;
        this.valeur = valeur;
    }

}
