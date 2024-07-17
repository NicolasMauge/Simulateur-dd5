package com.dd5.attaque;

import com.dd5.aleatoire.NombreDeDes;
import com.dd5.enumeration.TypeDegatEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttaqueParDegatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private TypeDegatEnum typeDegat;

    @Embedded
    private NombreDeDes nombreDeDes;

    @Override
    public String toString() {
        return "AttaqueParDegatEntity{" +
                "id=" + id +
                ", typeDegat=" + typeDegat +
                ", nombreDeDes=" + nombreDeDes +
                '}';
    }
}
