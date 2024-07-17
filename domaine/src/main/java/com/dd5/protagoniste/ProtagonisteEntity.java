package com.dd5.protagoniste;

import com.dd5.attaque.AttaqueEntity;
import com.dd5.enumeration.TypeRaceEnum;
import com.dd5.caracteristiques.CaracteristiquesEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    @Override
    public String toString() {
        return "ProtagonisteEntity{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", race=" + race +
                ", classeArmure='" + classeArmure + '\'' +
                ", pointDeVie=" + pointDeVie +
                ", pointDeVieDes='" + pointDeVieDes + '\'' +
                ", vitesse='" + vitesse + '\'' +
                ", caracteristiques=" + caracteristiques +
                ", competenceList=" + competenceList +
                ", listeAttaques=" + listeAttaques +
                ", bonusDeMaitrise=" + bonusDeMaitrise +
                '}';
    }
}
