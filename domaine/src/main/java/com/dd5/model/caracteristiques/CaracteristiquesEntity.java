package com.dd5.model.caracteristiques;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CaracteristiquesEntity {
    private int caracteristiqueFor;
    private int caracteristiqueDex;
    private int caracteristiqueCon;
    private int caracteristiqueInt;
    private int caracteristiqueSag;
    private int caracteristiqueCha;
}
