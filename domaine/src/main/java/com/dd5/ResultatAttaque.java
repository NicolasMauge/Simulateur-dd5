package com.dd5;

import com.dd5.enumeration.ConditionEnum;
import com.dd5.enumeration.ResultatTestDDEnum;
import com.dd5.attaque.DegatParTypeAjuste;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ResultatAttaque {
    private ResultatTestDDEnum resultat;
    private boolean toucheOuNon;
    private List<DegatParTypeAjuste> degatParType;
    private int totalDegats;
    private ConditionEnum etatSupplementaire;
}
