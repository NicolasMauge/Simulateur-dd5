package com.dd5.combat_etapes;

import com.dd5.ResultatAttaque;
import com.dd5.combat.EtatProtagoniste;

import java.util.List;

public interface IEtatService {
    List<EtatProtagoniste> nouvelEtatSuiteAttaque(ResultatAttaque resultatAttaque,
                                                  List<EtatProtagoniste> etatProtagonisteList);
}
