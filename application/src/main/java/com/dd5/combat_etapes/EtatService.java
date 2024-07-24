package com.dd5.combat_etapes;

import com.dd5.enumeration.ConditionEnum;
import com.dd5.enumeration.StatutProtagonisteEnum;
import com.dd5.ResultatAttaque;
import com.dd5.model.combat.EtatProtagoniste;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class EtatService implements IEtatService {
    @Override
    public List<EtatProtagoniste> nouvelEtatSuiteAttaque(ResultatAttaque resultatAttaque,
                                                          List<EtatProtagoniste> etatProtagonisteList) {
        EtatProtagoniste dernierEtat = etatProtagonisteList.getLast();

        if (dernierEtat.statutProtagoniste() == StatutProtagonisteEnum.NEUTRALISE) {
            return etatProtagonisteList;
        }

        // on copie les conditions et on ajoute la dernière à la fin
        Set<ConditionEnum> setConditions = dernierEtat.setConditions();
        setConditions.addAll(resultatAttaque.getSetConditionsSupplementaires());

        int vie = dernierEtat.vie() - resultatAttaque.getTotalDegats();

        List<EtatProtagoniste> listeEtatsProtagoniste = new ArrayList<>(etatProtagonisteList);

        listeEtatsProtagoniste.add(new EtatProtagoniste(
                vie,
                setConditions,
                (vie <=0)? StatutProtagonisteEnum.NEUTRALISE:StatutProtagonisteEnum.VIVANT));

        return listeEtatsProtagoniste;
    }
}
