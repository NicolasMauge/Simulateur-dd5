package com.dd5.combat_attaque;

import com.dd5.enumeration.AvantageEnum;
import com.dd5.enumeration.MetaTypeDegatEnum;
import com.dd5.enumeration.ResultatTestDDEnum;
import com.dd5.enumeration.TypeDegatEnum;
import com.dd5.ResultatAttaque;
import com.dd5.attaque.AttaqueEntity;
import com.dd5.protagoniste.ProtagonisteEntity;

import java.util.Map;

public interface IAttaqueService {
    AttaqueEntity choisitAttaque(ProtagonisteEntity p);

    ResultatTestDDEnum toucheOuNon(AttaqueEntity attaque, int classeArmure, AvantageEnum avantage);

    ResultatAttaque lanceAttaque(AttaqueEntity attaque,
                                 int classeArmure,
                                 Map<TypeDegatEnum, MetaTypeDegatEnum> reactionDegats,
                                 AvantageEnum avantage);
}
