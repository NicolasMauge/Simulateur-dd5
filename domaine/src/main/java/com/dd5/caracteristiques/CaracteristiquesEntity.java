package com.dd5.caracteristiques;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CaracteristiquesEntity {
    private int caracteristiqueFor;
    private int caracteristiqueDex;
    private int caracteristiqueCon;
    private int caracteristiqueInt;
    private int caracteristiqueSag;
    private int caracteristiqueCha;
}
