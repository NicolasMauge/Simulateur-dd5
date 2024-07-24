package com.dd5.entity.protagoniste;

import com.dd5.entity.attaque.AttaqueEntity;
import com.dd5.enumeration.TypeRaceEnum;
import com.dd5.model.caracteristiques.CaracteristiquesEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProtagonisteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private TypeRaceEnum race;

    private int classeArmure;
    private int pointDeVie;
    private String pointDeVieDes;
    private String vitesse;

    private CaracteristiquesEntity caracteristiques;

    @ManyToMany
    private List<CompetenceEntity> competenceList;

    @OneToMany(cascade = CascadeType.PERSIST)
    private List<AttaqueEntity> listeAttaques;

    private int bonusDeMaitrise;

    private String listeResistances;
    private String listeVulnerabilites;
    private String listeImmunites;
}
