package com.dd5.entity.attaque.pardegat;

import com.dd5.model.aleatoire.NombreDeDes;
import com.dd5.enumeration.TypeDegatEnum;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AttaqueParDegatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private TypeDegatEnum typeDegat;

    @Embedded
    private NombreDeDes nombreDeDes;
}
