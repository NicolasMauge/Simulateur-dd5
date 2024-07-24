package com.dd5;

import com.dd5.enumeration.ConditionEnum;
import com.dd5.model.aleatoire.enumeration.ResultatTestDDEnum;
import com.dd5.model.attaque.DegatParTypeAjuste;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class ResultatAttaque {
    private ResultatTestDDEnum resultat;
    private boolean toucheOuNon;
    private Set<DegatParTypeAjuste> degatParType;
    private int totalDegats;
    private Set<ConditionEnum> setConditionsSupplementaires;
}
