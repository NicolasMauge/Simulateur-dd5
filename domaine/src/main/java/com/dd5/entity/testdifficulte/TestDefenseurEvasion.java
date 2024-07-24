package com.dd5.entity.testdifficulte;

import com.dd5.model.caracteristiques.enumeration.CaracteristiqueEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@DiscriminatorValue("evasion")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class TestDefenseurEvasion extends AbstractConfrontation {
    private CaracteristiqueEnum caracteristique;
    private int degreDifficulte;
}
