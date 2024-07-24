package com.dd5.model.testdifficulte;

import com.dd5.model.caracteristiques.enumeration.CaracteristiqueEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class DescriptionTest {
    private String nomTest;
    private CaracteristiqueEnum caracteristique;
    private int degreDifficulte;
}
