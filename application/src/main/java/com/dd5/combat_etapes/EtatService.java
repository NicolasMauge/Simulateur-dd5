package com.dd5.combat_etapes;

import com.dd5.enumeration.ConditionEnum;
import com.dd5.enumeration.StatutProtagonisteEnum;
import com.dd5.ResultatAttaque;
import com.dd5.combat.EtatProtagoniste;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class EtatService {
    public List<EtatProtagoniste> nouvelEtatSuiteAttaque(ResultatAttaque resultatAttaque,
                                                          List<EtatProtagoniste> etatProtagonisteList) {
        EtatProtagoniste dernierEtat = etatProtagonisteList.getLast();

        if (dernierEtat.statutProtagoniste() == StatutProtagonisteEnum.NEUTRALISE) {
            return etatProtagonisteList;
        }

        // on copie les conditions et on ajoute la dernière à la fin
        // TODO transformer en set
        Set<ConditionEnum> conditionList = new HashSet<>(dernierEtat.listeConditions());
        conditionList.add(resultatAttaque.getEtatSupplementaire());

        int vie = dernierEtat.vie() - resultatAttaque.getTotalDegats();

        List<EtatProtagoniste> etatProtagonisteListMutable = new ArrayList<>(etatProtagonisteList);

        etatProtagonisteListMutable.add(new EtatProtagoniste(
                vie,
                conditionList,
                (vie <=0)? StatutProtagonisteEnum.NEUTRALISE:StatutProtagonisteEnum.VIVANT));

        return etatProtagonisteListMutable;
    }
}
