package com.dd5.model.caracteristiques.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MetaTypeDeCaracteristique {
    CARACTERISTIQUE_DE_BASE("une des caractéristique de base"),
    CLASSE_ARMURE("classe d'armure"),
    VALEUR_FIXE("une valeur fixe");

    private final String description;
}
