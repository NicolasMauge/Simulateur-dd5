package com.dd5.combat_attaque;

import com.dd5.entity.attaque.AttaqueEntity;
import com.dd5.entity.testdifficulte.AbstractConfrontation;
import com.dd5.enumeration.AvantageEnum;
import com.dd5.model.aleatoire.enumeration.ResultatTestDDEnum;
import com.dd5.ResultatAttaque;
import com.dd5.entity.protagoniste.ProtagonisteEntity;

public interface IAttaqueService {
    AttaqueEntity choisitAttaque(ProtagonisteEntity p);

    ResultatTestDDEnum quelResultatTest(AbstractConfrontation test,
                                        ProtagonisteEntity defenseur,
                                        AvantageEnum avantage);

    ResultatAttaque lanceAttaque(AttaqueEntity attaque,
                                 AvantageEnum avantage,
                                 ProtagonisteEntity defenseur);
}
